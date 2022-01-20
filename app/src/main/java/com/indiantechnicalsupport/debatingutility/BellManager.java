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
        
        this.originalBellTimes.add(5000);
        this.originalBellTimes.add(10000);
        this.originalBellTimes.add(15000);

        this.originalBellNumbers.add(1); // 1 bell  at first ring
        this.originalBellNumbers.add(2); // 2 bells at first ring
        this.originalBellNumbers.add(3); // 3 bells at first ring
    }

    public void setOriginalBellTimes(ArrayList<Integer> bellTimes) {
        this.originalBellTimes = bellTimes;
    }

    public void prepareBells() {
        
        // Add tasks to threadpool in the form of bell ringers
        this.bellThreadPool = new ScheduledThreadPoolExecutor(3);
        this.threadTasks = new ArrayList<ScheduledFuture<?>>();

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
            if (this.originalBellTimes.get(i) < elapsed) {
                continue;
            } else {
                updatedBellTimes.add((Integer) (int) (this.originalBellTimes.get(i) - elapsed));
                updatedBellNumbers.add(originalBellNumbers.get(i));
            }
        }

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
        this.workingBellTimes = this.originalBellNumbers;
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

        this.bellThreadPool.shutdownNow();
    }
}
