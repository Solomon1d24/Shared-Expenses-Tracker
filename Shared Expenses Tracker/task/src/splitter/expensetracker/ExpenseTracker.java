package splitter.expensetracker;

import splitter.factory.ServiceFactory;
import splitter.functionality.DoCalculation;
import splitter.functionality.DoTransaction;
import splitter.model.Account;
import splitter.model.Book;
import splitter.model.Transaction;
import splitter.service.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class ExpenseTracker {

    private LocalDate transactionDate;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    private Book book;

    public ExpenseTracker(Book book) {
        this.book = book;
    }

    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String[] entries = scanner.nextLine().split(" ");
            try {
                transactionDate = LocalDate.parse(entries[0], dateTimeFormatter);
                entries = Arrays.copyOfRange(entries, 1, entries.length);
            } catch (DateTimeException e) {
                transactionDate = LocalDate.now();
            }
            try {
                validation(entries);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
            String command = entries[0];
            if (Objects.equals("exit", command)) {
                System.exit(0);
            } else if (Objects.equals("help", command)) {
                System.out.println("balance\n" + "borrow\n" + "exit\n" + "help\n" + "repay");
                continue;
            }
            Service service = ServiceFactory.get(command);
            if (service instanceof DoCalculation) {
                String openClose;
                try {
                    openClose = entries[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    openClose = "close";
                }
                ((DoCalculation) service).calculate(transactionDate, openClose, book);
            } else {
                String action = entries[0];
                Account from = new Account(entries[1]);
                Account to = new Account(entries[2]);
                int amount = Integer.parseInt(entries[3]);
                Transaction transaction = new Transaction(from, to, transactionDate, action, amount);
                DoTransaction doTransaction = (DoTransaction) service;
                doTransaction.addTransaction(transaction, book);
            }
        }
    }

    private void validation(String[] inputs) throws IllegalArgumentException {
        List<String> entries = Arrays.asList("help", "balance", "repay", "exit", "borrow");
        if (entries.stream().noneMatch(m -> inputs[0].equals(m))) {
            throw new IllegalArgumentException("Unknown command. Print help to show commands list");
        } else if (inputs.length <= 2 && Stream.of("balance", "help", "exit").noneMatch(m -> inputs[0].equals(m))) {
            throw new IllegalArgumentException("Illegal command arguments");
        }
    }
}
