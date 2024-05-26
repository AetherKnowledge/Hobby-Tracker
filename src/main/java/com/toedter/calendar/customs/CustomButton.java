
package com.toedter.calendar.customs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;

public class CustomButton extends JButton{
    
    public CustomButton(){
        addChangePointer(this);
    }
    
    public CustomButton(String text){
        this();
        setText(text);
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        if (getModel().isPressed()) g2D.setColor(CalendarColors.getPRESSED_COLOR());
        else if (getModel().isRollover()) g2D.setColor(CalendarColors.getHOVER_COLOR());
        else g2D.setColor(CalendarColors.getMAIN_COLOR());

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2D.setColor(Color.WHITE);
        FontMetrics metrics = g2D.getFontMetrics(getFont());
        int x = (getWidth()-metrics.stringWidth(getText()))/2;
        int y = (getHeight() - metrics.getHeight())/2 + metrics.getAscent();
        g2D.drawString(getText(), x, y);
    }
    
    private void addChangePointer(JComponent component){
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                component.setCursor(Cursor.getDefaultCursor());
            }
        });
    }
}
