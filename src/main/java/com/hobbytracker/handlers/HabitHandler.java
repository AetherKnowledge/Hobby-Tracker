
package com.hobbytracker.handlers;

import java.awt.Image;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objects.Habit;
import objects.User;

public class HabitHandler {
    
    private static Connection con;
    private static boolean usersUpdating = false;
    private static boolean hasStarted = false;
    private static ArrayList<Habit> habitList = new ArrayList<>();
    private static User currentUser;
    
    public static void startManager(Connection connection){
        con = connection;
        if (habitList.isEmpty()) loadUsersOnline();
        hasStarted = true;
    }
    
    public static void addUser(Habit habit){
        usersUpdating = true;

        try {
            String queryRegister = "INSERT into habit(id, email, area, name, iconType, dateStart, daysOfWeek, timeStart, durationSeconds, ammount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement st = con.prepareStatement(queryRegister);
            st.setString(1, habit.getId());
            st.setString(2, habit.getEmail());
            st.setString(3, habit.getArea());
            st.setString(4, habit.getName());
            st.setString(5, habit.getIconType().name());
            st.setTimestamp(6, Timestamp.valueOf(habit.getDateStart()));
            st.setBytes(7, Utilities.serializeObj(habit.getDaysOfWeek()));
            st.setTime(8, Time.valueOf(habit.getTimeStart()));
            st.setInt(9, habit.getDurationSeconds());
            st.setInt(10, habit.getAmmount());
            st.executeUpdate();
            
            System.out.println("User " + habit.getName()+ " added successfully to user " + habit.getEmail() );
            JOptionPane.showMessageDialog(new JFrame(), "Account Registered.","Registration",1);
            
            habitList.add(habit);
        }
        catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(), "Invalid Habit","Error",0);
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
            
//            for (int i = 0; i < habitList.size(); i++) {
//                User oldUser = habitList.get(i);
//                if (oldUser.getEmail().equals(oldEmail)) {
//                    habitList.set(i, user);
//                }
//            }
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
            
            habitList.remove(user);
            JOptionPane.showMessageDialog(new JFrame(), "Account Removed.","Registration",1);
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(), "Invalid Account","Error",0);
        }
        usersUpdating = false;
    }
    
    private static void loadUsersOnline(){
        usersUpdating = true;
        habitList.removeAll(habitList);
        
        try {
            String query = "SELECT * FROM user";
            ResultSet resultSet = con.createStatement().executeQuery(query);
            
            while (resultSet.next()) {
                habitList.add(getUser(resultSet));
            }
        } catch (SQLException | IOException ex) {
            System.out.println("Loading Users online failed");
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Loading Users online complete");
        usersUpdating = false;
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



    public static boolean habitUpdating() {
        return usersUpdating;
    }

    public static ArrayList<Habit> getUsersList() {
        return habitList;
    }
    
    public static boolean hasStarted() {
        return hasStarted;
    }
}
