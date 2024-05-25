
package com.hobbytracker.components;

import com.hobbytracker.handlers.Utilities;
import java.awt.Image;
import javax.swing.ImageIcon;

public class SideBarLogo extends javax.swing.JPanel {
    
    Image logo = Utilities.getImage("/textures/readingLogo.png").getImage();
    
    public SideBarLogo() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        icon = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(65, 78, 101));
        setLayout(new java.awt.GridBagLayout());

        icon.setText("icon");
        icon.setPreferredSize(new java.awt.Dimension(50, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 9);
        add(icon, gridBagConstraints);
        logo = logo.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon.setText("");
        icon.setIcon(new ImageIcon(logo));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Library System");
        add(jLabel2, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
