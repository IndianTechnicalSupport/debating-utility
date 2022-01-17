package com.indiantechnicalsupport.debatingutility;

import java.util.ArrayList;

public class Model {

    private Stopwatch stopwatch;
    private ArrayList<Stopwatch> stopwatchList;

    private boolean stopwatchIsRunning;

    public Model() {
        this.stopwatch = new Stopwatch();
    }

    public Stopwatch getStopwatch() {
        return this.stopwatch;
    }

    protected class Stopwatch {
        private long milliStart;
        private long milliEnd;

        protected Stopwatch() {
            this.milliStart = 0;
            this.milliEnd = 0;
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
    }
}
