import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CircleFrame {

    JFrame frame2;
    private boolean stop;
    private static Service service;
    public PrintWriter output = null;
    public InputStreamReader in = null;

    public CircleFrame(){
        service = new Service();
    }

    public void circleFrame() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame2 = new JFrame("Circle");
        frame2.setLayout(null);
        frame2.setResizable(false);
        frame2.setBounds(new Rectangle(300, 200));
        frame2.setLocation(d.width / 2 - frame2.getSize().width / 2,
                d.height / 2 - frame2.getSize().height / 2);


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
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("RUN|CIR");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Thread readMessage = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            in = new InputStreamReader(service.client.getInputStream());
                            int count = 0;
                            String contents = "";
                            do {
                                if (stop == true) {
                                    break;
                                }
                                int ch = 0;
                                ch = in.read();

                                if (ch == -1) {
                                    System.out.print("return");
                                    return;
                                }
                                if (ch == '|') {
                                    System.out.println("Location: " + contents);//displayImage()
                                    textField.setText(contents);
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

        JButton stop = new JButton("Stop");
        stop.setBounds(110, 130, 70, 30);
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
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
                frame2.dispose();
            }
        });

        frame2.add(panel);
        panel.add(stop);
        panel.add(location);
        panel.add(textField);
        panel.add(run);
        panel.add(exit);
        frame2.setEnabled(true);
        frame2.setVisible(true);
    }
}
