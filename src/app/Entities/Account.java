package app.Entities;

import app.annotations.Column;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Account {

    SimpleStringProperty accountname;
    SimpleLongProperty user_id;
    SimpleDoubleProperty balance;
    SimpleStringProperty type;
    SimpleLongProperty accountnumber;

    public Account(String accountname, Long accountnumber, Double balance, String type) {
        this.accountname = new SimpleStringProperty(accountname);
        this.balance = new SimpleDoubleProperty(balance);
        this.accountnumber = new SimpleLongProperty(accountnumber);
        this.type = new SimpleStringProperty(type);
    }

    public Long getAccountnumber() {
        return accountnumber.getValue();
    }

    public Long getUser_id() {
        return user_id.getValue();
    }

    public Double getBalance() {
        return balance.getValue();
    }
    public String getType(){return  type.getValue(); }

    public void setAccount_name(String account_name){
        this.accountname = new SimpleStringProperty(account_name);
    }

    public String getAccount_name() {
        return accountname.getValue();
    }
}
