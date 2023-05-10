package splitter.functionality;

import splitter.model.Book;
import splitter.model.Transaction;

public interface DoTransaction {
    void addTransaction(Transaction transaction, Book book);
}
