package com.indiantechnicalsupport.debatingutility;

import java.util.ArrayList;
import java.util.concurrent.*;

public class BellManager {

    private ArrayList<Integer> originalBellTimes;
    private ArrayList<Integer> originalBellNumbers;
    
    private ArrayList<Integer> workingBellTimes;
    private ArrayList<Integer> workingBellNumbers;

    private boolean isPaused;

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

        this.originalBellTimes.add(120000); // Bell 1 at 2 minutes
        this.originalBellTimes.add(240000); // Bell 2 at 4 minutes
        this.originalBellTimes.add(270000); // Bell 3 at 4 minutes 30 seconds

        this.originalBellNumbers.add(1); // 1 bell  at first ring
        this.originalBellNumbers.add(2); // 2 bells at first ring
        this.originalBellNumbers.add(3); // 3 bells at first ring
    }

    public void setOriginalBellTimes(ArrayList<Integer> bellTimes) {
        this.originalBellTimes = bellTimes;
    }

    public void prepareBells() {
        // Add tasks to threadpool in the form of bell ringers

        System.out.println("Bells scheduled");

        this.bellThreadPool = new ScheduledThreadPoolExecutor(3);
        this.threadTasks = new ArrayList<ScheduledFuture<?>>();

        if (isPaused) { // previously paused, use working times instead
            System.out.println(" using working times");
            for (int i = 0; i < this.workingBellNumbers.size(); i ++) {
                System.out.println(this.workingBellNumbers.get(i) + " " + this.workingBellTimes.get(i));
                ScheduledFuture<?> task = this.bellThreadPool.schedule(new BellRinger(this.workingBellNumbers.get(i)), this.workingBellTimes.get(i), TimeUnit.MILLISECONDS);
                this.threadTasks.add(task);
            }
        } else {
            for (int i = 0; i < this.originalBellNumbers.size(); i ++) {
                ScheduledFuture<?> task = this.bellThreadPool.schedule(new BellRinger(this.originalBellNumbers.get(i)), this.originalBellTimes.get(i), TimeUnit.MILLISECONDS);
                this.threadTasks.add(task);
            }
        }
    }

    public void pauseBells(Model.Stopwatch activeStopwatch) {
        long elapsed = activeStopwatch.getElapsed();

        System.out.println("\nPause bells\n");

        ArrayList<Integer> updatedBellTimes = new ArrayList<Integer>();
        ArrayList<Integer> updatedBellNumbers = new ArrayList<Integer>();

        // Calculating new times for remaining bells
        if (isPaused) {
            for (int i = 0; i < this.workingBellNumbers.size(); i ++) {
                System.out.println(elapsed + " : " + this.workingBellTimes.get(i));

                if (this.originalBellTimes.get(i) < elapsed) {
                    System.out.println("Bell not added.");
                    continue;
                } else {
                    System.out.println("Bell added with " + (this.workingBellTimes.get(i) - elapsed) + " time remaining");
                    updatedBellTimes.add((Integer) (int) (this.workingBellTimes.get(i) - elapsed));
                    updatedBellNumbers.add(workingBellNumbers.get(i));
                }
            }

            this.workingBellNumbers = updatedBellNumbers;
            this.workingBellTimes = updatedBellTimes;
        } else {
            for (int i = 0; i < this.originalBellNumbers.size(); i ++) {
                System.out.println(elapsed + " : " + this.originalBellTimes.get(i));

                if (this.originalBellTimes.get(i) < elapsed) {
                    System.out.println("Bell not added.");
                    continue;
                } else {
                    System.out.println("Bell added with " + (this.originalBellTimes.get(i) - elapsed) + " time remaining");
                    updatedBellTimes.add((Integer) (int) (this.originalBellTimes.get(i) - elapsed));
                    updatedBellNumbers.add(originalBellNumbers.get(i));
                }
            }

            this.workingBellNumbers = updatedBellNumbers;
            this.workingBellTimes = updatedBellTimes;
        }

        // Cancelling remaining tasks for other bells
        this.cancelBellTasks();

        this.isPaused = true;
    }

    public void resetBells() {

        if (!this.isPaused) { // Not previously paused, need to cancel scheduled bell ringing
            this.cancelBellTasks();
        }

        // Change pause flag
        this.isPaused = false;
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
