import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.UnknownHostException;


public class QualityCheckUserInterface1 {

    // variable  for color
    private Color color;
    int Hu, Su, Lu, Hl, Sl, Ll;
    private ColorImagePanel colorImagePanel;
    private BufferedImage img2;
    private static float H, S, L;
    JSlider sliderHl, sliderSl, sliderLl, sliderHu, sliderSu, sliderLu;

    private static BufferedImage img;
    private static final int MAX_SIZE = 10 * 1024 * 1024;
    private JTextField confidencetxt;
    private JTextField txt;
    private boolean stop;
    private static Service service;
    public PrintWriter output = null;
    public InputStreamReader in = null;
    private String x, s;
    public JPanel panel;

    //Frame
    JFrame frame;
    JFrame frame1;
    JFrame frame2;
    JFrame frame3;
    JFrame frame4;
    private ImagePanel imagePanel;


    public QualityCheckUserInterface1() {
        // TODO Auto-generated constructor stub
        service = new Service();

    }


    public static void main(String args[]) throws IOException {
        QualityCheckUserInterface1 quality = new QualityCheckUserInterface1();
        quality.mainFrame();

    }


    //Main frame

    public void mainFrame() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Main");
        frame.setSize(new Dimension(500, 500));
        frame.setLocation(d.width / 2 - frame.getSize().width / 2,
                d.height / 2 - frame.getSize().height / 2);
        frame.setLayout(null);

        JPanel panel = new JPanel();
        panel.setSize(new Dimension(500, 500));
        panel.setLayout(new FlowLayout(10, 40, 50));


