import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private List<Account> accounts = new ArrayList<>();
    private List<ATM> atms = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public Account getAccount(int number) {
        for (Account acc : accounts) {
            if (acc.getNumber() == number) {
                return acc;
            }
        }
        return null;
    }

    public void addATM(ATM atm) {
        atms.add(atm);
    }

    public ATM getATM(int id) {
        for (ATM atm : atms) {
            if (atm.getIdentification_number() == id) {
                return atm;
            }
        }
        return null;
    }

    public void removeATM(int id) {
        atms.removeIf(atm -> atm.getIdentification_number() == id);
    }
}