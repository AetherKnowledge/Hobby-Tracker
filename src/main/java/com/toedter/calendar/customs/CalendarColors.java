
package com.toedter.calendar.customs;

import java.awt.Color;

public class CalendarColors {
    
    private static Color MAIN_COLOR = new Color(65,78,101);
    private static Color ACCENT_COLOR = new Color(52,63,82);
    private static Color HOVER_COLOR = new Color(52,63,82);
    private static Color PRESSED_COLOR = new Color(43,49,65);
    
    private static Color ARROW_BUTTON_BACKGROUND_COLOR = new Color(224,224,224);
    private static Color ARROW_BUTTON_HOVER_COLOR = new Color(180,180,180);
    private static Color ARROW_BUTTON_PRESSED_COLOR = new Color(120,120,120);
    
    private static Color TEXT_COLOR = Color.WHITE;
    private static Color TRANSPARENT = new Color(0,0,0,0);

    public static Color getMAIN_COLOR() {
        return MAIN_COLOR;
    }

    public static void setMAIN_COLOR(Color color) {
        MAIN_COLOR = color;
    }

    public static Color getACCENT_COLOR() {
        return ACCENT_COLOR;
    }

    public static void setACCENT_COLOR(Color color) {
        ACCENT_COLOR = color;
    }

    public static Color getHOVER_COLOR() {
        return HOVER_COLOR;
    }

    public static void setHOVER_COLOR(Color color) {
        HOVER_COLOR = color;
    }

    public static Color getPRESSED_COLOR() {
        return PRESSED_COLOR;
    }

    public static void setPRESSED_COLOR(Color color) {
        PRESSED_COLOR = color;
    }

    public static Color getARROW_BUTTON_BACKGROUND_COLOR() {
        return ARROW_BUTTON_BACKGROUND_COLOR;
    }

    public static void setARROW_BUTTON_BACKGROUND_COLOR(Color color) {
        ARROW_BUTTON_BACKGROUND_COLOR = color;
    }

    public static Color getARROW_BUTTON_HOVER_COLOR() {
        return ARROW_BUTTON_HOVER_COLOR;
    }

    public static void setARROW_BUTTON_HOVER_COLOR(Color color) {
        ARROW_BUTTON_HOVER_COLOR = color;
    }

    public static Color getARROW_BUTTON_PRESSED_COLOR() {
        return ARROW_BUTTON_PRESSED_COLOR;
    }

    public static void setARROW_BUTTON_PRESSED_COLOR(Color color) {
        ARROW_BUTTON_PRESSED_COLOR = color;
    }

    public static Color getTEXT_COLOR() {
        return TEXT_COLOR;
    }

    public static void setTEXT_COLOR(Color color) {
        TEXT_COLOR = color;
    }

    public static Color getTRANSPARENT() {
        return TRANSPARENT;
    }

    public static void setTRANSPARENT(Color color) {
        TRANSPARENT = color;
    }

}