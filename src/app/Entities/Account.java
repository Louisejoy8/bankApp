package app.Entities;

import app.annotations.Column;

public class Account {
    @Column("accountnumber")
    private String accountnumber;
    @Column
    private Long user_id;
    @Column
    private Double amount;
    @Column
    private String account_name;

    public String getAccountnumber() {
        return accountnumber;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Double getAmount() {
        return amount;
    }

    public String getAccount_name() {
        return account_name;
    }
}
