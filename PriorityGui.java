import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PriorityGui extends JFrame {
    private JPanel Output;
    private JLabel J1;
    private ImageIcon I1;
    private JButton input;
    private JLabel J2;
    private int temp_num;
    private int process_num;
    private int time_duration;
    private int burst_time;
    private int priority;
    private ArrayList<Integer> time_arr;

    public PriorityGui(int N1,int N2){
        process_num =N1;
        burst_time =N2;

        I1 = new ImageIcon("E:\\Documents\\4th semester\\OS\\untitled\\src\\gui\\os_background.jpeg");
        J1 =new JLabel(I1);
        J1.setSize(1500,800);
        J2 = new JLabel("OS Project");
        J2.setBounds(650,50,500,50);
        J2.setForeground(Color.white);
        J2.setFont(new Font("arial",Font.BOLD,35));

        JLabel J3 = new JLabel("Process Scheduler");
        J3.setBounds(600,100,500,50);
        J3.setForeground(Color.white);
        J3.setFont(new Font("arial",Font.BOLD,35));


        JLabel J5 = new JLabel("Process 1 Time Duration");
        J5.setBounds(200,300,500,50);
        J5.setForeground(Color.white);
        J5.setFont(new Font("arial",Font.PLAIN,20));

        JLabel J6 = new JLabel("Process 1 Priority");
        J6.setBounds(200,400,500,50);
        J6.setForeground(Color.white);
        J6.setFont(new Font("arial",Font.PLAIN,20));

        JTextField J7 = new JTextField();
        J7.setBounds(200,450,500,35);
        J7.setFont(new Font("arial",Font.PLAIN,20));
        //JTextField J8 = new JTextField();
        //J8.setBounds(200,350,500,35);
        //J8.setFont(new Font("arial",Font.PLAIN,20));


        JTextField J9 = new JTextField();
        J9.setBounds(200,350,500,35);
        J9.setFont(new Font("arial",Font.PLAIN,20));

        input = new JButton("Next");
        input.setBounds(1000,400,100,50);
        input.setBackground(Color.white);


        J1.add(input);

        J1.add(J2);
        J1.add(J3);
        J1.add(J5);
        J1.add(J6);
        J1.add(J7);
        J1.add(J9);


        JFrame frame = new JFrame("OS Project");
        frame.add(J1);

        frame.setSize(1500,800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (temp_num!=process_num){
                    String S1=J5.getText();
                    String[] S2=S1.split("\\s+");
                    temp_num=Integer.parseInt(S2[1]);
                    time_duration =Integer.parseInt (J9.getText());

                    String S3=J6.getText();
                    String[] S4=S3.split("\\s+");
                    priority =Integer.parseInt (J7.getText());

                    temp_num++;
                    J5.setText("Process "+temp_num +" Time Duration");
                    J6.setText("Process "+temp_num +" Priority");
                    J7.setText("");
                    J9.setText("");


                    if(temp_num==process_num){
                        input.setText("Schedule");
                        System.out.println(time_arr);
                    }


                }


            }
        });
    }


    public static void main(String[] arg){
        new PriorityGui(7,4);

    }}