package Account;

import Money.Money;

import java.util.List;
import java.util.Map;

public abstract class Account {
    private int accountNumber;
    private String routingNumber;
    private String swiftCode;
    private Map<String,Money> currentBalance;
    private boolean frozen = false; // indicate that if the account is frozen or not.

    public Account(int accountNumber,String routingNumber, String swiftCode){
        this.accountNumber =accountNumber;
        this.routingNumber =routingNumber;
        this.swiftCode = swiftCode;
    }

    public boolean open(){
        this.frozen=false;
        return frozen;
    }

    public boolean close(){
        this.frozen=true;
        return frozen;
    }

    //todo
    public boolean updateBalance(Money money, int value){
        return false;
    }

    //todo
    public boolean transact(Money money, Account account){
        return false;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public Map<String, Money> getCurrentBalance() {
        return currentBalance;
    }

}
