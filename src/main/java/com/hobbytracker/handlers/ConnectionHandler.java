
package com.hobbytracker.handlers;

import com.mysql.cj.jdbc.Driver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionHandler{

    private static final String DB_NAME = "librarysystem";
    private static final String DB_DRIVER = Driver.class.getName();
    private static String DB_URL = "jdbc:mysql://localhost/"+DB_NAME;
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    
    private static final boolean ONLINE_MODE = false;
    private static final boolean AIVEN = true;
    private static final String DB_HTTP = "https://related-seemingly-goose.ngrok-free.app" + "/ip.txt";
    
    private static Connection con;
    
    public boolean startConnection() throws SQLException{
        if (AIVEN) {
            DB_URL = "jdbc:mysql://librarysystem-librarysystem.e.aivencloud.com:15306/"+DB_NAME;
            DB_USERNAME = "avnadmin";
            DB_PASSWORD = "AVNS_8fHGFjNWVnbsdD9IaMb";
        }
        else if (ONLINE_MODE) {
            DB_URL = "jdbc:mysql://" + getTCPAddress() + "/" + DB_NAME;
        }
        
        try {
            Class.forName(DB_DRIVER);
            
            //Url, Username, Password
            con = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found");
            return false;
        }
        
        if (con != null) {
            System.out.println("\nConnection Successful");
            return true;
        }
        else return false;
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public String getTCPAddress(){
        StringBuilder tcpAddress = new StringBuilder();
        
        try {
            URI uri = new URI(DB_HTTP);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            // Check if the response code is OK
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    // Read each line from the input stream and append it to the StringBuilder
                    String line;
                    while ((line = reader.readLine()) != null) {
                        tcpAddress.append(line);
                    }
                }
                
                System.out.println("IP : " + tcpAddress);
            } else {
                System.out.println("Failed to get IP. HTTP response code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (IOException e) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, e);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tcpAddress.toString();
    }
    
    public static String getAddress() {
        String address = DB_HTTP;
        address = address.replaceAll("https://", "");
        address = address.replaceAll("/ip.txt", "");
        
        return address;
    }
    
}
