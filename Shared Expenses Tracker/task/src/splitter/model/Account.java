package splitter.model;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private String name;

    private Map<Account, Integer> accountBalanceMap;

    public Account(String name) {
        this.name = name;
        this.accountBalanceMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<Account, Integer> getAccountBalanceMap() {
        return accountBalanceMap;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account)) {
            return false;
        }
        return this.name.equals(((Account) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Account{" + "name='" + name + '\'' + '}';
    }
}
