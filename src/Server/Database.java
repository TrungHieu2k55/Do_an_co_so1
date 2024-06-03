package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Connection daConnectionLink;

    public Connection getConection(){
        String databaseName = "ticketbookandflim";
        String databaseUserName ="root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/"+databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            daConnectionLink = DriverManager.getConnection(url,databaseUserName,databasePassword);
        } catch (Exception e){
            e.printStackTrace();
        }
        return daConnectionLink;
    }
    public void closeConnection() {
        if (daConnectionLink != null) {
            try {
                daConnectionLink.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
