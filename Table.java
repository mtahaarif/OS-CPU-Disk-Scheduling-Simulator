import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;



public class Table {
    private List<Process> process1;

    public Table(int process_num, List<Process> processList, Object selectedItem) {
        SwingUtilities.invokeLater(() -> {
            // Create the frame
            JFrame frame = new JFrame("OS Project");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            // Load the background image
            BufferedImage backgroundImage = null;
            try {
                backgroundImage = ImageIO.read(new File("E:\\Documents\\4th semester\\OS\\untitled\\src\\gui\\os_background.jpeg"));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

            // Create the custom panel with background image
            BufferedImage finalBackgroundImage = backgroundImage;
            JPanel backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (finalBackgroundImage != null) {
                        g.drawImage(finalBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };
            backgroundPanel.setLayout(new BorderLayout());

            // Define column names for the process table
            String[] processColumnNames = new String[5];
            if (selectedItem!="Priority")
                processColumnNames = new String[]{"Process ID", "Arrival Time", "Burst Time", "Waiting Time", "Turnaround Time"};
            else
                processColumnNames = new String[]{"Process ID", "Priority", "Burst Time", "Waiting Time", "Turnaround Time"};

            // Sort process list by arrival time
            if (selectedItem=="FCFS" || selectedItem=="Round Robin")
                processList.sort(Comparator.comparingInt(p -> p.arrivalTime));
            else if (selectedItem=="SJF")
                processList.sort(Comparator.comparingInt(p -> p.burstTime));
            else if (selectedItem=="Priority")
                processList.sort(Comparator.comparingInt((Process p) -> p.arrivalTime).thenComparingInt(p -> p.priority));

            Object[][] data = new Object[process_num][5];
            int[] rem_bt = new int[process_num];


            int     currentTime = 0,
                    i = 0,qt=3,
                    end_time = 0;

            Object[][] ganttData;
            String []process_arr1;

            if(selectedItem=="Round Robin"){
                int j=0;
                for (Process P1 : processList) {
                    rem_bt[j]=P1.burstTime;
                    j++;
                }
                while (true) {
                    j=0;
                    boolean done = true;
                    for (Process P1 : processList) {
                        if (rem_bt[j] > 0) {
                            done = false;
                            if (rem_bt[j] > qt) {
                                currentTime += qt;
                                rem_bt[j] -= qt;
                            } else {
                                currentTime += rem_bt[j];
                                P1.waitingTime = currentTime - P1.burstTime;
                                rem_bt[j] = 0;
                                P1.turnaroundTime = currentTime;
                            }
                        }
                        j++;
                    }
                    if (done)
                        break;
                }

                process1=processList;
                process1.sort(Comparator.comparingInt(p -> p.turnaroundTime));
                end_time = process1.get(process_num-1).turnaroundTime;
                process1.sort(Comparator.comparingInt(p -> p.arrivalTime));

            }

            for (Process process : processList) {
                if (selectedItem!="Round Robin") {
                    if (process.arrivalTime > currentTime)
                        currentTime = process.arrivalTime;

                    process.startTime = currentTime;
                    process.waitingTime = currentTime - process.arrivalTime;
                    process.turnaroundTime = process.waitingTime + process.burstTime;
                    currentTime += process.burstTime;
                    process.completionTime = currentTime;
                }
                data[i][0] = "P" + process.pid;
                if (selectedItem!="Priority")
                    data[i][1] = process.arrivalTime;
                else
                    data[i][1] = process.priority;
                data[i][2] = process.burstTime;
                data[i][3] = process.waitingTime;
                data[i][4] = process.turnaroundTime;
                if (selectedItem!="Round Robin")
                    end_time = Math.max(end_time, process.completionTime);

                i++;
            }


            // Create the table model for the process table
            DefaultTableModel processTableModel = new DefaultTableModel(data, processColumnNames);

            // Create the process JTable
            JTable processTable = new JTable(processTableModel) {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                    Component c = super.prepareRenderer(renderer, row, column);
                    if (row % 2 == 0) {
                        c.setBackground(new Color(220, 220, 255)); // Light blue for even rows
                    } else {
                        c.setBackground(new Color(255, 255, 220)); // Light yellow for odd rows
                    }
                    return c;
                }
            };

            // Customize the table header
            JTableHeader processHeader = processTable.getTableHeader();
            processHeader.setBackground(new Color(100, 100, 150)); // Darker blue
            processHeader.setForeground(Color.WHITE); // White text

            // Add the process table to a JScrollPane
            JScrollPane processScrollPane = new JScrollPane(processTable);


            ganttData = new Object[1][end_time];
            String[] ganttColumnNames = new String[end_time];

            for (int k = 0; k < ganttColumnNames.length; k++) {
                ganttColumnNames[k] = String.valueOf(k);
            }

            // Create sample data for the Gantt chart table
            String[] P1 = new String[end_time];

            if (selectedItem!="Round Robin"){
                for (int j = 0; j < process_num; j++) {
                    int start = processList.get(j).startTime;
                    int end = processList.get(j).completionTime;
                    for (int k = start; k < end; k++) {
                        P1[k] = "P" + processList.get(j).pid;
                    }
                }
                ganttData[0] = P1;
            }
            else{

                ganttData = new Object[1][end_time];

                int j=0;
                for (Process Process : processList) {
                    rem_bt[j]=Process.burstTime;
                    j++;
                }

                for (int k=0;k<end_time;){
                    for(int l=0;l<process_num;l++){
                        for (int m=0;m<qt;m++){
                            if (rem_bt[l]>0){
                                P1[k] = "P" + processList.get(l).pid;
                                rem_bt[l]--;
                                k++;
                            }
                        }
                    }
                }
            }
            ganttData[0]=P1;



            // Create the table model for the Gantt chart table
            DefaultTableModel ganttTableModel = new DefaultTableModel(ganttData, ganttColumnNames);

            // Create the Gantt chart JTable
            JTable ganttTable = new JTable(ganttTableModel) {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                    Component c = super.prepareRenderer(renderer, row, column);
                    String process = (String) getValueAt(row, column);
                    if (process != null) {
                        switch (process) {
                            case "P1":
                                c.setBackground(new Color(255, 100, 100)); // Light red for P1
                                break;
                            case "P2":
                                c.setBackground(new Color(100, 255, 100)); // Light green for P2
                                break;
                            case "P3":
                                c.setBackground(new Color(100, 100, 255)); // Light blue for P3
                                break;
                            case "P4":
                                c.setBackground(new Color(255, 255, 100)); // Light yellow for P4
                                break;
                            case "P5":
                                c.setBackground(new Color(255, 165, 0)); // Orange for P5
                                break;
                            case "P6":
                                c.setBackground(new Color(75, 0, 130)); // Indigo for P6
                                break;
                            case "P7":
                                c.setBackground(new Color(238, 130, 238)); // Violet for P7
                                break;
                            case "P8":
                                c.setBackground(new Color(0, 255, 255)); // Cyan for P8
                                break;
                            case "P9":
                                c.setBackground(new Color(255, 192, 203)); // Pink for P9
                                break;
                            case "P10":
                                c.setBackground(new Color(192, 192, 192)); // Light gray for P10
                                break;
                            default:
                                c.setBackground(Color.WHITE); // Default white for empty cells
                                break;
                        }
                    } else {
                        c.setBackground(Color.WHITE); // Default white for empty cells
                    }
                    return c;
                }
            };

            // Customize the Gantt chart table header
            JTableHeader ganttHeader = ganttTable.getTableHeader();
            ganttHeader.setBackground(new Color(150, 150, 150)); // Grey
            ganttHeader.setForeground(Color.WHITE); // White text

            // Add the Gantt chart table to a JScrollPane
            JScrollPane ganttScrollPane = new JScrollPane(ganttTable);

            // Add the process table and the Gantt chart table to the custom background panel
            backgroundPanel.add(processScrollPane, BorderLayout.CENTER);
            backgroundPanel.add(ganttScrollPane, BorderLayout.SOUTH);

            // Set the custom background panel as the content pane of the frame
            frame.setContentPane(backgroundPanel);

            // Make the frame visible
            frame.setVisible(true);
        });


    }
}

