import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;



public class ProgressBar extends JFrame {
    private JPanel processPanel;

    public ProgressBar(int processNum, List<Process> processList, String selectedItem) {
        setTitle("OS Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);


        processPanel = new JPanel();
        processPanel.setLayout(new GridLayout(processNum, 1, 10, 10));
        for (Process process : processList) {
            processPanel.add(process.progressBar);
        }
        add(new JScrollPane(processPanel), BorderLayout.CENTER);

        setVisible(true);

        new Scheduler(processList, selectedItem).execute();
    }

    private class Scheduler extends SwingWorker<Void, Void> {
        private List<Process> processList;
        private String algorithm;

        public Scheduler(List<Process> processList, String algorithm) {
            this.processList = processList;
            this.algorithm = algorithm;
        }

        @Override
        protected Void doInBackground() throws Exception {
            if ("Round Robin".equals(algorithm)) {
                roundRobinScheduling(3);
            } else if ("FCFS".equals(algorithm)) {
                fcfsScheduling();
            } else if ("SJF".equals(algorithm)) {
                sjfScheduling();
            } else if ("Priority".equals(algorithm)) {
                priorityScheduling();
            }
            return null;
        }

        private void roundRobinScheduling(int quantum) throws InterruptedException {
            int currentTime = 0;
            boolean done;
            do {
                done = true;
                for (Process process : processList) {
                    if (process.remainingTime > 0) {
                        done = false;
                        int executionTime = Math.min(quantum, process.remainingTime);
                        processExecution(process, executionTime);
                        currentTime += executionTime;
                        process.remainingTime -= executionTime;
                    }
                }
            } while (!done);
        }

        private void fcfsScheduling() throws InterruptedException {
            processList.sort(Comparator.comparingInt(p -> p.arrivalTime));
            for (Process process : processList) {
                processExecution(process, process.burstTime);
            }
        }

        private void sjfScheduling() throws InterruptedException {
            processList.sort(Comparator.comparingInt(p -> p.burstTime));
            for (Process process : processList) {
                processExecution(process, process.burstTime);
            }
        }

        private void priorityScheduling() throws InterruptedException {
            processList.sort(Comparator.comparingInt(p -> p.priority));
            for (Process process : processList) {
                processExecution(process, process.burstTime);
            }
        }

        private void processExecution(Process process, int time) throws InterruptedException {
            for (int i = 0; i < time; i++) {
                Thread.sleep(1000); // Simulate 1 second per unit of burst time
                process.progressBar.setValue(process.progressBar.getValue() + 1);
            }
        }
    }

    public static void main(String[] args) {
        List<Process> processList = new ArrayList<>();
        processList.add(new Process(1, 0, 5, 1));
        processList.add(new Process(2, 2, 3, 3));
        processList.add(new Process(3, 4, 2, 2));
        processList.add(new Process(4, 6, 4, 4));
        processList.add(new Process(5, 8, 1, 5));
        processList.add(new Process(6, 0, 5, 1));
        processList.add(new Process(7, 2, 3, 3));
        processList.add(new Process(8, 4, 2, 2));
        processList.add(new Process(9, 6, 4, 4));
        processList.add(new Process(10, 8, 1, 5));

        SwingUtilities.invokeLater(() -> new ProgressBar(5, processList, "Round Robin"));
    }
}
