
package com.hobbytracker.handlers;

import com.hobbytracker.Frame;
import com.hobbytracker.HobbyTracker;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.Timestamp;
import java.awt.Image;
import java.util.NoSuchElementException;
import objects.User;

public final class UserHandler{

    private static Connection con;
    private static boolean usersUpdating = false;
    private static boolean hasStarted = false;
    private static ArrayList<User> usersList = new ArrayList<>();
    private static User currentUser;
    
    public static void startManager(Connection connection){
        con = connection;
        if (usersList.isEmpty()) loadUsersOnline();
        hasStarted = true;
    }
    
    public static void addUser(User user){
        usersUpdating = true;

        try {
            String email,password,fullName;
            int status;
            byte[] imageData = Utilities.serializeImage(user.getIcon());
            LocalDateTime dateJoined = user.getDateJoined();

            email = user.getEmail();
            password = user.getPassword();
            fullName = user.getFullName();
            status = 1;
            
            if (user.isImageDefault()) {
                imageData = null;
            }
            
            String queryRegister = "INSERT into user(email, password, imageData, fullName, dateJoined, status) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement st = con.prepareStatement(queryRegister);
            st.setString(1, email);
            st.setString(2, password);
            st.setBytes(3, imageData);
            st.setString(4, fullName);
            st.setTimestamp(5, Timestamp.valueOf(dateJoined));
            st.setInt(6, status);
            st.executeUpdate();
            
            System.out.println("User " + user.getUserName() + " added successfully");
            JOptionPane.showMessageDialog(new JFrame(), "Account Registered.","Registration",1);
            
            usersList.add(user);
        }
        catch (SQLException | IOException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(), "Invalid Account","Error",0);
        }
        
