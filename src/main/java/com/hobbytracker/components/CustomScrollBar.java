
package com.hobbytracker.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBar extends BasicScrollBarUI{
    
    private static final int THUMB_PADDING = 3;
    
    public static ComponentUI createUI(JComponent c){
        return new CustomScrollBar();
    }
    
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(c.getParent().getBackground());
        g2D.fillRect(0, 0, trackBounds.width, trackBounds.height);
        
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(trackColor);
        g2D.fillRoundRect(0, 0, trackBounds.width - 1, trackBounds.height - 1, 15, 15);
        
    }
    
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(PalleteColors.TRANSPARENT);
        g.fillRect(getThumbBounds().x, getThumbBounds().y, getThumbBounds().width - 1, getThumbBounds().height - 1);
        
        Graphics2D g2D = (Graphics2D) g;
        int thumbX = getThumbBounds().x + THUMB_PADDING;
        int thumbY = getThumbBounds().y + THUMB_PADDING;
        int thumbWidth = getThumbBounds().width - 1 - THUMB_PADDING*2;
        int thumbHeight = getThumbBounds().height - 1 - THUMB_PADDING*2;
        
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(thumbColor);
        g2D.fillRoundRect(thumbX, thumbY, thumbWidth, thumbHeight, 10, 10);
    }
    
    @Override
    protected void configureScrollBarColors(){
        thumbColor = PalleteColors.DROPDOWN;
        trackColor = new Color(224,224,224);
    }
    
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }
    
    private JButton createZeroButton() {
        JButton button = new JButton();
        Dimension zeroDim = new Dimension(0, 0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }
    
    
}
