package app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Database {
    private static Database ourInstance = new Database();
    public static Database getInstance() {
        return ourInstance;
    }
    private Database() { connectToDb(); }

    final String connectionURL = "jdbc:mysql://localhost/bank?user=root&password=mysql&serverTimezone=UTC";
    private Connection conn = null;
    private HashMap<String, PreparedStatement> preparedStatements = new HashMap<>();


    /** Returns a cached PreparedStatement if possible, else caches it for future use */
    public PreparedStatement prepareStatement(String SQLQuery){
        PreparedStatement ps = preparedStatements.get(SQLQuery);
        if (ps == null) {
            System.out.println("Caching " + SQLQuery);
            try {
                ps = conn.prepareStatement(SQLQuery);
                preparedStatements.put(SQLQuery, ps);
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return ps;
    }

    private void connectToDb(){
        try { conn = DriverManager.getConnection(connectionURL); }
        catch (SQLException e) { e.printStackTrace(); }
    }
}
