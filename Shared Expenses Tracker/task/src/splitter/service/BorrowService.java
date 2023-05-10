package splitter.service;

import splitter.functionality.DoTransaction;
import splitter.model.Book;
import splitter.model.Transaction;

public class BorrowService extends Service implements DoTransaction {

    private static BorrowService instance;

    private BorrowService() {}

    public static synchronized BorrowService getInstance() {
        if (instance == null) {
            instance = new BorrowService();
        }
        return instance;
    }

    @Override
    public void addTransaction(Transaction transaction, Book book) {
        book.getTransactionList().add(transaction);
    }
}
