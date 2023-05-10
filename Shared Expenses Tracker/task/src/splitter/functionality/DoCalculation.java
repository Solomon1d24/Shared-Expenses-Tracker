package splitter.functionality;

import splitter.model.Book;

import java.time.LocalDate;

public interface DoCalculation {
    void calculate(LocalDate localDate, String openCloseBalance, Book book);
}
