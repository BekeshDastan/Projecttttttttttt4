public class ATM {
    private int identification_number;
    private String address;
    private Bank bank;
    public ATM(Bank bank, String address, int identification_number) {
        this.bank = bank;
        this.address = address;
        this.identification_number = identification_number;
    }

    public void setIdentification_number(int id) {
        this.identification_number = id;
    }
    public int getIdentification_number() {
        return this.identification_number;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return this.address;
    }
}