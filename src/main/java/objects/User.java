
package objects;

import com.hobbytracker.components.Icons;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class User implements Serializable{

    private transient Image icon;
    private byte[] imageData;
    private String email;
    private String password;
    private String fullName;
    private final LocalDateTime dateJoined;
    private final LocalDateTime lastUpdated;
    private boolean isImageDefault;
    private boolean isOnline = false;
    
    public User(String email, String password,String fullName, Image icon, LocalDateTime dateJoined, LocalDateTime lastUpdated, boolean isImageDefault){
        this.dateJoined = dateJoined;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.lastUpdated = lastUpdated;
        this.isImageDefault = isImageDefault;
        
        try{
            if (icon == null) imageData = serializeImage(Icons.noImageIcon.getImage());
            else imageData = serializeImage(icon);
        }
        catch(IOException e){Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);}
    }
    
    private byte[] serializeImage(Image image) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }
    
    private Image deserializeImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        return ImageIO.read(bais);
    }

    public String getUserName() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Image getIcon() {
        if (icon == null) {
            try {this.icon = deserializeImage(imageData);}
            catch (IOException ex) {Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);}
        }
        
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
    
    public byte[] getImageData() {
        return imageData;
    }
    
    public LocalDateTime getDateJoined() {
        return dateJoined;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public boolean isImageDefault() {
        return isImageDefault;
    }

    public void setIsImageDefault(boolean isImageDefault) {
        this.isImageDefault = isImageDefault;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
    
}
