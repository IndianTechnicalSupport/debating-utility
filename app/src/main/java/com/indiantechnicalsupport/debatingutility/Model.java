package com.indiantechnicalsupport.debatingutility;

import java.util.ArrayList;

public class Model {

    private Stopwatch stopwatch;
    private ArrayList<Stopwatch> stopwatchList;

    public Model(int stopwatchNumber) {
        this.stopwatch = new Stopwatch();

        this.stopwatchList = new ArrayList<Stopwatch>();

        for (int i = 0; i < stopwatchNumber; i ++) {
            this.stopwatchList.add(new Stopwatch());
        }

        // this.stopwatch = this.stopwatchList.get(0);
    }

    public Stopwatch getStopwatch() {
        return this.stopwatch;
    }

    protected class Stopwatch {
        private long milliStart;
        private long milliEnd;

        private BellManager stopwatchBellManager;

        protected Stopwatch() {
            this.milliStart = 0;
            this.milliEnd = 0;

            this.stopwatchBellManager = new BellManager();
        }

        public void startStopwatch() {
            if (this.milliEnd == 0) { // First time started
                this.milliStart = System.currentTimeMillis();
                this.milliEnd = -1;
                
            } else if (this.milliEnd == -1) { // Still running, do nothing
                return;
            } else { // Already paused and now restarted
                this.milliStart = System.currentTimeMillis() - (this.getElapsed());
                this.milliEnd = -1;
            }

        }

        public void stopStopwatch() {
            if (this.milliEnd == -1) {  // Running, update end time
                this.milliEnd = System.currentTimeMillis();
            } else {                    // Already stopped, do nothing
                return;
            }
        }

        public void resetStopwatch() {
            this.milliStart = 0;
            this.milliEnd = 0;
        }

        public long getElapsed() {
            if (this.milliEnd == 0 && this.milliStart == 0) {   // Not started yet
                return 0;
            } else if (this.milliEnd != -1) {                   // Ended
                return this.milliEnd - this.milliStart;
            } else {                                            // Still running
                return System.currentTimeMillis() - milliStart;
            }
        }

        public long getStart() {
            return this.milliStart;
        }

        public long getEnd() {
            return this.milliEnd;
        }

        public BellManager getBellManager() {
            return this.stopwatchBellManager;
        }
    }
}
