import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class BarFrame {

    private boolean stop;
    private static Service service1;
    public PrintWriter output = null;
    public InputStreamReader in = null;

    JFrame frame1;


    public BarFrame() {
        service1 = new Service();
    }


    public void BarFrame() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame1 = new JFrame("Bar Code");
        frame1.setLayout(null);
        frame1.setResizable(false);
        frame1.setBounds(new Rectangle(300, 200));
        frame1.setLocation(d.width / 2 - frame1.getSize().width / 2,
                d.height / 2 - frame1.getSize().height / 2);


        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 300, 200);
        panel.setLayout(null);

        JLabel location = new JLabel("Location:");
        location.setBounds(50, 60, 80, 30);

        final JTextField textField = new JTextField();
        textField.setBounds(140, 60, 150, 30);
        textField.setEditable(false);


        JButton run = new JButton("Run");
        run.setBounds(20, 130, 70, 30);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop = false;
                try {
                    output = new PrintWriter(new OutputStreamWriter(service1.client.getOutputStream()), true);
                    output.println("RUN|BCR");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Thread readMessage = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            in = new InputStreamReader(service1.client.getInputStream());
                            int count = 0;
                            String contents = "";
                            do {
                                if (stop == true) {
                                    break;
                                }
                                int ch = 0;
                                ch = in.read();

                                if (ch == -1) {
                                    System.out.print("return\n");
                                    return;
                                }
                                if (ch == '|') {
                                    System.out.println("Location: " + contents);//displayImage()
                                    // textField.setText(contents);
                                    contents = "";
                                } else {
                                    char c = (char) ch;
                                    contents = contents + c;
                                }
                            } while (true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                readMessage.start();
            }
        });

        final JButton stopb = new JButton("Stop");
        stopb.setBounds(110, 130, 70, 30);
        stopb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop = true;
                try {
                    output = new PrintWriter(new OutputStreamWriter(service1.client.getOutputStream()), true);
                    output.println("STOP");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton exit = new JButton("Exit");
        exit.setBounds(210, 130, 70, 30);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame1.dispose();
            }
        });

        frame1.add(panel);
        panel.add(stopb);
        panel.add(location);
        panel.add(textField);
        panel.add(run);
        panel.add(exit);
        frame1.setEnabled(true);
        frame1.setVisible(true);
    }
}
