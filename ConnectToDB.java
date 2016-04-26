
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kevin Yang
 */
public class ConnectToDB {
    
    // Constructor for ConnectToDB
    public ConnectToDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;
        }
    }
    
    // Connect to DB
    private Connection getConnection() {
        java.sql.Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/test", "postgres",
                    "password");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        return connection;
    }
    
    
    // Executes query string and returns the result.
    public ResultSet execQuery (String query) throws SQLException {
        Connection con = this.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        Statement statement = con.createStatement();

        ResultSet result = statement.executeQuery(query);
        con.close(); // Not sure if allowed to close connection before result.
        // MAKE SURE to close result after using it! result.close();
        return result;
    }
    
    // Executes an update on the DB. This can be for deletes and changing data.
    public void execUpdate(String query) throws SQLException{
        Connection con = this.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate(query);
        statement.close();
        con.commit();
        con.close();
    }
}
