package slanitsch.ue08.pferderennen;


import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class pferderennen extends javax.swing.JFrame {
    public static void main(String[] args){
        Konto useraccount = new Konto(5000);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        JFrame frame = new JFrame("Pferderennen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) width, (int) height);
        frame.setLocation(0, 0);
        frame.setVisible(true);

        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Amount (in €)");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        JLabel label2 = new JLabel("Enter Horse(1-3)");
        JTextField tf2 = new JTextField(10); // accepts upto 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        JButton startbutt = new JButton("Start Race");
        panel.add(label2);
        panel.add(tf2);
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);
        panel.add(startbutt);

        // Text Area at the Center
        JTextPane ta = new JTextPane();
        ta.setEditable(false);
        ta.setPreferredSize(new Dimension((int) (width/6), (int) height));
        ta.setText("Event Log:");

        Font font =  new Font("Verdana", Font.BOLD, 16);
        //TODO: JLabel doesn't update
        final JLabel[] balance = {new JLabel("Balance: " + useraccount)};
        balance[0].setFont(font);
        StyledDocument doc = ta.getStyledDocument();

        Style style = ta.addStyle("Test", null);
        Style stylegreen = ta.addStyle("Test", null);
        StyleConstants.setForeground(style, Color.blue);
        StyleConstants.setForeground(stylegreen, Color.green);

        JPanel horses = new JPanel(new GridLayout(10, 0));
        JProgressBar horse1 = new JProgressBar(0, 100);
        JProgressBar horse2 = new JProgressBar(0, 100);
        JProgressBar horse3 = new JProgressBar(0, 100);
        horse1.setSize(new Dimension((int) width, 30));
        horse2.setSize(new Dimension((int) width, 30));
        horse3.setSize(new Dimension((int) width, 30));
        horse1.setValue(0);
        horse2.setValue(0);
        horse3.setValue(0);

        horses.add(horse1);
        horses.add(horse2);
        horses.add(horse3);

        //Adding Components to the frame.
        frame.getContentPane().add(horses);
        frame.getContentPane().add(BorderLayout.NORTH, balance[0]);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.EAST, ta);
        frame.setVisible(true);

        final int[] bets = new int[]{0, 0, 0};

        send.addActionListener(e -> {
            switch (tf2.getText()) {
                case "1":
                    bets[0] = Integer.parseInt(tf.getText());
                    break;
                case "2":
                    bets[1] = Integer.parseInt(tf.getText());
                    break;
                case "3":
                    bets[2] = Integer.parseInt(tf.getText());
                    break;
            }
            int sum = bets[0] + bets[1] + bets[2];
            useraccount.add(-sum);
            balance[0] = new JLabel("Balance: " + useraccount);
            try { doc.insertString(doc.getLength(), "\nPlaced " + tf.getText() + "€ on Horse " + tf2.getText(),style); }
            catch (BadLocationException ignored){}
        });

        reset.addActionListener(e -> {
            tf.setText("");
            tf2.setText("");
        });

        AtomicLong startTime = new AtomicLong();
        AtomicLong timeH1AT = new AtomicLong();
        AtomicLong timeH2AT = new AtomicLong();
        AtomicLong timeH3AT = new AtomicLong();

        AtomicBoolean WinnerDeclared = new AtomicBoolean(false);

        startbutt.addActionListener(ev -> {
            Thread t = new Thread(() -> {
                startTime.set(System.currentTimeMillis());
                for (double i = 0; i <= 100; i++) {
                    horse1.setValue((int) i);
                    try {
                        double rnd = Math.random()*100;
                        Thread.sleep((long) rnd);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timeH1AT.set(System.currentTimeMillis());
                useraccount.add(bets[0]*2);
                balance[0] = new JLabel("Balance: " + useraccount);
                if (!WinnerDeclared.get()){
                    WinnerDeclared.set(true);
                    try {
                        doc.insertString(doc.getLength(), "\nHorse 1 won with a time of " + (timeH1AT.get() - startTime.get())/1000.00 + "seconds", stylegreen);
                    } catch (BadLocationException ignored) {
                    }
                }
            });
            t.start();
        });

        startbutt.addActionListener(ev -> {
            Thread t = new Thread(() -> {
                for (double i = 0; i <= 100; i++) {
                    horse2.setValue((int) i);
                    try {
                        double rnd = Math.random()*100;
                        Thread.sleep((long) rnd);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timeH2AT.set(System.currentTimeMillis());
                useraccount.add(bets[1]*2);
                balance[0] = new JLabel("Balance: " + useraccount);
                if (!WinnerDeclared.get()){
                    WinnerDeclared.set(true);
                    try {
                        doc.insertString(doc.getLength(), "\nHorse 2 won with a time of " + (timeH2AT.get() - startTime.get())/1000.00 + "seconds", stylegreen);
                    } catch (BadLocationException ignored) {
                    }
                }
            });
            t.start();
        });

        startbutt.addActionListener(ev -> {
            Thread t = new Thread(() -> {
                for (double i = 0; i <= 100; i++) {
                    horse3.setValue((int) i);
                    try {
                        double rnd = Math.random()*100;
                        Thread.sleep((long) rnd);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                timeH3AT.set(System.currentTimeMillis());
                useraccount.add(bets[2]*2);
                balance[0] = new JLabel("Balance: " + useraccount);
                if (!WinnerDeclared.get()){
                    WinnerDeclared.set(true);
                    try {
                        doc.insertString(doc.getLength(), "\nHorse 3 won with a time of " + (timeH3AT.get() - startTime.get())/1000.00 + "seconds", stylegreen);
                    } catch (BadLocationException ignored) {
                    }
                }
            });
            t.start();
        });
    }
}
