package app.account;

import app.annotations.Column;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

//Class made for creating objects to display in tableview

public class ModelTable {
    private SimpleStringProperty senderaccount;
    private SimpleStringProperty message;
    private SimpleStringProperty receiveraccount;
    private SimpleStringProperty time;
    private SimpleDoubleProperty amount;

    public ModelTable(String senderaccount, String message, String time, String receiveraccount, Double amount) {
        this.senderaccount = new SimpleStringProperty(senderaccount);
        this.message = new SimpleStringProperty(message);
        this.time = new SimpleStringProperty(time);
        this.receiveraccount = new SimpleStringProperty(receiveraccount);
        this.amount = new SimpleDoubleProperty(amount);

    }

    public String getSenderaccount() {
        return senderaccount.getValue();
    }

    public void setSenderaccount(String senderaccount) {
        this.senderaccount = new SimpleStringProperty(senderaccount);

    }

    public String getMessage() {
        return message.getValue();
    }

    public void setMessage(String message) {
        this.message = new SimpleStringProperty(message);

    }

    public String getReceiveraccount() {
        return receiveraccount.getValue();
    }

    public void setReceiveraccount(String receiveraccount) {
        this.receiveraccount = new SimpleStringProperty(receiveraccount);

    }

    public String getTime() {
        return time.getValue();
    }

    public void setTime(String time) {
        this.time = new SimpleStringProperty(time);

    }

    public Double getAmount() {
        return amount.getValue();
    }

    public void setAmount(Double amount) {
        this.amount = new SimpleDoubleProperty(amount);

    }

}
