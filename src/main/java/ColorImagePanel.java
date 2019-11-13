import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ColorImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public BufferedImage image;

    public ColorImagePanel(final BufferedImage img) throws IOException {
        image = img;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, null);
    }
}
