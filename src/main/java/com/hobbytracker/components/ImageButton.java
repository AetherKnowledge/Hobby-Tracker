
package com.hobbytracker.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton extends JButton{

    private Image image;
    
    public ImageButton(Image image){
        this.image = image;
        this.setBackground(PalleteColors.TRANSPARENT);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
    
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(PalleteColors.SIDEBAR_MAIN_COLOR);
        g2D.fillRoundRect(0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        
        if (getWidth() != image.getWidth(this) || getHeight() != image.getHeight(this)) {
            image = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            image = new ImageIcon(image).getImage();
        }
        
        Shape circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
        BufferedImage maskedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D maskedG2D = maskedImage.createGraphics();
        maskedG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        maskedG2D.setClip(circle);
        maskedG2D.drawImage(image, 0, 0, this);
        maskedG2D.dispose();
        
        g2D.drawImage(maskedImage, 0,0, this);
        g2D.setColor(PalleteColors.SIDEBAR_MAIN_COLOR);
        
        int borderWidth = 2;
        g2D.setStroke(new BasicStroke(borderWidth));
        g2D.drawRoundRect(1, 1, getWidth()-3, getHeight()-2, getWidth()-3, getHeight()-2);
        
        if(getModel().isPressed()){
            g2D.setColor(new Color(255,255,255,120));
            g2D.fillRoundRect(0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        }
        else if (getModel().isRollover()) {
            g2D.setColor(new Color(255,255,255,80));
            g2D.fillRoundRect(0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        }
        
    }
    
    public void changeImage(Image image) {
        this.image = image;
    }
    
}
