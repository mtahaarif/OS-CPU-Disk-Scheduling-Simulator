import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DiskSchedulingSimulation {

    private static final int MAX_CYLINDER = 200;
    private static final int REQUEST_COUNT = 20;
    private static final int HEAD_POSITION = 100;
    private static final int DELAY = 500; // Delay in milliseconds

    private enum Algorithm { FCFS, SSTF, SCAN, CSCAN, LOOK, CLOOK }

    private List<Integer> requests;
    private List<Integer> sequence;
    private XYSeries series;
    private XYSeries headSeries;
    private ChartPanel chartPanel;
    private javax.swing.Timer timer;
    private int currentIndex;
    private int currentHeadPosition;
    private Algorithm selectedAlgorithm;
    private boolean movingUpward; // For SCAN, CSCAN, LOOK, and CLOOK

    public DiskSchedulingSimulation() {
        requests = new ArrayList<>();
        sequence = new ArrayList<>();
        series = new XYSeries("Disk Head Movement");
        headSeries = new XYSeries("Requests");

        // Adding initial position of head
        headSeries.add(0, HEAD_POSITION);

        // Initialize the graph
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(headSeries);

        JFreeChart chart = ChartFactory.createXYLineChart("Disk Scheduling Simulation",
                "Time", "Disk Cylinder", dataset);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame("Disk Scheduling Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);

        // Create algorithm selection dropdown
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Select Algorithm:");
        panel.add(label);
        JComboBox<Algorithm> algorithmComboBox = new JComboBox<>(Algorithm.values());
        algorithmComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                Algorithm selectedAlgorithm = (Algorithm) comboBox.getSelectedItem();
                generateRequestsAndStart(selectedAlgorithm);
            }
        });
        panel.add(algorithmComboBox);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        // Default selected algorithm
        selectedAlgorithm = Algorithm.FCFS;
        // Generate requests and start the simulation
        generateRequestsAndStart(selectedAlgorithm);
    }

    private void generateRequestsAndStart(Algorithm algorithm) {
        if (timer != null && timer.isRunning()) {
            timer.stop(); // Stop the timer if it's running
        }
        requests.clear();
        sequence.clear();
        series.clear();
        headSeries.clear();
        currentIndex = 0;
        currentHeadPosition = HEAD_POSITION;

        Random random = new Random();
        for (int i = 0; i < REQUEST_COUNT; i++) {
            int request = random.nextInt(MAX_CYLINDER);
            requests.add(request);
        }

        // Shuffle or sort requests based on the selected algorithm
        switch (algorithm) {
            case FCFS:
                Collections.shuffle(requests); // Randomize for FCFS
                break;
            case SSTF:
                Collections.sort(requests, Comparator.comparingInt(req -> Math.abs(HEAD_POSITION - req)));
                break;
            case SCAN:
            case CSCAN:
            case LOOK:
            case CLOOK:
                Collections.sort(requests); // Sort for SCAN, CSCAN, LOOK, CLOOK
                movingUpward = true; // Initialize the direction
                break;
        }

        series.add(0, HEAD_POSITION);
        chartPanel.repaint(); // Refresh the chart

        // Initialize timer
        timer = new javax.swing.Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGraph();
            }
        });

        timer.start();

        // Show order of service
        if (selectedAlgorithm != algorithm) {
            showOrderOfService(sequence);
        }
    }

    private void updateGraph() {
        if (currentIndex < requests.size()) {
            int request = requests.get(currentIndex);
            sequence.add(request);
            series.add(currentIndex + 1, request);
            headSeries.add(currentIndex + 1, request);
            currentHeadPosition = request;
            currentIndex++;
            chartPanel.repaint();
        } else {
            timer.stop();
            System.out.println("Order of service:");
            for (int r : sequence) {
                System.out.println("Servicing request: " + r);
            }
        }

        if (selectedAlgorithm == Algorithm.SCAN || selectedAlgorithm == Algorithm.CSCAN ||
                selectedAlgorithm == Algorithm.LOOK || selectedAlgorithm == Algorithm.CLOOK) {
            handleMovingDirection();
        }
    }

    private void handleMovingDirection() {
        if (currentHeadPosition == 0 || currentHeadPosition == MAX_CYLINDER) {
            movingUpward = !movingUpward; // Change direction at boundary
        }
    }

    private void showOrderOfService(List<Integer> sequence) {
        StringBuilder message = new StringBuilder("Order of Service:\n");
        for (int r : sequence) {
            message.append("Servicing request: ").append(r).append("\n");
        }
        JOptionPane.showMessageDialog(chartPanel, message.toString(), "Order of Service", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DiskSchedulingSimulation();
            }
});
}
}