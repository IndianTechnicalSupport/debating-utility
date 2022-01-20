package com.indiantechnicalsupport.debatingutility;

import java.util.ArrayList;
import java.util.concurrent.*;

public class BellManager {

    private ArrayList<Integer> originalBellTimes;
    private ArrayList<Integer> originalBellNumbers;
    private ArrayList<Integer> workingBellTimes;
    private ArrayList<Integer> workingBellNumbers;

    private ScheduledThreadPoolExecutor bellThreadPool;
    private ArrayList<ScheduledFuture<?>> threadTasks;

    public BellManager() {
        this.bellThreadPool = new ScheduledThreadPoolExecutor(3);
        this.threadTasks = new ArrayList<ScheduledFuture<?>>();

        this.workingBellTimes = new ArrayList<Integer>();
        this.workingBellNumbers = new ArrayList<Integer>();
        this.originalBellTimes = new ArrayList<Integer>();
        this.originalBellNumbers = new ArrayList<Integer>();

        // DUMMY VARIABLES FOR TESTING FUNCTIONALITY

        // this.originalBellTimes.add(120000); // Bell 1 at 2 minutes
        // this.originalBellTimes.add(240000); // Bell 2 at 4 minutes
        // this.originalBellTimes.add(270000); // Bell 3 at 4 minutes 30 seconds
        
        this.originalBellTimes.add(5000);  // Bell 1 at 5 seconds
        this.originalBellTimes.add(10000); // Bell 2 at 10 seconds
        this.originalBellTimes.add(15000); // Bell 3 at 15 seconds

        this.workingBellTimes = this.originalBellTimes;

        this.originalBellNumbers.add(1); // 1 bell  at first ring
        this.originalBellNumbers.add(2); // 2 bells at first ring
        this.originalBellNumbers.add(3); // 3 bells at first ring

        this.workingBellNumbers = this.originalBellNumbers;
    }

    public void setOriginalBellTimes(ArrayList<Integer> bellTimes) {
        this.originalBellTimes = bellTimes;
    }

    public void prepareBells() {
        
        // Add tasks to threadpool in the form of bell ringers
        this.bellThreadPool = new ScheduledThreadPoolExecutor(3);
        this.threadTasks = new ArrayList<ScheduledFuture<?>>();

        // Iterate over bells to be scheduled, add to thread pool as scheduled future tasks
        for (int i = 0; i < this.workingBellNumbers.size(); i ++) {
            ScheduledFuture<?> task = this.bellThreadPool.schedule(new BellRinger(this.workingBellNumbers.get(i)), this.workingBellTimes.get(i), TimeUnit.MILLISECONDS);
            this.threadTasks.add(task);
        }

    }

    public void pauseBells(Model.Stopwatch activeStopwatch) {
        long elapsed = activeStopwatch.getElapsed();

        ArrayList<Integer> updatedBellTimes = new ArrayList<Integer>();
        ArrayList<Integer> updatedBellNumbers = new ArrayList<Integer>();

        // Calculating new times for remaining bells
        for (int i = 0; i < this.originalBellTimes.size(); i ++) {
            if (this.originalBellTimes.get(i) < elapsed) { // Bell has already rung
                continue;
            } else { // Add bell back into list to be scheduled, with remaining time shortened
                updatedBellTimes.add((Integer) (int) (this.originalBellTimes.get(i) - elapsed));
                updatedBellNumbers.add(originalBellNumbers.get(i));
            }
        }

        // Update working bells array list
        this.workingBellNumbers = updatedBellNumbers;
        this.workingBellTimes = updatedBellTimes;

        // Cancelling remaining tasks for other bells
        this.cancelBellTasks();
    }

    public void resetBells() {

        if (!(this.bellThreadPool.isShutdown())) { // Not previously shutdown, need to cancel scheduled bell ringing
            this.cancelBellTasks();
        }

        // Reset working bell times and number of rings
        this.workingBellTimes = this.originalBellTimes;
        this.workingBellNumbers = this.originalBellNumbers;
    }

    public void cancelBellTasks() {
        // Cancelling remaining bell-ringing tasks
        for (int j = 0; j < this.threadTasks.size(); j ++) {
            boolean successfulCancel = this.threadTasks.get(j).cancel(true);

            if (!successfulCancel) {
                System.out.println("Unable to cancel scheduled bell ringer!");
            }
        }

        // All tasks cancelled, shutdown thread pool
        this.bellThreadPool.shutdownNow();
    }
}
