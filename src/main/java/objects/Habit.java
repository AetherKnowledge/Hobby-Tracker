
package objects;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Habit {
    
    private final String id;
    private final String email;
    private final String area;
    private final String name;
    private final IconType iconType;
    private final LocalDateTime dateStart;
    private final ArrayList<DayOfWeek> daysOfWeek;
    private final LocalTime timeStart;
    private final int durationSeconds;
    private final int ammount;
    
    public Habit(String email, String name, String area, IconType iconType, LocalDateTime dateStart, ArrayList<DayOfWeek> daysOfWeek, LocalTime timeStart, int durationSeconds, int ammount){
        this.email = email;
        this.name = name;
        this.area = area;
        this.iconType = iconType;
        this.dateStart = dateStart;
        this.daysOfWeek = daysOfWeek;
        this.timeStart = timeStart;
        this.durationSeconds = durationSeconds;
        this.ammount = ammount;
        this.id = email + "@" + "area" + "@" + name;
    }

    public String getName() {
        return name;
    }

    public IconType getIconType() {
        return iconType;
    }

    public ArrayList<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public int getAmmount() {
        return ammount;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getArea() {
        return area;
    }
    
    public enum IconType{
        RUNNING, MEDITATE;
        
        
    }
        
        
}
