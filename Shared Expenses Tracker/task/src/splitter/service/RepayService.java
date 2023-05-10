package splitter.service;

import splitter.functionality.DoTransaction;
import splitter.model.Book;
import splitter.model.Transaction;

public class RepayService extends Service implements DoTransaction {

    private static RepayService instance;

    private RepayService() {}

    public static synchronized RepayService getInstance() {
        if (null == instance) {
            instance = new RepayService();
        }
        return instance;
    }

    @Override
    public void addTransaction(Transaction transaction, Book book) {
        book.getTransactionList().add(transaction);
    }
}
