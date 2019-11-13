import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Service {

    //for color
    private Color color;
    int Hu, Su, Lu, Hl, Sl, Ll;
    private ImagePanel label;


    public Socket client;
    private static float H, S, L;
    private QualityCheckUserInterface1 quality;
    public PrintWriter output = null;
    public InputStreamReader in = null;
    private static final int MAX_SIZE = 10 * 1024 * 1024;
    private static BufferedImage img;

    public String s;
    BufferedImage img2;


    public Service() {
    }

    public void socket() throws IOException {

        client = new Socket("100.100.100.1", 3001);
        System.out.println("Just connected to " + client.getRemoteSocketAddress());


    }


    //public  void getImage(){
//    final Border border = BorderFactory.createLineBorder(Color.black, 3);
//    try {
//        output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
//        output.println("SEND");
//        in = new InputStreamReader(client.getInputStream());
//        int count = 0;
//
//        byte[] contents = new byte[MAX_SIZE];
//        int idx = 0;
//
//        do {
//            img =null;
//            int ch = 0;
//            ch = in.read();
//
//            if (ch == -1) {
////                    System.out.println("\nEnd"+contents.length);
//                //System.out.print("return");
//                return;
//            }
//
//            if (ch == ',') {
//                //System.out.println("EOI");
//                contents[idx] = '\0';
//                byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(new String(contents));
//                img = ImageIO.read(new ByteArrayInputStream(imageBytes));
//                label = new ImagePanel(img);
//                label.setBounds(5, 5, 640, 480);
//                label.setBorder(border);
//                label.setForeground(Color.black);
//                QualityCheckUserInterface1 quality =  new QualityCheckUserInterface1();
//                quality.panel.add(label);
//                label.revalidate();
//                label.repaint();
//                //label.getGraphics().drawImage(img, 0, 0, null);
//                idx = 0;
//                //System.out.println("saved " + count);//displayImage()
//            } else {
//                contents[idx] = (byte) ch;
//                idx++;
//            }
//            if(img != null){
//                break;
//            }
//        } while (true);
//
//
//    } catch (UnknownHostException ex) {
//        ex.printStackTrace();
//    } catch (IOException ex) {
//        ex.printStackTrace();
//    }
//}
    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void changeEvent() {
        img = deepCopy(img2);
        label.image = deepCopy(img2);
        for (int x = 0; x < label.image.getWidth(); x++) {
            for (int y = 0; y < label.image.getHeight(); y++) {
                color = new Color(label.image.getRGB(x, y));
               // RgbToHsl();
                //Hu = sliderHu.getValue();Su = sliderSu.getValue(); Lu = sliderLu.getValue();
                // Hl = sliderHl.getValue();Sl = sliderSl.getValue(); Ll = sliderLl.getValue();
                if ((H >= Hl && H <= Hu) && (S >= Sl && S <= Su) && (L >= Ll && L <= Lu)) {
                    label.image.setRGB(x, y, color.getRGB());
                    label.repaint();
                    label.revalidate();
                } else {
                    int rgb = new Color(0, 0, 0).getRGB();
                    label.image.setRGB(x, y, rgb);
                    label.repaint();
                    label.revalidate();
                }
            }
        }
        s = ("[" + Hl + "," + Ll + "," + Sl + "]" + "," + "\"ub\":" + "[" + Hu + "," + Lu + "," + Su + "]");

    }


    public void RgbToHsl() {
        String s1, s2, s3;
        s1 = Integer.toString(color.getRed());
        s2 = Integer.toString(color.getBlue());
        s3 = Integer.toString(color.getGreen());
        float r, g, b, R, G, B;
        r = Integer.parseInt(s1);
        b = Integer.parseInt(s2);
        g = Integer.parseInt(s3);
        R = r / 255;
        G = g / 255;
        B = b / 255;

        float Cmax = Math.max(R, Math.max(G, B));
        float Cmin = Math.min(R, Math.min(G, B));

        float diff = Cmax - Cmin;

        //calculate H
        if (diff == 0) {
            H = 0;
        } else if (Cmax == R) {
            float diff1 = G - B;
            float div1 = diff1 / diff;
            float m = (div1 % 6);
            if (m < 0) {
                m = 6 + m;
            }
            H = 60 * m;

        } else if (Cmax == G) {
            float diff1 = B - R;
            float div1 = diff1 / diff;
            H = 60 * (div1 + 2);


        } else if (Cmax == B) {
            float diff1 = R - G;
            float div1 = diff1 / diff;
            H = (60 * (div1 + 4));
        }

        // Calculate L
        L = ((Cmax + Cmin) / 2);

        //Calculate S
        if (diff == 0) {
            S = 0;
        } else {
            float diff1 = (1 - Math.abs(2 * L - 1));
            S = diff / diff1;
        }
        S = S * 255;
        L = L * 255;
        if (H > 360 || S > 255 || L > 255) {
            H = 360;
            S = 255;
            L = 255;
        }

    }


}
