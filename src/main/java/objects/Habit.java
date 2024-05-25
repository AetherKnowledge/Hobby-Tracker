
package objects;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

public class Habit {
    
    private final String name;
    private final IconType iconType;
    private final ArrayList<DayOfWeek> daysOfWeek;
    private final LocalTime timeStart;
    private final int durationSeconds;
    private final int ammount;
    
    public Habit(String name, IconType iconType, ArrayList<DayOfWeek> daysOfWeek, LocalTime timeStart, int durationSeconds, int ammount){
        this.name = name;
        this.iconType = iconType;
        this.daysOfWeek = daysOfWeek;
        this.timeStart = timeStart;
        this.durationSeconds = durationSeconds;
        this.ammount = ammount;
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
    
    public enum IconType{
        RUNNING, MEDITATE;
        
        
    }
        
        
}
