package splitter.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Book {
    private List<Transaction> transactionList;
    private Set<Account> accountSet;

    public Book(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public Set<Account> getAccountSet() {
        return accountSet;
    }

    public void setupAccountSet() {
        if (accountSet == null) {
            accountSet = new HashSet<>();
        }
        Set<Account> accounts =
                transactionList.stream().map(Transaction::getFrom).collect(Collectors.toSet());
        accounts.addAll(transactionList.stream().map(Transaction::getTo).collect(Collectors.toSet()));
        accountSet.addAll(accounts);
        accountSet.forEach(a -> a.getAccountBalanceMap().clear());
    }
}
