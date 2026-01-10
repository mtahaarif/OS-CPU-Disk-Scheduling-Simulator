import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Console extends JFrame {
    private JLabel J1;
    private ImageIcon I1;
    private JButton input;
    private JLabel J2;

    public Console(int processNum, List<Process> processList, Object selectedItem,int i) {

        I1 = new ImageIcon("E:\\Documents\\4th semester\\OS\\untitled\\src\\gui\\os_background.jpeg");
        J1 = new JLabel(I1);
        J1.setSize(1500, 800);
        J2 = new JLabel("OS Project");
        J2.setBounds(650, 50, 500, 50);
        J2.setForeground(Color.white);
        J2.setFont(new Font("arial", Font.BOLD, 35));

        JLabel J3 = new JLabel("Process Scheduler");
        J3.setBounds(600, 100, 500, 50);
        J3.setForeground(Color.white);
        J3.setFont(new Font("arial", Font.BOLD, 35));


        input = new JButton("Table");
        input.setBounds(900, 400, 100, 50);
        input.setBackground(Color.gray);
        input.setFocusable(false);
        JButton input1 = new JButton("Progress Bar");
        input1.setBounds(500, 400, 100, 50);
        input1.setBackground(Color.gray);
        input1.setFocusable(false);

        J1.add(input);
        J1.add(input1);

        J1.add(J2);
        J1.add(J3);


        JFrame frame = new JFrame("OS Project");
        frame.add(J1);

        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Table(processNum, processList, selectedItem);

            }
        });

        input1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new ProgressBar(processNum, processList, (String) selectedItem));

            }
        });


    }
    public Console(int processNum, List<RealTimeProcess> processList1, Object selectedItem) {


        I1 = new ImageIcon("E:\\Documents\\4th semester\\OS\\untitled\\src\\gui\\os_background.jpeg");
        J1 = new JLabel(I1);
        J1.setSize(1500, 800);
        J2 = new JLabel("OS Project");
        J2.setBounds(650, 50, 500, 50);
        J2.setForeground(Color.white);
        J2.setFont(new Font("arial", Font.BOLD, 35));

        JLabel J3 = new JLabel("Real Time Process Scheduler");
        J3.setBounds(500, 100, 500, 50);
        J3.setForeground(Color.white);
        J3.setFont(new Font("arial", Font.BOLD, 35));


        input = new JButton("Table");
        input.setBounds(900, 400, 100, 50);
        input.setBackground(Color.gray);
        input.setFocusable(false);
        JButton input1 = new JButton("Progress Bar");
        input1.setBounds(500, 400, 100, 50);
        input1.setBackground(Color.gray);
        input1.setFocusable(false);

        J1.add(input);
        J1.add(input1);

        J1.add(J2);
        J1.add(J3);


        JFrame frame = new JFrame("OS Project");
        frame.add(J1);

        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new Execution(processNum, processList1, (String) selectedItem));
            }
        });

        input1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //SwingUtilities.invokeLater(() -> new Task(processNum, processList1, (String) selectedItem));

                //SwingUtilities.invokeLater(() -> new ProgressBar(processNum, processList1, (String) selectedItem));

            }
        });


    }


}