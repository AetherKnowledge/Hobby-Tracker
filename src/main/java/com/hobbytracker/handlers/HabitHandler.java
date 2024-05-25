
package com.hobbytracker.handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objects.Habit;
import objects.Habit.IconType;

public class HabitHandler {
    
    private static Connection con;
    private static boolean isUpdating = false;
    private static boolean hasStarted = false;
    private static ArrayList<Habit> habitList = new ArrayList<>();
    
    public static void startManager(Connection connection){
        con = connection;
        if (habitList.isEmpty()) loadHabitsOnline();
        hasStarted = true;
    }
    
    public static void addUser(Habit habit){
        isUpdating = true;

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
            
            System.out.println("Habit " + habit.getName()+ " added successfully to user " + habit.getEmail() );
            JOptionPane.showMessageDialog(new JFrame(), "Account Registered.","Registration",1);
            
            habitList.add(habit);
        }
        catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(), "Invalid Habit","Error",0);
        }
        
        isUpdating = false;
    }
    
    public static void updateHabit(Habit habit, String oldID){
        isUpdating = true;

        String query = "UPDATE habit SET id = ?, email = ?, area = ?, name = ?, iconType = ?, dateStart = ?, daysOfWeek = ?, timeStart = ?, durationSeconds = ?, ammount WHERE id = ?";
        try {
            PreparedStatement st = con.prepareCall(query);
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
            st.setString(11, oldID);
            st.execute();
            System.out.println("Habit " + habit.getName()+ " added updated from user " + habit.getEmail() );
        } catch (SQLException ex) {
            System.out.println("Habit " + habit.getName()+ " update Failed");
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        isUpdating = false;
    }
    
    public static void removeUser(Habit habit){
        isUpdating = true;
        String queryRegister = "DELETE FROM habit WHERE id = '" + habit.getId()+ "'";
        try {
            PreparedStatement st = con.prepareStatement(queryRegister);
            st.executeUpdate();
            
            System.out.println("Habit " + habit.getName()+ " added removed from user " + habit.getEmail() );
            
            habitList.remove(habit);
            JOptionPane.showMessageDialog(new JFrame(), "Habit Removed.","Registration",1);
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(new JFrame(), "Invalid Habit","Error",0);
        }
        isUpdating = false;
    }
    
    private static void loadHabitsOnline(){
        isUpdating = true;
        habitList.removeAll(habitList);
        
        try {
            String query = "SELECT * FROM user";
            ResultSet resultSet = con.createStatement().executeQuery(query);
            
            while (resultSet.next()) {
                habitList.add(getHabit(resultSet));
            }
            System.out.println("Loading Habits online successful");
        } catch (SQLException | IOException ex) {
            System.out.println("Loading Habits online failed");
            Logger.getLogger(UserHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Loading Habits online complete");
        isUpdating = false;
    }
    
    private static Habit getHabit(ResultSet rs) throws SQLException, IOException{
        String email = rs.getString("id");
        String area = rs.getString("area");
        String name = rs.getString("name");
        LocalDateTime dateStart = rs.getTimestamp("dateStart").toLocalDateTime();
        ArrayList<DayOfWeek> daysOfWeek = (ArrayList<DayOfWeek>)Utilities.deserializeObj(rs.getBytes("id"));
        LocalTime timeStart = rs.getTime("timeStart").toLocalTime();
        int durationSeconds = rs.getInt("durationSeconds");
        int ammount = rs.getInt("ammount");
        
        IconType iconType = null;
        for (IconType type : Habit.IconType.values()) {
            if (rs.getString("iconType").equals(type.name())) {
                iconType = type;
            }
        }
        if (iconType == null) {
            iconType = Habit.IconType.NONE;
        }
        
        return new Habit(email, name, area, iconType, dateStart, daysOfWeek, timeStart, durationSeconds, ammount);
    }

    public static boolean habitUpdating() {
        return isUpdating;
    }

    public static ArrayList<Habit> getUsersList() {
        return habitList;
    }
    
    public static boolean hasStarted() {
        return hasStarted;
    }
}
