package splitter.model;

import java.time.LocalDate;

public class Transaction implements Comparable {

    private Account from;

    private Account to;

    private LocalDate date;

    private String action;

    private int amount;

    public Transaction(Account from, Account to, LocalDate date, String action, int amount) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.action = action;
        this.amount = amount;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" + "from="
                + from + ", to="
                + to + ", date="
                + date + ", action='"
                + action + '\'' + ", amount="
                + amount + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;

        if (!getFrom().equals(that.getFrom())) return false;
        if (!getTo().equals(that.getTo())) return false;
        if (!date.equals(that.date)) return false;
        return action.equals(that.action);
    }

    @Override
    public int hashCode() {
        int result = getFrom().hashCode();
        result = 31 * result + getTo().hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Transaction) {
            return this.date.compareTo(((Transaction) o).date);
        }
        return 0;
    }
}
