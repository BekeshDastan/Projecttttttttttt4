public class Account{
    private int number;
    private int PIN_Code;
    private long remainder;
    private Bank bank;
    public Account(Bank bank, int number, int pinCode, long balance) {
        this.bank = bank;
        this.number = number;
        this.PIN_Code = pinCode;
        this.remainder = balance;
    }


    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }
    public void setPinCode(int PIN_Code) {
        this.PIN_Code = PIN_Code;
    }
    public int getPinCode() {
        return PIN_Code;
    }
    public void setRemainder(long remainder) {
        this.remainder = remainder;
    }
    public long getRemainder() {
        return remainder;
    }
    public void replenishAccount(double amount){
        this.remainder += amount;
    }
    public void withdraw_from_account(double amount){
        this.remainder -= amount;
    }

}