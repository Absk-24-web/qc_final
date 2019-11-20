import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ColorImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public BufferedImage image;

    public ColorImagePanel(final BufferedImage img) throws IOException {
        image = img;
        //ImageIO.write(img, "jpg", new File("/home/ur/Desktop/NEW ur cap/check/src/main/resources/out.jpg"));

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, null);
    }
}
