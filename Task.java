import java.util.concurrent.ScheduledFuture;

public class Task implements Runnable {
    private String name;
    private int period;
    private int executionTime;
    private int deadline;
    private int executionCount;
    private long executionStartTime;
    private long executionEndTime;
    private ScheduledFuture<?> future;

    public Task(String name, int period, int executionTime) {
        this.name = name;
        this.period = period;
        this.executionTime = executionTime;
        this.deadline = period; // For simplicity, deadline equals period
        this.executionCount = 0;
    }

    public int getPeriod() {
        return period;
    }

    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }

    public long getExecutionStartTime() {
        return executionStartTime;
    }

    public long getExecutionEndTime() {
        return executionEndTime;
    }

    public int getExecutionCount() {
        return executionCount;
    }

    @Override
    public void run() {
        executionStartTime = System.currentTimeMillis();
        System.out.println("Executing task: " + name);
        try {
            Thread.sleep(executionTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executionEndTime = System.currentTimeMillis();
        executionCount++;
        if (!future.isCancelled()) {
            System.out.println("Completed task: " + name);
        }
    }
}
