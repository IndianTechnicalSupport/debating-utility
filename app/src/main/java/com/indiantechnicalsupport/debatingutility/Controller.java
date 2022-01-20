package com.indiantechnicalsupport.debatingutility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Controller {

    private Model model;
    private View view;

    private Timer stopwatchDisplayUpdate;

    private BellRinger dedicatedButtonBellRinger;

    private boolean nextSpeakerAllowed;
    private boolean prevSpeakerAllowed;

    public Controller() {
        this.model = new Model(6, this); // Default is 6 speakers
        this.view = new View();
        this.dedicatedButtonBellRinger = new BellRinger(1); // Button rings bell once

        // Starting at first speaker. conditions set by default
        this.nextSpeakerAllowed = true;
        this.prevSpeakerAllowed = false;
    }

    public void initialise() {
        this.initStopwatchDisplay();
        this.initStartStopFunctionality();
        this.initBellControlsFunctionality();
        this.initSpeakerControlsFunctionality();
    }

    public void initStartStopFunctionality() {

        // Start Button
        this.view.getStartButton().addActionListener(e -> this.model.getStopwatch().startStopwatch());
        this.view.getStartButton().addActionListener(e -> this.initStopwatchDisplay());
        this.view.getStartButton().addActionListener(e -> this.model.getStopwatch().getBellManager().prepareBells());
        this.view.getStartButton().addActionListener(e -> this.toggleSpeakerControlButtons(false));

        // Stop Button
        this.view.getStopButton().addActionListener(e -> this.model.getStopwatch().stopStopwatch());
        this.view.getStopButton().addActionListener(e -> this.stopwatchDisplayUpdate.stop());
        this.view.getStopButton().addActionListener(e -> this.model.getStopwatch().getBellManager().pauseBells(this.model.getStopwatch()));
        this.view.getStopButton().addActionListener(e -> this.toggleSpeakerControlButtons(true));

        // Reset Button
        this.view.getResetButton().addActionListener(e -> this.view.setElapsedTime(0, 0));
        this.view.getResetButton().addActionListener(e -> this.model.getStopwatch().resetStopwatch());
        this.view.getResetButton().addActionListener(e -> this.stopwatchDisplayUpdate.stop());
        this.view.getResetButton().addActionListener(e -> this.model.getStopwatch().getBellManager().resetBells());
        this.view.getResetButton().addActionListener(e -> this.toggleSpeakerControlButtons(true));
    }

    public void initStopwatchDisplay() {
        final int SECOND_UPDATE_DELAY = 1000;

        ActionListener displayUpdate = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = model.getStopwatch().getElapsed();
                
                int minutes = (int) Math.floor(elapsed / 60000);
                int seconds = (int) ((elapsed / 1000) - minutes * 60);

                view.setElapsedTime(minutes, seconds);
            }
        };

        // Activate stopwatch display update
        this.stopwatchDisplayUpdate = new Timer(SECOND_UPDATE_DELAY, displayUpdate);
        this.stopwatchDisplayUpdate.start();

        // Each time a new speaker is loaded, display updates on click
        this.getView().getNextSpeakerButton().addActionListener(displayUpdate);
        this.getView().getPrevSpeakerButton().addActionListener(displayUpdate);
    }

    public void initBellControlsFunctionality() {
        this.view.getDingButton().addActionListener(e -> this.dedicatedButtonBellRinger.soundBellOnce());
    }

    public void initSpeakerControlsFunctionality() {
        // Next Speaker Button
        this.view.getNextSpeakerButton().addActionListener(e -> this.model.nextSpeaker());

        // Previous Speaker Button
        this.view.getPrevSpeakerButton().addActionListener(e -> this.model.prevSpeaker());
        this.view.getPrevSpeakerButton().setEnabled(false); // Starts on first speaker by default
    }

    public void toggleSpeakerControlButtons(boolean permitted) {
        if (this.nextSpeakerAllowed) {
            this.view.getNextSpeakerButton().setEnabled(permitted);
        }

        if (this.prevSpeakerAllowed) {
            this.view.getPrevSpeakerButton().setEnabled(permitted);
        }
    }

    // Getter/Setter Functions

    public Model getModel() {
        return this.model;
    }

    public View getView() {
        return this.view;
    }

    public void setNextSpeakerAllowed(boolean allowed) {
        this.nextSpeakerAllowed = allowed;
        this.getView().getNextSpeakerButton().setEnabled(true);
    }

    public void setPrevSpeakerAllowed(boolean allowed) {
        this.prevSpeakerAllowed = allowed;
        this.getView().getPrevSpeakerButton().setEnabled(true);
    }
}