        usersUpdating = false;
    }
    
    public static void updateUser(User user, String oldEmail){
        usersUpdating = true;

        String query = "UPDATE user SET email = ?, password = ?, fullName = ?, imageData = ?, lastUpdated = ? WHERE email = ?";
        try {
            byte[] imageData = null;
            if (!user.isImageDefault()) {
                imageData = Utilities.serializeImage(user.getIcon());
            }
            
            PreparedStatement st = con.prepareCall(query);
            st.setString(1, user.getEmail());
            st.setString(2, user.getPassword());
            st.setString(3, user.getFullName());
            st.setBytes(4, imageData);
            st.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            st.setString(6, oldEmail);
            st.execute();
            
            for (int i = 0; i < usersList.size(); i++) {
                User oldUser = usersList.get(i);
                if (oldUser.getEmail().equals(oldEmail)) {
                    usersList.set(i, user);
                }
            }
            if (currentUser != null && currentUser.getEmail().equals(oldEmail)) {
                currentUser = user;
            }
            
        } catch (SQLException | IOException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usersUpdating = false;
    }
    
    public static void removeUser(User user){
        usersUpdating = true;
        String queryRegister = "DELETE FROM user WHERE email = '" + user.getEmail() + "'";
        try {
            PreparedStatement st = con.prepareStatement(queryRegister);
            st.executeUpdate();
            
            System.out.println("User " + user.getUserName() + " removed successfully");
            
            usersList.remove(user);
            JOptionPane.showMessageDialog(new JFrame(), "Account Removed.","Registration",1);
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(), "Invalid Account","Error",0);
        }
        usersUpdating = false;
    }
    
    private static void loadUsersOnline(){
        usersUpdating = true;
        usersList.removeAll(usersList);
        
        try {
            String query = "SELECT * FROM user";
            ResultSet resultSet = con.createStatement().executeQuery(query);
            
            while (resultSet.next()) {
                usersList.add(getUser(resultSet));
            }
        } catch (SQLException | IOException ex) {
            System.out.println("Loading Users online failed");
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Loading Users online complete");
        usersUpdating = false;
    }
    
    private static User seacrhUserOnOnline(String email) throws SQLException, Exception{
        usersUpdating = true;
        String query = "SELECT * FROM user WHERE email = '" + email +"'";
        ResultSet resultSet = con.createStatement().executeQuery(query);

        while (resultSet.next()) {

            usersUpdating = false;
            return getUser(resultSet);
        }
        
        usersUpdating = false;
        throw new Exception("User " + email + " not found");
        
    }
    
    private static User getUser(ResultSet rs) throws SQLException, IOException{
        String email = rs.getString("email");
        String password = rs.getString("password");
        byte[] imgArray = rs.getBytes("imageData");
        String fullName = rs.getString("fullName");
        LocalDateTime dateJoined = rs.getTimestamp("dateJoined").toLocalDateTime();
        LocalDateTime lastUpdated = rs.getTimestamp("lastUpdated").toLocalDateTime();
        
        boolean isImageDefault = imgArray == null;
        Image userImg;
        if (!isImageDefault) {
            userImg = Utilities.deserializeImage(imgArray);
        }
        else{
            userImg = Utilities.createUserLogo(fullName.trim().toUpperCase().charAt(0));
        }
        
        return new User(email, password, fullName, userImg, dateJoined,lastUpdated, isImageDefault);
    }
    
    public static User searchUser(String email){
        for (User user : usersList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        
        try {
            return seacrhUserOnOnline(email);
        } catch (Exception ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static boolean hasUsersUpdated(){
        boolean hasUsersUpdated = false;
        usersUpdating = true;
        try {
            for (int i = 0; i < usersList.size(); i++) {
                User user = usersList.get(i);
                String query = "SELECT lastUpdated FROM user WHERE email = '" + user.getEmail()+ "'";
                ResultSet resultSet = con.createStatement().executeQuery(query);

                while (resultSet.next()) {
                    if (!user.getLastUpdated().toString().equals(resultSet.getTimestamp("lastUpdated").toLocalDateTime().toString())) {
                        String getUser = "SELECT * FROM user WHERE email = '" + user.getEmail()+ "'";
                        ResultSet getUsersResult = con.createStatement().executeQuery(getUser);
                        getUsersResult.next();
                        
                        User changedUser = getUser(getUsersResult);
                        usersList.set(i, changedUser);
                        if (currentUser != null && user.getEmail().equals(currentUser.getEmail())) {
                            System.out.println("Changed current User");
                            if (changedUser.getPassword().equals(currentUser.getPassword())) {
                                currentUser = changedUser;
                                HobbyTracker.changeUser(changedUser);
                            }
                            else{
                                JOptionPane.showMessageDialog(new JFrame(), "Password changed");
                                Frame.switchPanel(Frame.PanelTypes.ENTRY);
                                currentUser = null;
                            }
                        }
                        
                        System.out.println("User "+ getUsersResult.getString("fullName") + " Changed");
                        hasUsersUpdated = true;
                    }
                }
            }
            
            String query = "SELECT email FROM user";
            ResultSet resultSet = con.createStatement().executeQuery(query);
            
            ArrayList<String> cacheUserEmails = new ArrayList<>();
            for (User user : usersList) {
                cacheUserEmails.add(user.getEmail());
            }
            
            ArrayList<String> serverUserEmails = new ArrayList<>();
            while (resultSet.next()){
                serverUserEmails.add(resultSet.getString("email"));
            }
            
            if (usersList.removeIf(user -> !serverUserEmails.contains(user.getEmail()))) {
                hasUsersUpdated = true;
                System.out.println("User Removed");
            }
            
            if (serverUserEmails.size() > usersList.size()) {
                for (String email : serverUserEmails) {
                    if (!cacheUserEmails.contains(email)) {
                        String getBook = "SELECT * FROM user WHERE email = '" + email + "'";
                        ResultSet getUserResult = con.createStatement().executeQuery(getBook);
                        getUserResult.next();
                        
                        usersList.add(getUser(getUserResult));
                        hasUsersUpdated = true;
                        System.out.println("User "+ getUserResult.getString("fullName") + " Added");
                    }
                }
            }
            
        } catch (SQLException | IOException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (currentUser != null && !doesUserStillExist()) {
            JOptionPane.showMessageDialog(new JFrame(), "User no longer Exists");
            Frame.switchPanel(Frame.PanelTypes.ENTRY);
            currentUser = null;
        }
        
        usersUpdating = false;
        if (hasUsersUpdated) {
            System.out.println("Users updated");
        }
        return hasUsersUpdated;
    }
    
    private static boolean doesUserStillExist(){
        for (User user : usersList) {
            if (currentUser.getEmail().equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }
    
    public static void updateOnlineStatus(User user){
        usersUpdating = true;
        try {
            String updateSql = "UPDATE user SET status = ? WHERE email = ?";
            PreparedStatement updateStatement = con.prepareStatement(updateSql);
            updateStatement.setBoolean(1, true);
            updateStatement.setString(2, user.getEmail());
            updateStatement.executeUpdate();
            //System.out.println(Timestamp.valueOf(LocalDateTime.now()));
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        usersUpdating = false;
    }  
    
    public static boolean updateAllOnlineUsers(){
        usersUpdating = true;
        boolean hasChanged = false;
        for (User user : usersList) {
            String query = "SELECT status FROM user WHERE email = '" + user.getEmail() + "'";
            try {
                ResultSet rs = con.createStatement().executeQuery(query);
                while(rs.next()){
                    if (rs.getBoolean("status") != user.isOnline()) {
                        user.setIsOnline(rs.getBoolean("status"));
                        hasChanged = true;
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        usersUpdating = false;
        if (hasChanged) {
            System.out.println("Online users updated");
        }
        return hasChanged;
    }
    
    public static int getOnlineCount(){
        int onlineCount = 0;
        for (User user : usersList) {
            if (user.isOnline()) {
                onlineCount++;
            }
        }
        usersUpdating = false;
        return onlineCount;
    }
    
    public static boolean isLoginSuccessful(String username, char[] password) {
        try{
            User user = usersList.stream().filter(email -> email.getEmail().equals(username)).findFirst().get();
            if (Utilities.verifyPassword(user.getPassword(), password)) {
                changeUser(user);
                return true;
            }
        }
        catch (NoSuchElementException e){
            JOptionPane.showMessageDialog(new JFrame(), "Invalid User or Password");
        }
        return false;
    }
    
    public static boolean changeUser(User user){
        try{
            currentUser = user;
            HobbyTracker.changeUser(user);
            return true;
        }
        catch(Exception e){}
        return false;
    }
    
    public static boolean doesUserExist(String email, String studentID) {
        for (User user : usersList) {
            if (email.equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUsersUpdating() {
        return usersUpdating;
    }

    public static ArrayList<User> getUsersList() {
        return usersList;
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }

    public static int getUserCount() {
        return usersList.size();
    }

    public static boolean hasStarted() {
        return hasStarted;
    }

}
