import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JLabel J1;
    private JLabel J2;
    private ImageIcon I1;
    private JButton input;
    public Main(){

        I1 = new ImageIcon("E:\\Documents\\4th semester\\OS\\untitled\\src\\gui\\os_background.jpeg");
        J1 =new JLabel(I1);
        J1.setSize(1500,800);
        J2 = new JLabel("OS Project");
        J2.setBounds(650,50,500,50);
        J2.setForeground(Color.white);
        J2.setFont(new Font("arial",Font.BOLD,35));

        JLabel J4 = new JLabel("Select the Schedule type");
        J4.setBounds(200,300,500,100);
        J4.setForeground(Color.white);
        J4.setFont(new Font("arial",Font.PLAIN,20));

        String[] arr= {"Normal Schedule","Real_Time Schedule","Disk Schedule"};
        JComboBox J8 = new JComboBox(arr);
        J8.setBounds(200,400,500,50);
        J8.setBackground(Color.gray);
        input = new JButton("Schedule");
        input.setBounds(1000,400,100,50);
        input.setBackground(Color.gray);

        J1.add(input);
        J1.add(J2);
        J1.add(J4);
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
                if (J8.getSelectedItem()=="Normal Schedule")
                    new Scheduler();
                else if (J8.getSelectedItem()=="Real_Time Schedule")
                    new RT_Scheduler();
                else if (J8.getSelectedItem()=="Disk Schedule")
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new DiskSchedulingSimulation();
                        }});
            }
        });

    }

    public static void main(String[] arg){
        new Main();
    }}