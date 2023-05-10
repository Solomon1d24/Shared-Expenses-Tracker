package splitter.service;

import splitter.functionality.DoCalculation;
import splitter.model.Account;
import splitter.model.Book;
import splitter.model.Pair;
import splitter.model.Transaction;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BalanceService extends Service implements DoCalculation {

    private static BalanceService instance;

    private BalanceService() {}

    public static synchronized BalanceService getInstance() {
        if (null == instance) {
            instance = new BalanceService();
        }
        return instance;
    }

    @Override
    public void calculate(LocalDate localDate, String openCloseBalance, Book book) {
        List<Transaction> selectedTransactions = null;
        Set<Pair<Account, Account>> accountPairSet = new HashSet<>();
        List<String> messages = new ArrayList<>();
        book.setupAccountSet();
        if (Objects.equals("open", openCloseBalance)) {
            LocalDate firstDateOfMonth = LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
            selectedTransactions = book.getTransactionList().stream()
                    .filter(t -> t.getDate().isBefore(firstDateOfMonth))
                    .toList();
            accountPairSet = doNetting(selectedTransactions, accountPairSet);
        } else if (Objects.equals("close", openCloseBalance)) {
            selectedTransactions = book.getTransactionList().stream()
                    .filter(t -> t.getDate().isBefore(localDate) || t.getDate().isEqual(localDate))
                    .toList();
            accountPairSet = doNetting(selectedTransactions, accountPairSet);
        }
        if (accountPairSet.size() == 0) {
            System.out.println("No repayments");
        } else {
            accountPairSet.stream().filter(p -> p.getAmount().intValue() > 0).forEach(p -> {
                messages.add(p.getLeft().getName() + " owes " + p.getRight().getName() + " " + p.getAmount());
            });
            accountPairSet.stream().filter(p -> p.getAmount().intValue() < 0).forEach(p -> {
                messages.add(p.getRight().getName() + " owes " + p.getLeft().getName() + " "
                        + Math.abs(p.getAmount().intValue()));
            });
        }
        messages.stream().sorted().forEach(System.out::println);
    }

    private Set<Pair<Account, Account>> doNetting(
            List<Transaction> selectedTransactions, Set<Pair<Account, Account>> accountPairs) {
        selectedTransactions.forEach(t -> {
            Account left = t.getFrom();
            Account to = t.getTo();
            Pair<Account, Account> pair = new Pair<>(left, to, new AtomicInteger(0));
            accountPairs.add(pair);
        });

        for (Pair<Account, Account> pair : accountPairs) {
            List<Transaction> leftToRightList = selectedTransactions.stream()
                    .filter(t -> {
                        Account from = t.getFrom();
                        Account to = t.getTo();
                        return (from.equals(pair.getLeft()) && to.equals(pair.getRight()));
                    })
                    .toList();
            List<Transaction> rightToLeftList = selectedTransactions.stream()
                    .filter(t -> {
                        Account from = t.getFrom();
                        Account to = t.getTo();
                        return (from.equals(pair.getRight()) && to.equals(pair.getLeft()));
                    })
                    .toList();
            leftToRightList.forEach(t -> {
                if (t.getAction().equals("borrow")) {
                    pair.getAmount().addAndGet(t.getAmount());
                } else if (t.getAction().equals("repay")) {
                    pair.getAmount().addAndGet(t.getAmount() * -1);
                }
            });
            rightToLeftList.forEach(t -> {
                if (t.getAction().equals("borrow")) {
                    pair.getAmount().addAndGet(t.getAmount() * -1);
                } else if (t.getAction().equals("repay")) {
                    pair.getAmount().addAndGet(t.getAmount());
                }
            });
        }
        return accountPairs.stream().filter(p -> p.getAmount().intValue() != 0).collect(Collectors.toSet());
    }
}
