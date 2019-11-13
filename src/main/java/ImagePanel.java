import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public BufferedImage image;
    private Shape shape = null;
    public StringBuilder str = new StringBuilder();
    public String s, s1;
    Point startDrag, endDrag;


    public void updateImage(BufferedImage img) {
        this.image = img;
    }

    //public ImagePanel(String inputImage) throws IOException {
    public ImagePanel(final BufferedImage img) throws IOException {
        image = img;
        //image = ImageIO.read(new File("/home/ur/sdk/sdk-1.6.1/socketclient/src/main/resources/impl/pic.jpg"));
        //image = ImageIO.read(new File(inputImage));
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startDrag = new Point(e.getX(), e.getY());
                endDrag = startDrag;
                revalidate();
                repaint();
            }

            int count = 0;

            public void mouseReleased(MouseEvent e) {
                if (endDrag != null && startDrag != null) {
                    try {
                        shape = makeRectangle(startDrag.x, startDrag.y, e.getX(),
                                e.getY());
                        int x, y, w, h;
                        x = startDrag.x;
                        w = e.getX();
                        y = startDrag.y;
                        h = e.getY();
                        s = ("[" + x + "," + y + "," + w + "," + h + "]");
                        startDrag = null;
                        endDrag = null;
                        revalidate();
                        repaint();

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
                count++;
                System.out.println(count);
                str.append(s);
                if (count == 1) {
                    str.append(",");
                }
                s1 = str.toString();
                if (count == 2) {
                    str.delete(0, str.length());
                    count = 0;
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                endDrag = new Point(e.getX(), e.getY());
                revalidate();
                repaint();
            }
        });

    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                0.50f));

        if (shape != null) {
            g2.setPaint(Color.BLACK);
            g2.draw(shape);
            g2.setPaint(Color.LIGHT_GRAY);
            g2.fill(shape);
        }

        if (startDrag != null && endDrag != null) {
            g2.setPaint(Color.LIGHT_GRAY);
            Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x,
                    endDrag.y);
            g2.draw(r);
        }

    }

    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2),
                Math.abs(x1 - x2), Math.abs(y1 - y2));
    }
}