        JButton button = new JButton("Color");
        button.setPreferredSize(new Dimension(150, 50));
        button.setMaximumSize(button.getPreferredSize());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    colorFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton button1 = new JButton("Circle");
        button1.setPreferredSize(new Dimension(150, 50));
        button1.setMaximumSize(button.getPreferredSize());
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                circleFrame();
            }
        });

        JButton button2 = new JButton("Bar");
        button2.setPreferredSize(new Dimension(150, 50));
        button2.setMaximumSize(button.getPreferredSize());
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                BarFrame();
            }
        });

        JButton button3 = new JButton("Pattern");
        button3.setPreferredSize(new Dimension(150, 50));
        button3.setMaximumSize(button.getPreferredSize());
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    patternFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton button4 = new JButton("OCR");
        button4.setPreferredSize(new Dimension(150, 50));
        button4.setMaximumSize(button.getPreferredSize());
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ocrFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton connect = new JButton("Connect");
        connect.setPreferredSize(new Dimension(150, 50));
        connect.setMaximumSize(button.getPreferredSize());
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    service.socket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        panel.add(button);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(connect);
        frame.add(panel);
        frame.setVisible(true);
        frame.setEnabled(true);

    }

    //color Frame
    public void colorFrame() throws IOException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("Color");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBounds(new Rectangle(770, 575));
        frame.setLocation(d.width / 2 - frame.getSize().width / 2,
                d.height / 2 - frame.getSize().height / 2);
        frame.getDefaultCloseOperation();

        final JPanel panel = new JPanel();
        panel.setBounds(0, 0, 770, 575);
        panel.setLayout(null);

        final Border border = BorderFactory.createLineBorder(Color.black, 3);


        ImageIcon icon = new ImageIcon("/home/ur/Desktop/NEW ur cap/qc_urcap/src/main/resources/com/jazari/QC/impl/main.png");

        final JLabel label1 = new JLabel();
        label1.setBounds(5, 25, 640, 480);
        label1.setBorder(border);
        label1.setForeground(Color.black);
        label1.setIcon(icon);

        final JLabel label2 = new JLabel("Hl:");
        label2.setBounds(10, 510, 20, 10);
        final JLabel label3 = new JLabel("Hu:");
        label3.setBounds(330, 510, 30, 10);
        final JLabel label4 = new JLabel("Sl:");
        label4.setBounds(10, 530, 30, 10);
        final JLabel label5 = new JLabel("Su:");
        label5.setBounds(330, 530, 30, 10);
        final JLabel label6 = new JLabel("Ll:");
        label6.setBounds(10, 550, 30, 10);
        final JLabel label7 = new JLabel("Lu:");
        label7.setBounds(330, 550, 30, 10);

        sliderHl = new JSlider(JSlider.HORIZONTAL);
        sliderHl.setBounds(35, 510, 285, 20);
        sliderHl.setValue(0);
        sliderHl.setMaximum(360);
        sliderHl.setMinimum(0);
        sliderHl.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                changeEvent();
            }
        });
        sliderHu = new JSlider(JSlider.HORIZONTAL);
        sliderHu.setBounds(365, 510, 285, 20);
        sliderHu.setMinimum(0);
        sliderHu.setMaximum(360);
        sliderHu.setValue(360);
        sliderHu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                changeEvent();
            }
        });

        sliderSl = new JSlider(JSlider.HORIZONTAL);
        sliderSl.setBounds(35, 530, 285, 20);
        sliderSl.setValue(0);
        sliderSl.setMinimum(0);
        sliderSl.setMaximum(255);
        sliderSl.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                changeEvent();
            }
        });

        sliderSu = new JSlider(JSlider.HORIZONTAL);
        sliderSu.setBounds(365, 530, 285, 20);
        sliderSu.setMinimum(0);
        sliderSu.setMaximum(255);
        sliderSu.setValue(255);
        sliderSu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                changeEvent();
            }
        });

        sliderLl = new JSlider(JSlider.HORIZONTAL);
        sliderLl.setBounds(35, 550, 285, 20);
        sliderLl.setValue(0);
        sliderLl.setMinimum(0);
        sliderLl.setMaximum(255);
        sliderLl.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                changeEvent();
            }
        });


        sliderLu = new JSlider(JSlider.HORIZONTAL, 255);
        sliderLu.setBounds(365, 550, 285, 20);
        sliderLu.setMinimum(0);
        sliderLu.setMaximum(255);
        sliderLu.setValue(255);
        sliderLu.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                changeEvent();
            }
        });

        final JButton getImage = new JButton("Get Image");
        getImage.setBounds(650, 25, 110, 30);
        getImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //send
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("SEND");
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //receive in new thread
                Thread readImage3 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            in = new InputStreamReader(service.client.getInputStream());
                            int count = 0;
                            byte[] contents = new byte[MAX_SIZE];
                            int idx = 0;

                            do {
                                img = null;
                                int ch = 0;
                                ch = in.read();

                                if (ch == -1) {
//                    System.out.println("\nEnd"+contents.length);
                                    //System.out.print("return");
                                    return;
                                }

                                if (ch == ',') {
                                    //System.out.println("EOI");
                                    contents[idx] = '\0';
                                    byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(new String(contents));
                                    img = ImageIO.read(new ByteArrayInputStream(imageBytes));
                                    img2 = deepCopy(img);
                                    colorImagePanel = new ColorImagePanel(img);
                                    colorImagePanel.setBounds(5, 5, 640, 480);
                                    colorImagePanel.setBorder(border);
                                    colorImagePanel.setForeground(Color.black);
                                    panel.add(colorImagePanel);
                                    colorImagePanel.revalidate();
                                    colorImagePanel.repaint();
                                    //label.getGraphics().drawImage(img, 0, 0, null);
                                    idx = 0;
                                    //System.out.println("saved " + count);//displayImage()
                                } else {
                                    contents[idx] = (byte) ch;
                                    idx++;
                                }
                                if (img != null) {
                                    break;
                                }
                            } while (true);
                        } catch (UnknownHostException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }

                });
                readImage3.start();
            }

        });

        JButton send = new JButton("Send ");
        send.setBounds(660, 70, 80, 30);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (s != null) {
                    try {
                        output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                        output.println("TAKE|CLR|" + "{" + "\"LB\":" + s + "}");
                        JOptionPane.showMessageDialog(frame, "Successfully Send");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (service.client == null) {
                    JOptionPane.showMessageDialog(frame, "Connection Error");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please make sure set the bound");
                }


            }
        });

        JButton run = new JButton("Run");
        run.setBounds(665, 120, 70, 30);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop = false;
                //send
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("RUN|CLR");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //receive in new thread
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
                                    System.out.println("Location: " + contents);//displayLocation()
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
        stopb.setBounds(665, 170, 70, 30);
        stopb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop = true;
                //send
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("STOP");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton exit = new JButton("Exit");
        exit.setBounds(680, 530, 70, 30);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });


        panel.add(sliderHu);
        panel.add(sliderHl);
        panel.add(sliderSu);
        panel.add(sliderSl);
        panel.add(sliderLu);
        panel.add(sliderLl);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);
        panel.add(label6);
        panel.add(label7);
        panel.add(send);
        panel.add(label1);
        panel.add(getImage);
        panel.add(stopb);
        panel.add(run);
        panel.add(exit);
        frame.add(panel);
        frame.setVisible(true);
        frame.setEnabled(true);

    }


    //pattern frame
    public void patternFrame() throws IOException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame4 = new JFrame("Pattern Matching");
        frame4.setLayout(null);
        frame4.setResizable(false);
        frame4.setBounds(new Rectangle(745, 530));
        frame4.setLocation(d.width / 2 - frame4.getSize().width / 2,
                d.height / 2 - frame4.getSize().height / 2);

        panel = new JPanel();
        panel.setBounds(0, 0, 745, 530);
        panel.setLayout(null);


        final Border border = BorderFactory.createLineBorder(Color.black, 3);

        ImageIcon icon = new ImageIcon("/home/ur/Desktop/NEW ur cap/qc_urcap/src/main/resources/com/jazari/QC/impl/main.png");

        final JLabel label1 = new JLabel();
        label1.setBounds(5, 5, 640, 480);
        label1.setBorder(border);
        label1.setForeground(Color.black);
        label1.setIcon(icon);

        final JButton getImage = new JButton("Get Image");
        getImage.setBounds(20, 490, 110, 30);
        getImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                label1.disable();
                //send
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("SEND");

                } catch (
                        UnknownHostException ex) {
                    ex.printStackTrace();
                } catch (
                        IOException ex) {
                    ex.printStackTrace();
                }
                //receive image in new thread
                Thread readImage2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            in = new InputStreamReader(service.client.getInputStream());
                            int count = 0;

                            byte[] contents = new byte[MAX_SIZE];
                            int idx = 0;

                            do {
                                img = null;
                                int ch = 0;
                                ch = in.read();

                                if (ch == -1) {
//                    System.out.println("\nEnd"+contents.length);
                                    // System.out.print("return");
                                    return;
                                }

                                if (ch == ',') {
                                    //System.out.println("EOI");
                                    contents[idx] = '\0';
                                    byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(new String(contents));
                                    img = ImageIO.read(new ByteArrayInputStream(imageBytes));
                                    ImageIO.write(img, "jpg", new File("/home/ur/Desktop/NEW ur cap/check/src/main/resources/out.jpg"));
                                    if (imagePanel == null) {
                                        imagePanel = new ImagePanel(img);
                                    } else {
                                        imagePanel.updateImage(img);
                                    }
                                    imagePanel.setBounds(5, 5, 640, 480);
                                    imagePanel.setBorder(border);
                                    imagePanel.setForeground(Color.black);
                                    panel.add(imagePanel);
                                    imagePanel.repaint();
                                    //label.getGraphics().drawImage(img, 0, 0, null);
                                    idx = 0;

                                    System.out.println("saved " + count);//displayImage()
                                } else {
                                    contents[idx] = (byte) ch;
                                    idx++;
                                }
                                if (img != null) {
                                    break;
                                }
                            } while (true);

                        } catch (
                                UnknownHostException ex) {
                            ex.printStackTrace();
                        } catch (
                                IOException ex) {
                            ex.printStackTrace();
                        }

                    }

                });
                readImage2.start();


            }
        });

        final JButton send = new JButton("Send Parameter");
        send.setBounds(140, 490, 150, 30);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                x = confidencetxt.getText();
                if (imagePanel.s != null && x != null && img != null) {
                    try {
                        output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                        output.println("TAKE|PAT|" + "{" + "\"bBox\":" + imagePanel.s + "," + "\"confi\":" + x + "}");
                        JOptionPane.showMessageDialog(frame4, "Send Successfully");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame4, "Please make sure the parameter or confidence is set");
                }
            }
        });


        JLabel confidence = new JLabel("Confidence:");
        confidence.setBounds(650, 190, 90, 30);

        confidencetxt = new JTextField();
        confidencetxt.setBounds(650, 230, 90, 30);

        JButton run = new JButton("Run");
        run.setBounds(300, 490, 70, 30);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop = false;
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("RUN|PAT");
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
        stopb.setBounds(380, 490, 70, 30);
        stopb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop = true;
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("STOP");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton exit = new JButton("Exit");
        exit.setBounds(460, 490, 70, 30);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame4.dispose();
            }
        });

        frame4.add(panel);
        panel.add(run);
        panel.add(exit);
        panel.add(label1);
        panel.add(getImage);
        panel.add(stopb);
        panel.add(send);
        panel.add(confidence);
        panel.add(confidencetxt);
        frame4.setVisible(true);
        frame4.setEnabled(true);

    }


