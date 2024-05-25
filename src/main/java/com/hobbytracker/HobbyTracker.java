
package com.hobbytracker;

import com.hobbytracker.components.CustomScrollBar;
import com.hobbytracker.handlers.ConnectionHandler;
import com.hobbytracker.handlers.UserHandler;
import java.awt.Color;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.UIManager;
import objects.User;

public class HobbyTracker {
    
    private static Frame frame = new Frame();
    private static final Timer timer = new Timer(10000,l -> {checkSQLUpdates();});
    private static boolean updating = false;
    
    private static final ConnectionHandler sql = new ConnectionHandler();
        
    private static final Object lock = new Object();
    private static boolean connected = false;
    
    public static void main(String[] args) {
        
        UIManager.put("ScrollBarUI", CustomScrollBar.class.getName());
        UIManager.put("ComboBox.disabledForeground", new Color(145,145,145));
        UIManager.put("ComboBox.disabledBackground", Color.WHITE);
        UIManager.put("TextField.inactiveBackground", Color.WHITE);
        
        Thread startConnectionThread = new Thread(() -> {
            try{
                updating = true;
                ConnectionHandler.startConnection();
                loadObjects();
                updating = false;
                
                synchronized (lock){
                    connected = true;
                    lock.notify();
                }
                
            }
            catch (SQLException e){
                Logger.getLogger(HobbyTracker.class.getName()).log(Level.SEVERE, null, e);
                
            }
        });
        startConnectionThread.start();
        
        frame.setVisible(true);
    }
    
    private static void loadObjects(){
        
        long startTime = System.currentTimeMillis();
        UserHandler.startManager(sql.getConnection());
        
        long endTime = System.currentTimeMillis();
        System.out.println("\nTime taken to connect : " + (endTime - startTime));
    }
    
    public static boolean isConnected() {
        return connected;
    }
    
    public static Object getLock() {
        return lock;
    }
    
    public static void changeUser(User user){
        frame.changeUser(user);
    }
    
    public static void checkSQLUpdates(){
    
    }
}
