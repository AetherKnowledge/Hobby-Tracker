
package com.hobbytracker.panels.account;

import com.hobbytracker.handlers.Utilities;
import com.hobbytracker.panels.MyPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Entry extends MyPanel{
    
    private Image bgImage;
    private final static JPanel loginHolder = new JPanel();
    private final static JPanel registerHolder = new JPanel();
    private final Login loginPanel = new Login();
    private final Register registerPanel = new Register();
    private static final CardLayout cardLayout = new CardLayout();
    
    public Entry(){
        bgImage = Utilities.getImage("/textures/entryBG.jpg").getImage();
        setBackground(new Color(0,0,0,0));
        setLayout(cardLayout);
        
        initComponents();
    }
    
    private void initComponents() {
        loginHolder.setBackground(new Color(0, 0, 0,0));
        registerHolder.setBackground(new Color(0,0,0,0));
        loginHolder.setLayout(new GridBagLayout());
        registerHolder.setLayout(new GridBagLayout());

        loginHolder.add(loginPanel);
        registerHolder.add(registerPanel);
        add(loginHolder, "LOGIN");
        add(registerHolder,"REGISTER");
        
    }

    @Override 
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        
        if (bgImage.getWidth(null) != getWidth() || bgImage.getHeight(null) != getHeight()) {
            bgImage = bgImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(bgImage));
        }
        
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.drawImage(bgImage, 0,0, this);
        
        super.paint(g);
    }
    
    public static void switchPanel(entryPanels panelName){
        cardLayout.show(loginHolder.getParent(), panelName.name());
    }
    
    public enum entryPanels{
        LOGIN,REGISTER;
    }
    
    @Override
    public void refreshItems() {}

    @Override
    public void resize() {}
}