//barframe

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
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("RUN|BCR");
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

        final JButton stop = new JButton("Stop");
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
                frame1.dispose();
            }
        });

        frame1.add(panel);
        panel.add(stop);
        panel.add(location);
        panel.add(textField);
        panel.add(run);
        panel.add(exit);
        frame1.setEnabled(true);
        frame1.setVisible(true);
    }

    //circle Frame

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

    //OCR
    public void ocrFrame() throws IOException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame3 = new JFrame("OCR");
        frame3.setLayout(null);
        frame3.setResizable(false);
        frame3.setBounds(new Rectangle(755, 530));
        frame3.setLocation(d.width / 2 - frame3.getSize().width / 2,
                d.height / 2 - frame3.getSize().height / 2);

        final JPanel panel = new JPanel();
        panel.setBounds(0, 0, 755, 530);
        panel.setLayout(null);


        final Border border = BorderFactory.createLineBorder(Color.black, 3);
//        //label = new ImagePanel("/home/ur/sdk/sdk-1.6.1/socketclient/src/main/resources/impl/pic.jpg");


        ImageIcon icon = new ImageIcon("/home/ur/Desktop/NEW ur cap/qc_urcap/target/QC-1.0-SNAPSHOT.urcap");

        final JLabel label1 = new JLabel();
        label1.setBounds(5, 5, 640, 480);
        label1.setBorder(border);
        label1.setForeground(Color.black);
        label1.setIcon(icon);
        //label.setFont(label.getFont().deriveFont(Font.BOLD, 20));

        JButton getImage = new JButton("Get Image");
        getImage.setBounds(20, 490, 110, 30);
        getImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                label1.disable();
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("SEND");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Thread readImage = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            in = new InputStreamReader(service.client.getInputStream());
                            int count = 0;

                            byte[] contents = new byte[MAX_SIZE];
                            int idx = 0;

                            do {
                                img = null;
                                int ch = 0;
                                ch = in.read();

                                if (ch == -1) {
//                    System.out.println("\nEnd"+contents.length);
                                    // System.out.print("return");
                                    return;
                                }

                                if (ch == ',') {
                                    //System.out.println("EOI");
                                    contents[idx] = '\0';
                                    byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(new String(contents));
                                    img = ImageIO.read(new ByteArrayInputStream(imageBytes));
                                    if (imagePanel == null) {
                                        imagePanel = new ImagePanel(img);
                                    } else {
                                        imagePanel.updateImage(img);
                                    }
                                    imagePanel.setBounds(5, 5, 640, 480);
                                    imagePanel.setBorder(border);
                                    imagePanel.setForeground(Color.black);
                                    panel.add(imagePanel);
                                    imagePanel.revalidate();
                                    imagePanel.repaint();
                                    //label.getGraphics().drawImage(img, 0, 0, null);
                                    idx = 0;
                                    System.out.println("saved " + count);//displayImage()
                                } else {
                                    contents[idx] = (byte) ch;
                                    idx++;
                                }
                                if (img != null) {
                                    break;
                                }
                            } while (true);

                        } catch (UnknownHostException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                });
                readImage.start();

            }
        });


        JButton send = new JButton("Send Parameter");
        send.setBounds(140, 490, 150, 30);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                x = txt.getText();
                try {
                    if (imagePanel.s1 != null && x != null) {
                        output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                        output.println("TAKE|OCR|" + "{" + "\"region\":" + "[" + imagePanel.s1 + "]" + "," + "\"text\":" + "\"" + x + "\"" + "}");
                        JOptionPane.showMessageDialog(frame3, "Send Successfully");
                    } else {
                        JOptionPane.showMessageDialog(frame3, "Please make sure the parameter or confidence is set");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        JLabel text = new JLabel("Text:");
        text.setBounds(650, 190, 90, 30);

        txt = new JTextField();
        txt.setBounds(650, 230, 100, 30);

        JButton run = new JButton("Run");
        run.setBounds(300, 490, 70, 30);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop = false;
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("RUN|OCR");
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
        stopb.setBounds(380, 490, 70, 30);
        stopb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stop = true;
                try {
                    output = new PrintWriter(new OutputStreamWriter(service.client.getOutputStream()), true);
                    output.println("STOP");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JButton exit = new JButton("Exit");
        exit.setBounds(460, 490, 70, 30);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame3.dispose();
            }
        });


        frame3.add(panel);
        panel.add(run);
        panel.add(exit);
        panel.add(label1);
        panel.add(getImage);
        panel.add(stopb);
        panel.add(send);
        panel.add(text);
        panel.add(txt);
        frame3.setVisible(true);
        frame3.setEnabled(true);

    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void changeEvent() {
        img = deepCopy(img2);
        colorImagePanel.image = deepCopy(img2);
        for (int x = 0; x < colorImagePanel.image.getWidth(); x++) {
            for (int y = 0; y < colorImagePanel.image.getHeight(); y++) {
                color = new Color(colorImagePanel.image.getRGB(x, y));
                RgbToHsl();
                Hu = sliderHu.getValue();
                Su = sliderSu.getValue();
                Lu = sliderLu.getValue();
                Hl = sliderHl.getValue();
                Sl = sliderSl.getValue();
                Ll = sliderLl.getValue();
                if ((H >= Hl && H <= Hu) && (S >= Sl && S <= Su) && (L >= Ll && L <= Lu)) {
                    colorImagePanel.image.setRGB(x, y, color.getRGB());
                    colorImagePanel.repaint();
                    colorImagePanel.revalidate();
                } else {
                    int rgb = new Color(0, 0, 0).getRGB();
                    colorImagePanel.image.setRGB(x, y, rgb);
                    colorImagePanel.repaint();
                    colorImagePanel.revalidate();
                }
            }
        }

        Hl = Hl / 2;
        Hu = Hu / 2;

        s = ("[" + Hl + "," + Ll + "," + Sl + "]" + "," + "\"UB\":" + "[" + Hu + "," + Lu + "," + Su + "]");

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
