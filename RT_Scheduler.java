import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RT_Scheduler extends JFrame {
    private JLabel J1;
    private JLabel J2;
    private ImageIcon I1;
    private JButton input;
    private int process_num;

    public RT_Scheduler(){


        I1 = new ImageIcon("E:\\Documents\\4th semester\\OS\\untitled\\src\\gui\\os_background.jpeg");

        J1 =new JLabel(I1);
        J1.setSize(1500,800);
        J2 = new JLabel("OS Project");
        J2.setBounds(650,50,500,50);
        J2.setForeground(Color.white);
        J2.setFont(new Font("arial",Font.BOLD,35));

        JLabel J3 = new JLabel("Real Time Process Scheduler");
        J3.setBounds(500,100,500,50);
        J3.setForeground(Color.white);
        J3.setFont(new Font("arial",Font.BOLD,35));

        JLabel J4 = new JLabel("Number of Processes");
        J4.setBounds(200,300,500,50);
        J4.setForeground(Color.white);
        J4.setFont(new Font("arial",Font.PLAIN,20));


        JSlider J6 = new JSlider(0,10);
        J6.setBounds(200,400,500,50);
        J6.setOpaque(false);
        J6.setForeground(Color.white);
        J6.setPaintTicks(true);
        J6.setPaintTrack(true);
        J6.setPaintLabels(true);
        J6.setMinorTickSpacing(1);
        J6.setMajorTickSpacing(5);

        String[] arr= {"RMS","EDF"};
        JComboBox J8 = new JComboBox(arr);
        J8.setBounds(1000,250,100,30);

        input = new JButton("Schedule");
        input.setBounds(1000,400,100,50);
        input.setBackground(Color.gray);


        J1.add(input);

        J1.add(J2);
        J1.add(J3);
        J1.add(J4);
        J1.add(J6);
        J1.add(J8);


        JFrame frame = new JFrame("OS Project");
        frame.add(J1);

        frame.setSize(1500,800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                process_num = (J6.getValue());
                //time_duration =Integer.parseInt (J8.getText());
                new main_interface(process_num,J8.getSelectedItem());
                frame.setVisible(false);
            }
        });

    }


    public static void main(String[] arg){
        new RT_Scheduler();

    }}