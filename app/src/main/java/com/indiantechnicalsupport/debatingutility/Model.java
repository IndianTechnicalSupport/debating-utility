package com.indiantechnicalsupport.debatingutility;

import java.util.ArrayList;

public class Model {

    private Stopwatch currentStopwatch;
    private ArrayList<Stopwatch> stopwatchList;

    private Controller controller;

    private ArrayList<Integer> bestSpeakers; 

    public Model(int stopwatchNumber, Controller controller, View view) {
        this.stopwatchList = new ArrayList<Stopwatch>();

        for (int i = 0; i < stopwatchNumber; i ++) {
            this.stopwatchList.add(new Stopwatch(view));
        }

        this.controller = controller;
        this.currentStopwatch = this.stopwatchList.get(0);

        this.bestSpeakers = new ArrayList<Integer>();
    }

    public Stopwatch getStopwatch() {
        return this.currentStopwatch;
    }

    public ArrayList<Stopwatch> getStopwatchList() {
        return this.stopwatchList;
    }

    public void updateBestSpeakers(Integer position, boolean remove) {
        if (remove) {
            if (this.bestSpeakers.contains(position)) {
            this.bestSpeakers.remove(position);
            } else {
                return;
            }
        } else {
            if (this.bestSpeakers.contains(position)) {
                return;
            } else {
                this.bestSpeakers.add(position);
            }
        }
    }

    public void generateCogitoSummary() {
        String summaryString = SummaryGenerator.getCogitoSummaryString(
            this.controller.getView().getSummaryClassCode(), 
            this.controller.getView().getSpeakerNames(), 
            this.getStopwatchList(), 
            this.controller.getView().getSummaryBestTeam(), 
            this.bestSpeakers);
        
            this.controller.getView().setSummaryText(summaryString);
    }

    public void nextSpeaker() {
        // Check if next speaker will be last in list
        if (this.stopwatchList.indexOf(this.currentStopwatch) == this.stopwatchList.size() - 2) {
            this.controller.setNextSpeakerAllowed(false);
            this.controller.getView().getNextSpeakerButton().setEnabled(false);
        } else if (this.stopwatchList.indexOf(this.currentStopwatch) == 0) {
            // Speaker is first in list, remove restrictions
            this.controller.setPrevSpeakerAllowed(true);
        } else if (this.stopwatchList.indexOf(this.currentStopwatch) == -1) {
            // Error case
        }

        int nextIndex = this.stopwatchList.indexOf(this.currentStopwatch) + 1;

        this.currentStopwatch = this.stopwatchList.get(nextIndex);
        
        this.controller.getView().updateSpeakerString(this.controller.getView().getSpeakerTitles().get(nextIndex), this.controller.getView().getSpeakerNames().get(nextIndex));
    }

    public void prevSpeaker() {
        // Check if prev speaker will be first in list
        if (this.stopwatchList.indexOf(this.currentStopwatch) == 1) {
            this.controller.setPrevSpeakerAllowed(false);
            this.controller.getView().getPrevSpeakerButton().setEnabled(false);
        } else if (this.stopwatchList.indexOf(this.currentStopwatch) == this.stopwatchList.size() - 1) {
            // Speaker is last in list, remove restrictions
            this.controller.setNextSpeakerAllowed(true);
        } else if (this.stopwatchList.indexOf(this.currentStopwatch) == -1) {
            // Error case
        }

        int prevIndex = this.stopwatchList.indexOf(this.currentStopwatch) - 1;
            
        this.currentStopwatch = this.stopwatchList.get(prevIndex);

        this.controller.getView().updateSpeakerString(this.controller.getView().getSpeakerTitles().get(prevIndex), this.controller.getView().getSpeakerNames().get(prevIndex));
    }

    protected class Stopwatch {
        private long milliStart;
        private long milliEnd;

        private BellManager stopwatchBellManager;

        protected Stopwatch(View view) {
            this.milliStart = 0;
            this.milliEnd = 0;

            this.stopwatchBellManager = new BellManager(view);
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
