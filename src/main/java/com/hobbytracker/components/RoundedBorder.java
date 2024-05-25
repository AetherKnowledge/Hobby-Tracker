
package com.hobbytracker.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder{

    private final int radii;
    private int thickness;
    private BasicStroke stroke = null;
    private Insets insets = null;
    private int strokePad;
    private Color newColor;
    
    public RoundedBorder(){
        this(8,4,null);
    }
    
    public RoundedBorder(int radii,int thickness) {
        this(radii,thickness,null);
    }

    public RoundedBorder(int radii,int thickness,Color newColor) {
        this.newColor = newColor;
        this.radii = radii;
        this.thickness = thickness;
        stroke = new BasicStroke(thickness);
        int pad = radii + strokePad;
        insets = new Insets(pad, pad, pad, pad);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        return getBorderInsets(c);
    }

    @Override
    public void paintBorder(Component c,Graphics g,int x, int y,int width, int height) {
        Graphics2D g2D = (Graphics2D) g;

        RoundRectangle2D.Double roundedRect = new RoundRectangle2D.Double(
                0 + strokePad,
                0 + strokePad,
                width - thickness,
                height - thickness,
                radii,
                radii);
        
        Area area = new Area(roundedRect);

        Component parent  = c.getParent();
        
        Color bg;
        if (newColor != null) bg = newColor;
        else bg = parent.getBackground();
        
        Shape clip = g2D.getClip();
        Area borderRegion = new Area(clip);

        borderRegion.subtract(area);
        
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setClip(borderRegion);
        g2D.setColor(bg);
        g2D.fillRect(0, 0, width, height);
        g2D.setClip(clip);
        
        g2D.setColor(Color.LIGHT_GRAY);
        g2D.setStroke(stroke);
        g2D.draw(area);

    }

}
