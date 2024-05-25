
package com.hobbytracker.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

public class LineBorderSide extends AbstractBorder{

    private final Color newColor;
    private final Side side;
    private final BasicStroke stroke;
    private int strokePad;
    private Insets insets;
    
    public LineBorderSide(Side side){
        this(side,4,Color.BLACK);
    }

    public LineBorderSide(Side side,int thickness,Color newColor) {
        this.newColor = newColor;
        this.side = side;
        this.stroke = new BasicStroke(thickness);
        int pad = strokePad;
        insets = new Insets(pad, pad, pad, pad);
    }
    
    public enum Side{
        NORTH,SOUTH,EAST,WEST,EASTSOUTH;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public void paintBorder(Component c,Graphics g,int x, int y,int width, int height) {
        Graphics2D g2D = (Graphics2D) g;
        Component parent  = c.getParent();
        
        
        g2D.setColor(newColor);
        g2D.setStroke(stroke);
        switch(side){
            case NORTH -> drawNorth(g2D, parent);
            case SOUTH -> drawSouth(g2D, parent);
            case EAST -> drawEast(g2D, parent);
            case WEST -> drawWest(g2D, parent);
            case EASTSOUTH ->{
                drawEast(g2D, parent);
                drawSouth(g2D, parent);
            }
        }
    }
    
    private void drawNorth(Graphics2D g2D, Component parent){
        int parentWidth = parent.getWidth() - 1;
        g2D.drawLine(0, 0, parentWidth, 0);
    }
    
    private void drawSouth(Graphics2D g2D, Component parent){
        int parentWidth = parent.getWidth() - 1;
        int parentHeight = parent.getHeight() - 1;
        g2D.drawLine(0, parentHeight, parentWidth, parentHeight);
    }
    
    private void drawEast(Graphics2D g2D, Component parent){
        int parentWidth = parent.getWidth() - 1;
        int parentHeight = parent.getHeight() - 1;
        g2D.drawLine(parentWidth, 0, parentWidth, parentHeight);
    }
    
    private void drawWest(Graphics2D g2D, Component parent){
        int parentHeight = parent.getHeight() - 1;
        g2D.drawLine(0, 0, 0, parentHeight);
    }
        

}
