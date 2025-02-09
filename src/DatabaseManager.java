import java.sql.*;

public class DatabaseManager {
    private Connection conn;

    public DatabaseManager(String dbname, String user, String pass) {
        db_connection db = new db_connection();
        this.conn = db.connect_to_db(dbname, user, pass);
    }

    private boolean authenticate(int accountNumber, int pin) {
        String sql = "SELECT * FROM accounts WHERE account_number = ? AND pin = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            pstmt.setInt(2, pin);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Authentication error: " + e.getMessage());
            return false;
        }
    }

    public boolean addAccount(int accountNumber, int pin, long balance) {
        String sql = "INSERT INTO accounts (account_number, pin, balance) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            pstmt.setInt(2, pin);
            pstmt.setLong(3, balance);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding account: " + e.getMessage());
            return false;
        }
    }

    public boolean removeAccount(int accountNumber, int pin) {
        if (!authenticate(accountNumber, pin)) {
            System.out.println("Invalid credentials");
            return false;
        }
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error removing account: " + e.getMessage());
            return false;
        }
    }

    public String getAccountInfo(int accountNumber, int pin) {
        if (!authenticate(accountNumber, pin)) {
            return "Invalid credentials";
        }
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return "Account Number: " + rs.getInt("account_number") +
                        "\nBalance: " + rs.getLong("balance");
            } else {
                return "Account not found.";
            }
        } catch (SQLException e) {
            return "Error retrieving account details: " + e.getMessage();
        }
    }

    public boolean depositMoney(int accountNumber, int pin, long amount) {
        if (!authenticate(accountNumber, pin)) {
            System.out.println("Invalid credentials");
            return false;
        }
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, amount);
            pstmt.setInt(2, accountNumber);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error depositing money: " + e.getMessage());
            return false;
        }
    }

    public boolean withdrawMoney(int accountNumber, int pin, long amount) {
        if (!authenticate(accountNumber, pin)) {
            System.out.println("Invalid credentials");
            return false;
        }
        String checkBalance = "SELECT balance FROM accounts WHERE account_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkBalance)) {
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getLong("balance") >= amount) {
                String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(sql)) {
                    updateStmt.setLong(1, amount);
                    updateStmt.setInt(2, accountNumber);
                    updateStmt.executeUpdate();
                    return true;
                }
            } else {
                System.out.println("Insufficient funds");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error withdrawing money: " + e.getMessage());
            return false;
        }
    }

    public boolean addATM(int atmId, String address) {
        String sql = "INSERT INTO atms (atm_id, address) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, atmId);
            pstmt.setString(2, address);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding ATM: " + e.getMessage());
            return false;
        }
    }

    public boolean removeATM(int atmId) {
        String sql = "DELETE FROM atms WHERE atm_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, atmId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error removing ATM: " + e.getMessage());
            return false;
        }
    }
}
