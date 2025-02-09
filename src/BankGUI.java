import javax.swing.*;
import java.awt.*;

public class BankGUI {
    private final DatabaseManager dbManager = new DatabaseManager("projdb", "postgres", "1234");

    public BankGUI() {
        createGUI();
    }

    private void createGUI() {
        JFrame frame = new JFrame("Bank System");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));

        JButton addAccountBtn = new JButton("Add Account");
        JButton removeAccountBtn = new JButton("Remove Account");
        JButton showAccountBtn = new JButton("Show Account Info");
        JButton depositBtn = new JButton("Deposit Money");
        JButton withdrawBtn = new JButton("Withdraw Money");
        JButton addATMBtn = new JButton("Add ATM");
        JButton removeATMBtn = new JButton("Remove ATM");
        JButton exitBtn = new JButton("Exit");

        panel.add(addAccountBtn);
        panel.add(removeAccountBtn);
        panel.add(showAccountBtn);
        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(addATMBtn);
        panel.add(removeATMBtn);
        panel.add(exitBtn);

        frame.add(panel);
        frame.setVisible(true);

        addAccountBtn.addActionListener(e -> addAccount());
        removeAccountBtn.addActionListener(e -> removeAccount());
        showAccountBtn.addActionListener(e -> showAccount());
        depositBtn.addActionListener(e -> depositMoney());
        withdrawBtn.addActionListener(e -> withdrawMoney());
        addATMBtn.addActionListener(e -> addATM());
        removeATMBtn.addActionListener(e -> removeATM());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private int requestPIN() {
        try {
            return Integer.parseInt(JOptionPane.showInputDialog("Enter PIN:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid PIN format!");
            return -1;
        }
    }

    private void addAccount() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog("Enter Unique Account Number:"));
            int pin = requestPIN();
            long balance = Long.parseLong(JOptionPane.showInputDialog("Enter Initial Balance:"));
            if (dbManager.addAccount(number, pin, balance)) {
                JOptionPane.showMessageDialog(null, "Account Added Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Error Adding Account!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input!");
        }
    }

    private void removeAccount() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog("Enter Account Number to Remove:"));
            int pin = requestPIN();
            if (dbManager.removeAccount(number, pin)) {
                JOptionPane.showMessageDialog(null, "Account Removed Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect PIN or Account Not Found!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input!");
        }
    }

    private void addATM() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Unique ATM ID:"));
            String address = JOptionPane.showInputDialog("Enter ATM Address:");
            if (dbManager.addATM(id, address)) {
                JOptionPane.showMessageDialog(null, "ATM Added Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Error Adding ATM!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input!");
        }
    }

    private void removeATM() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ATM ID to Remove:"));
            if (dbManager.removeATM(id)) {
                JOptionPane.showMessageDialog(null, "ATM Removed Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "ATM ID Not Found!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input!");
        }
    }

    private void showAccount() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog("Enter Account Number:"));
            int pin = requestPIN();
            String info = dbManager.getAccountInfo(number, pin);
            JOptionPane.showMessageDialog(null, info);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input!");
        }
    }

    private void depositMoney() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog("Enter Account Number:"));
            int pin = requestPIN();
            long amount = Long.parseLong(JOptionPane.showInputDialog("Enter Amount to Deposit:"));
            if (dbManager.depositMoney(number, pin, amount)) {
                JOptionPane.showMessageDialog(null, "Deposit Successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect PIN or Error Occurred!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input!");
        }
    }

    private void withdrawMoney() {
        try {
            int number = Integer.parseInt(JOptionPane.showInputDialog("Enter Account Number:"));
            int pin = requestPIN();
            long amount = Long.parseLong(JOptionPane.showInputDialog("Enter Amount to Withdraw:"));
            if (dbManager.withdrawMoney(number, pin, amount)) {
                JOptionPane.showMessageDialog(null, "Withdrawal Successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect PIN or Insufficient Funds!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input!");
        }
    }

    public static void main(String[] args) {
        new BankGUI();
    }
}
