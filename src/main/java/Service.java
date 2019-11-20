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


    public Socket client;

    public Service() {

    }

    public void socket() throws IOException {

        client = new Socket("localhost", 3001);
        System.out.println("Just connected to " + client.getRemoteSocketAddress());

    }


}
