package splitter.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Pair<L, R> {

    private L left;

    private R right;

    private AtomicInteger amount;

    public Pair(L left, R right, AtomicInteger amount) {
        this.left = left;
        this.right = right;
        this.amount = amount;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public AtomicInteger getAmount() {
        return amount;
    }

    public void setAmount(AtomicInteger amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair<?, ?>)) return false;
        return (this.getLeft().equals(((Pair<?, ?>) obj).getLeft())
                        && this.getRight().equals(((Pair<?, ?>) obj).getRight()))
                || (this.getLeft().equals(((Pair<?, ?>) obj).getRight())
                        && this.getRight().equals(((Pair<?, ?>) obj).getLeft()));
    }

    @Override
    public int hashCode() {
        return this.getLeft().hashCode() + this.getRight().hashCode();
    }

    @Override
    public String toString() {
        return "Pair{" + "left=" + left + ", right=" + right + ", amount=" + amount + '}';
    }
}
