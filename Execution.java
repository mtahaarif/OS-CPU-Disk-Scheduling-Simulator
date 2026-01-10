import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class RealTimeProcess {
    int pid;
    int arrivalTime;
    int burstTime;
    int period;
    int deadline;
    int remainingTime;
    int nextStartTime;
    JProgressBar progressBar;

    public RealTimeProcess(int pid, int arrivalTime, int burstTime, int period) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.period = period;
        this.deadline = arrivalTime + period;
        this.remainingTime = burstTime;
        this.nextStartTime = arrivalTime;
        this.progressBar = new JProgressBar(0, burstTime);
        this.progressBar.setStringPainted(true);
        this.progressBar.setString("P" + pid);
    }

    public void reset() {
        this.remainingTime = burstTime;
        this.deadline += period;
        this.nextStartTime += period;
        this.progressBar.setValue(0);
    }
}

public class Execution extends JFrame {
    private JTable processTable;
    private JTable ganttTable;
    private DefaultTableModel ganttTableModel;

    public Execution(int processNum, List<RealTimeProcess> processList, String selectedItem) {
        setTitle("Real-Time System Scheduling");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Define column names for the process table
        String[] processColumnNames = {"Process ID", "Arrival Time", "Burst Time", "Period", "Deadline"};
        Object[][] processData = new Object[processNum][5];
        for (int i = 0; i < processNum; i++) {
            RealTimeProcess process = processList.get(i);
            processData[i][0] = "P" + process.pid;
            processData[i][1] = process.arrivalTime;
            processData[i][2] = process.burstTime;
            processData[i][3] = process.period;
            processData[i][4] = process.deadline;
        }

        // Create the table model for the process table
        DefaultTableModel processTableModel = new DefaultTableModel(processData, processColumnNames);

        // Create the process JTable
        processTable = new JTable(processTableModel) {
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
        add(processScrollPane, BorderLayout.CENTER);

        // Create the Gantt chart table model
        String[] ganttColumnNames = new String[90]; // Assume a maximum of 100 time units for simplicity
        for (int i = 0; i < ganttColumnNames.length; i++) {
            ganttColumnNames[i] = String.valueOf(i);
        }
        ganttTableModel = new DefaultTableModel(new Object[1][ganttColumnNames.length], ganttColumnNames);
        ganttTable = new JTable(ganttTableModel) {
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
        add(ganttScrollPane, BorderLayout.SOUTH);

        setVisible(true);

        new Scheduler(processList, selectedItem).execute();
    }

    private class Scheduler extends SwingWorker<Void, Void> {
        private List<RealTimeProcess> processList;
        private String algorithm;
        private int currentTime;

        public Scheduler(List<RealTimeProcess> processList, String algorithm) {
            this.processList = processList;
            this.algorithm = algorithm;
            this.currentTime = 0;
        }

        @Override
        protected Void doInBackground() throws Exception {
            if ("RMS".equals(algorithm)) {
                rateMonotonicScheduling();
            } else if ("EDF".equals(algorithm)) {
                earliestDeadlineFirstScheduling();
            }
            return null;
        }

        private void rateMonotonicScheduling() throws InterruptedException {
            while (true) {
                processList.sort(Comparator.comparingInt(p -> p.period));
                for (RealTimeProcess process : processList) {
                    if (currentTime >= process.nextStartTime) {
                        processExecution(process);
                    }
                }
                currentTime++;
                updateGanttChart();
                Thread.sleep(1000); // Simulate 1 second per time unit
            }
        }

        private void earliestDeadlineFirstScheduling() throws InterruptedException {
            while (true) {
                processList.sort(Comparator.comparingInt(p -> p.deadline));
                for (RealTimeProcess process : processList) {
                    if (currentTime >= process.nextStartTime) {
                        processExecution(process);
                    }
                }
                currentTime++;
                updateGanttChart();
                Thread.sleep(1000); // Simulate 1 second per time unit
            }
        }

        private void processExecution(RealTimeProcess process) throws InterruptedException {
            for (int i = 0; i < process.burstTime; i++) {
                if (process.remainingTime > 0 && currentTime < process.deadline) {
                    process.remainingTime--;
                    ganttTableModel.setValueAt("P" + process.pid, 0, currentTime);
                    currentTime++;
                    Thread.sleep(1000); // Simulate 1 second per unit of burst time
                }
            }
            process.reset();
        }

        private void updateGanttChart() {
            for (int i = 0; i < ganttTableModel.getColumnCount(); i++) {
                if (ganttTableModel.getValueAt(0, i) == null) {
                    ganttTableModel.setValueAt("", 0, i);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<RealTimeProcess> processList = new ArrayList<>();
        processList.add(new RealTimeProcess(1, 0, 2, 5));
        processList.add(new RealTimeProcess(2, 1, 1, 7));
        processList.add(new RealTimeProcess(3, 2, 3, 10));
        processList.add(new RealTimeProcess(4, 0, 2, 5));
        processList.add(new RealTimeProcess(5, 1, 1, 7));
        processList.add(new RealTimeProcess(6, 2, 3, 10));
        processList.add(new RealTimeProcess(7, 0, 2, 5));
        processList.add(new RealTimeProcess(8, 1, 1, 7));
        processList.add(new RealTimeProcess(9, 2, 3, 10));

        SwingUtilities.invokeLater(() -> new Execution(3, processList, "RMS"));
    }
}