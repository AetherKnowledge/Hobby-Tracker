    
package com.hobbytracker.panels.account;

import com.hobbytracker.Frame;
import com.hobbytracker.HobbyTracker;
import com.hobbytracker.components.PalleteColors;
import com.hobbytracker.components.RoundedBorder;
import com.hobbytracker.handlers.UserHandler;
import com.hobbytracker.handlers.Utilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class Login extends JPanel{
    
    private Image emailIcon = Utilities.getImage("/textures/emailIcon.png").getImage();
    private Image passwordIcon = Utilities.getImage("/textures/passwordIcon.png").getImage();
    private Image logo = Utilities.getImage("/textures/readingLogo.png").getImage();
    private Image loginIcon = Utilities.getImage("/textures/login.png").getImage();
    private Image showPasswordIcon = Utilities.getImage("/textures/showPassword.png").getImage();
    private Image hidePasswordIcon = Utilities.getImage("/textures/hidePassword.png").getImage();
    private final JTextField emailTextField = new JTextField();
    private final JButton forgetPasswordButton;
    private final JLabel lcupLabel = new JLabel();
    private final JLabel lcupLogoLabel = new JLabel();
    private final JLabel emailIconLabel = new JLabel();
    private final JLabel passwordIconLabel = new JLabel();;
    private final JLabel loginLabel = new JLabel();
    private final JLabel loginLogoLabel = new JLabel();
    private final JPanel loginPanel = new JPanel();
    private final JPanel emailPanel;
    private final JPanel passwordPanel;
    private final JButton loginButton;
    private final JToggleButton togglePassVisible;
    private final JButton createAccountButton;
    private final JPasswordField passwordTextField = new JPasswordField();
    private final int arcWidth = 45;
    private final int arcHeight = 60;
    
    public Login(){
        this.setBorder(new RoundedBorder(8,0));
        this.setBackground(new Color(153, 255, 255,0));
        this.setPreferredSize(new Dimension(370, 514));
        this.setRequestFocusEnabled(false);
        this.setLayout(new BorderLayout());
        
        emailIcon = emailIcon.getScaledInstance(18, 14, Image.SCALE_SMOOTH);
        passwordIcon = passwordIcon.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        showPasswordIcon = showPasswordIcon.getScaledInstance(22, 18, Image.SCALE_SMOOTH);
        hidePasswordIcon = hidePasswordIcon.getScaledInstance(22, 18, Image.SCALE_SMOOTH);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(showPasswordIcon));
        
        logo = logo.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        
        loginIcon = Utilities.changeImageColor(loginIcon, PalleteColors.DROPDOWN);
        loginIcon = loginIcon.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        
        emailPanel = new JPanel(){
            @Override
            public void paint(Graphics g){
                Graphics2D g2D = (Graphics2D) g;
                g2D.setColor(PalleteColors.ENTRY_BACKGROUND_COLOR);
                g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2D.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
                super.paint(g);
            }
        };
        passwordPanel = new JPanel(){
            @Override
            public void paint(Graphics g){
                Graphics2D g2D = (Graphics2D) g;
                g2D.setColor(PalleteColors.ENTRY_BACKGROUND_COLOR);
                g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2D.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
                super.paint(g);
            }
        };
        loginButton = new JButton(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                Graphics2D g2D = (Graphics2D) g;
                
                if (getModel().isPressed()) g2D.setColor(PalleteColors.DROPDOWN_PRESSED);
                else if (getModel().isRollover()) g2D.setColor(PalleteColors.DROPDOWN);
                else g2D.setColor(PalleteColors.SIDEBAR_MAIN_COLOR);
                
                g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2D.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                
                g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2D.setColor(Color.WHITE);
                FontMetrics metrics = g2D.getFontMetrics(getFont());
                int x = (getWidth()-metrics.stringWidth(getText()))/2;
                int y = (getHeight() - metrics.getHeight())/2 + metrics.getAscent();
                g2D.drawString(getText(), x, y);
            }
        };

        togglePassVisible = new JToggleButton(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                Graphics2D g2D = (Graphics2D) g;
                
                g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isSelected()) g2D.drawImage(showPasswordIcon, 0,0, null);
                else g2D.drawImage(hidePasswordIcon, 0,0, null);
            }
        };
        
        forgetPasswordButton = new JButton(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                
                if (getModel().isPressed()) setForeground(PalleteColors.DROPDOWN_PRESSED);
                else if (getModel().isRollover()) setForeground(PalleteColors.ENTRY_TEXT_HOVERED_COLOR);
                else setForeground(PalleteColors.ENTRY_TEXT_LIGHT_COLOR);
            }
        };
        
        createAccountButton = new JButton(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                
                if (getModel().isPressed()) setForeground(PalleteColors.DROPDOWN_PRESSED);
                else if (getModel().isRollover()) {
                    setForeground(PalleteColors.ENTRY_TEXT_HOVERED_COLOR);
                    
                }
                else setForeground(PalleteColors.ENTRY_TEXT_LIGHT_COLOR);
            }
        };
        
        Utilities.addChangePointer(loginButton);
        Utilities.addChangePointer(forgetPasswordButton);
        Utilities.addChangePointer(createAccountButton);
        Utilities.addChangePointer(togglePassVisible);
        
        initComponents();
    }

    private void initComponents() {
        loginPanel.setBackground(new Color(255, 255, 255));
        loginPanel.setPreferredSize(new Dimension(350, 200));
        loginPanel.setLayout(new AbsoluteLayout());
        loginPanel.setFocusable(true);
        
        addLabels();
        addEmailInput();
        addPasswordInput();
        addLoginButton();
        addForgetPassword();
        addCreateAccountButton();
        
        this.add(loginPanel, BorderLayout.CENTER);
    }
    
    private void addLabels(){
        lcupLabel.setFont(new Font("Roboto", 1, 24)); // NOI18N
        lcupLabel.setForeground(PalleteColors.ENTRY_TEXT_COLOR);
        lcupLabel.setText("Hobby Tracker");
        loginPanel.add(lcupLabel, new AbsoluteConstraints(125, 21, -1, -1));
        
        lcupLogoLabel.setIcon(new ImageIcon(logo));
        loginPanel.add(lcupLogoLabel, new AbsoluteConstraints(65, 11, -1, -1));
        
        loginLabel.setFont(new Font("Roboto", 0, 24)); // NOI18N
        loginLabel.setForeground(PalleteColors.ENTRY_TEXT_COLOR);
        loginLabel.setText("Login");
        
        loginLogoLabel.setIcon(new ImageIcon(loginIcon));
        loginPanel.add(loginLogoLabel, new AbsoluteConstraints(140, 85, -1, -1));
        
        loginPanel.add(loginLabel, new AbsoluteConstraints(150, 160, -1, -1));
    }
    
    private void addLoginButton(){
        loginButton.setText("Login");
        loginButton.setFocusPainted(false);
        loginButton.setFocusable(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.setContentAreaFilled(false);
        loginButton.setFont(new Font("Roboto", 0, 12));
        loginButton.setBackground(PalleteColors.TRANSPARENT);
        loginButton.addActionListener(l -> {
            synchronized(HobbyTracker.getLock()){
                while (!Frame.hasPanelsLoaded() || !HobbyTracker.isConnected()) {
                    try {
                        System.out.println("Ayo wait a sec");
                        
                        getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        HobbyTracker.getLock().wait();
                    }
                    catch (InterruptedException e) {
                        Logger.getLogger(HobbyTracker.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                loginButtonAction();
                getParent().setCursor(Cursor.getDefaultCursor());
            }
        });
        loginPanel.add(loginButton, new AbsoluteConstraints(50, 340, 120, 30));
    }
    
    private void addForgetPassword(){
        forgetPasswordButton.setFont(new Font("Roboto", 0, 14)); // NOI18N
        forgetPasswordButton.setForeground(PalleteColors.ENTRY_TEXT_COLOR);
        forgetPasswordButton.setText("Forget Password?");
        forgetPasswordButton.setBackground(new Color(0,0,0,0));
        forgetPasswordButton.setFocusPainted(false);
        forgetPasswordButton.setFocusable(false);
        forgetPasswordButton.setBorder(BorderFactory.createEmptyBorder());
        forgetPasswordButton.setContentAreaFilled(false);
        forgetPasswordButton.addActionListener(l -> {
            synchronized(HobbyTracker.getLock()){
                while (!Frame.hasPanelsLoaded() || !HobbyTracker.isConnected()) {
                    try {
                        System.out.println("Ayo wait a sec");
                        
                        getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        HobbyTracker.getLock().wait();
                    }
                    catch (InterruptedException e) {
                        Logger.getLogger(HobbyTracker.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                Frame.makePopup(Frame.PopupType.FORGETPASSWORD, null);
                getParent().setCursor(Cursor.getDefaultCursor());
            }
        });
        
        loginPanel.add(forgetPasswordButton, new AbsoluteConstraints(180, 340, 150, 30));
    }
    
    private void addCreateAccountButton(){
        createAccountButton.setFont(new Font("Roboto", 0, 14)); // NOI18N
        createAccountButton.setForeground(PalleteColors.ENTRY_TEXT_COLOR);
        createAccountButton.setText("Create Your Account ->");
        //⟶
        createAccountButton.setBackground(new Color(0,0,0,0));
        createAccountButton.setFocusPainted(false);
        createAccountButton.setFocusable(false);
        createAccountButton.setBorder(BorderFactory.createEmptyBorder());
        createAccountButton.setContentAreaFilled(false);
        createAccountButton.addActionListener(l -> {
            Entry.switchPanel(Entry.entryPanels.REGISTER);
        });
        
        loginPanel.add(createAccountButton, new AbsoluteConstraints(110, 460, 150, 30));
    }

    private void addEmailInput(){
        emailPanel.setBackground(new Color(0,0,0,0));
        emailPanel.setLayout(new AbsoluteLayout());
        
        emailIconLabel.setIcon(new ImageIcon(emailIcon));
        emailPanel.add(emailIconLabel, new AbsoluteConstraints(30, 12, -1, -1));
        
        emailTextField.setBackground(PalleteColors.ENTRY_BACKGROUND_COLOR);
        emailTextField.setFont(new Font("Roboto", 0, 13));
        emailTextField.setForeground(PalleteColors.ENTRY_TEXT_LIGHT_COLOR);
        emailTextField.setText("Email");
        emailTextField.setName("emailTextField");
        emailTextField.setBorder(BorderFactory.createEmptyBorder());
        emailTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailTextField.getText().equals("Email")) {
                    emailTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailTextField.getText().isEmpty()) {
                    emailTextField.setText("Email");
                }
            }
        });
        emailPanel.add(emailTextField, new AbsoluteConstraints(60, 11, 170, -1));
        loginPanel.add(emailPanel, new AbsoluteConstraints(50, 220, 260, 40));
    }
    
    private void addPasswordInput(){
        passwordPanel.setBackground(new Color(0,0,0,0));
        passwordPanel.setLayout(new AbsoluteLayout());
        
        passwordIconLabel.setIcon(new ImageIcon(passwordIcon));
        passwordPanel.add(passwordIconLabel, new AbsoluteConstraints(30, 10, -1, -1));
        
        passwordTextField.setEchoChar((char) 0);
        passwordTextField.setBackground(PalleteColors.ENTRY_BACKGROUND_COLOR);
        passwordTextField.setFont(new Font("Roboto", 0, 13));
        passwordTextField.setForeground(PalleteColors.ENTRY_TEXT_LIGHT_COLOR);
        passwordTextField.setText("Password");
        passwordTextField.setName("emailTextField");
        passwordTextField.setBorder(BorderFactory.createEmptyBorder());
        passwordTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordTextField.getPassword()).equals("Password")) {
                    passwordTextField.setText("");
                    passwordTextField.setEchoChar((char) 0);
                }
                passwordTextField.setEchoChar('\u2022');
                
                if (togglePassVisible.isSelected()) passwordTextField.setEchoChar((char) 0);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordTextField.getPassword().length == 0) {
                    passwordTextField.setText("Password");
                    passwordTextField.setEchoChar((char) 0);
                }
                else passwordTextField.setEchoChar('\u2022');
                
                if (togglePassVisible.isSelected() || String.valueOf(passwordTextField.getPassword()).equals("Password")) passwordTextField.setEchoChar((char) 0);
                
            }
        });
        
        togglePassVisible.setBackground(new Color(0,0,0,0));
        togglePassVisible.setFocusPainted(false);
        togglePassVisible.setFocusable(false);
        togglePassVisible.setContentAreaFilled(false);
        togglePassVisible.setBorder(BorderFactory.createEmptyBorder());
        togglePassVisible.addActionListener((ActionEvent e) -> {
            if (!togglePassVisible.isSelected() && !String.valueOf(passwordTextField.getPassword()).equals("Password")) {
                passwordTextField.setEchoChar('\u2022');
            }
            else {
                passwordTextField.setEchoChar((char) 0);
            }
        });
        
        loginPanel.add(togglePassVisible, new AbsoluteConstraints(315, 280, 22, 18));
        
        passwordPanel.add(passwordTextField, new AbsoluteConstraints(60, 10, 170, -1));
        loginPanel.add(passwordPanel, new AbsoluteConstraints(50, 270, 260, 40));
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.WHITE);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paint(g);
    }
    
    private void loginButtonAction(){
        String email = emailTextField.getText();
        if (!UserHandler.isLoginSuccessful(email, passwordTextField.getPassword())) {
            return;
        }
        
        emailTextField.setText("Email");
        passwordTextField.setText("Password");
    }
    
}
