import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Process {
    int pid; // Process ID
    int priority; // Priority of the process
    int arrivalTime; // Arrival time of the process
    int burstTime; // Burst time of the process
    int waitingTime; // Waiting time of the process
    int turnaroundTime; // Turnaround time of the process
    int remainingTime;
    int startTime; // Start time of the process
    int completionTime; // Completion time of the process
    JProgressBar progressBar;
    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.remainingTime = burstTime;
        this.startTime = 0;
        this.completionTime = 0;
        this.progressBar = new JProgressBar(0, burstTime);
        this.progressBar.setStringPainted(true);
        this.progressBar.setString("P" + pid);
    }
    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.remainingTime = burstTime;
        this.startTime = 0;
        this.completionTime = 0;
        this.progressBar = new JProgressBar(0, burstTime);
        this.progressBar.setStringPainted(true);
        this.progressBar.setString("P" + pid);
    }
}

public class main_interface extends JFrame {
    private JLabel J1;
    private ImageIcon I1;
    private JButton input;
    private JLabel J2;
    private int temp_num;
    private int process_num;
    private int time_duration;
    private int arrival_time;
    private  int priority;
    private int n=0;
    private ArrayList<Integer> time_arr;
    private List<Process> processList = new ArrayList<>();
    private List<RealTimeProcess> processList1 = new ArrayList<>();



    public main_interface(int N1, Object selectedItem){
        process_num =N1;
        I1 = new ImageIcon("E:\\Documents\\4th semester\\OS\\untitled\\src\\gui\\os_background.jpeg");

        J1 =new JLabel(I1);
        J1.setSize(1500,800);
        J2 = new JLabel("OS Project");
        J2.setBounds(650,50,500,50);
        J2.setForeground(Color.white);
        J2.setFont(new Font("arial",Font.BOLD,35));

        JLabel J3 = new JLabel();
        if (selectedItem=="EDF" || selectedItem == "RMS"){
            J3.setBounds(500,100,500,50);
            J3.setText("Real Time Process Scheduler");

        }
        else
            J3.setText("Process Scheduler");

        J3.setBounds(600,100,500,50);
        J3.setForeground(Color.white);
        J3.setFont(new Font("arial",Font.BOLD,35));


        JLabel J5 = new JLabel("Process 1 Time Duration");
        J5.setBounds(200,250,500,50);
        J5.setForeground(Color.white);
        J5.setFont(new Font("arial",Font.PLAIN,20));

        JLabel J6 = new JLabel("Process 1 Arrival Time");
        J6.setBounds(200,350,500,50);
        J6.setForeground(Color.white);
        J6.setFont(new Font("arial",Font.PLAIN,20));


        JTextField J7 = new JTextField();
        J7.setBounds(200,300,500,35);
        J7.setFont(new Font("arial",Font.PLAIN,20));
        input = new JButton("Next");
        input.setBounds(1000,400,100,50);
        input.setBackground(Color.gray);
        input.setFocusable(false);

        JTextField J8 = new JTextField();
        J8.setBounds(200,400,500,35);
        J8.setFont(new Font("arial",Font.PLAIN,20));

        JLabel J9 = new JLabel("Process 1 Priority");
        J9.setBounds(200,450,500,50);
        J9.setForeground(Color.white);
        J9.setFont(new Font("arial",Font.PLAIN,20));


        JTextField J10 = new JTextField();
        J10.setBounds(200,500,500,35);
        J10.setFont(new Font("arial",Font.PLAIN,20));



        J1.add(input);

        if(selectedItem=="FCFS"){
            J1.add(J8);
            J1.add(J6);
        }
        else if (selectedItem=="Priority" || selectedItem =="RMS" || selectedItem =="EDF" ){
            J1.add(J8);
            J1.add(J6);
            J1.add(J9);
            J1.add(J10);
            if ( selectedItem =="RMS" || selectedItem =="EDF" ){
                J9.setText("Process 1 Period");
            }
        }
        else if (selectedItem=="SJF" || selectedItem=="Round Robin"){
            J5.setBounds(200,350,500,50);
            J7.setBounds(200,400,500,35);
        }

        J1.add(J2);
        J1.add(J3);
        J1.add(J5);
        J1.add(J7);





        JFrame frame = new JFrame("OS Project");
        frame.add(J1);

        frame.setSize(1500,800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (temp_num<=process_num){
                    String S1=J5.getText();
                    String[] S2=S1.split("\\s+");
                    temp_num=Integer.parseInt(S2[1]);
                    time_duration =Integer.parseInt(J7.getText());

                    if (selectedItem=="FCFS") {
                        arrival_time = Integer.parseInt(J8.getText());
                        processList.add(new Process(temp_num, arrival_time, time_duration));
                    }
                    else if (selectedItem=="Round Robin"  ){
                        processList.add(new Process(temp_num, n, time_duration));
                        n++;
                    }
                    else if (selectedItem=="SJF"){
                        processList.add(new Process(temp_num, 0, time_duration));


                    }
                    else if (selectedItem=="Priority"){
                        priority = Integer.parseInt(J10.getText());
                        processList.add(new Process(temp_num, arrival_time, time_duration,priority));
                    }
                    else if (selectedItem=="EDF" || selectedItem=="RMS"){
                        arrival_time = Integer.parseInt(J8.getText());
                        priority = Integer.parseInt(J10.getText());
                        processList1.add(new RealTimeProcess(temp_num, arrival_time, time_duration,priority));
                    }
                    
                    if(temp_num==process_num ){
                        if (selectedItem=="EDF" || selectedItem=="RMS")
                            new Console(process_num, processList1,selectedItem);
                        else
                            new Console(process_num, processList,selectedItem,0);

                        frame.setVisible(false);
                    }
                        temp_num++;
                    if(temp_num<=process_num){
                        J5.setText("Process "+temp_num +" Time Duration");
                        J6.setText("Process "+temp_num +" Arrival Time");
                        if (selectedItem=="RMS" || selectedItem=="EDF")
                            J9.setText("Process "+temp_num +" Period");

                        else
                            J9.setText("Process "+temp_num +" Priority");

                    }


                    J7.setText("");
                    J8.setText("");
                    J10.setText("");

                    if(temp_num==process_num){
                        input.setText("Schedule");

                    }


                }


            }
        });
    }


    public static void main(String[] arg){
        new main_interface(7, "EDF");

    }}