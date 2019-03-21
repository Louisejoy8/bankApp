package app.Entities;


import app.annotations.Column;

import java.time.LocalDate;

public class Transaction {
    @Column
    private long id;
    @Column
    private String senderaccount_id;
    @Column
    private String message;
    @Column
    private float amount;
    @Column
    private LocalDate date;
    @Column
    private String receiveraccount;

    public Transaction (String sender, String message, float amount, String receiver, LocalDate date) {
        receiveraccount = receiver;
        senderaccount_id = sender;
        this.message = message;
        this.amount = amount;
        this.date = date;
    }

    public String getMessage() { return message; }
    public float getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getReceiveraccount(){ return receiveraccount;}
}
