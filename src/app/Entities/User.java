package app.Entities;


import app.annotations.Column;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement statement = null;
    @Column("id")
    private long id;
    @Column
    private String name;
    @Column("age")
    private int age;
    @Column
    private String userName;
    @Column
    private String password;

    @Override
    public String toString(){
        return String.format("User: { id: %d, name: %s, age: %d }", id, name, age);
    }
    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
