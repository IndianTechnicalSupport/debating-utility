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
        this.dedicatedButtonBellRinger = new BellRinger(1);

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
        // this.view.getStartButton().addActionListener(e -> this.startBellFunctionality());
        // this.view.getStartButton().addActionListener(e -> this.bellManager.prepareBells());
        this.view.getStartButton().addActionListener(e -> this.model.getStopwatch().getBellManager().prepareBells());
        this.view.getStartButton().addActionListener(e -> this.disableSpeakerControlButtons());

        // Stop Button
        this.view.getStopButton().addActionListener(e -> this.model.getStopwatch().stopStopwatch());
        this.view.getStopButton().addActionListener(e -> this.stopwatchDisplayUpdate.stop());
        // this.view.getStopButton().addActionListener(e -> this.pauseBellFunctionality());
        // this.view.getStopButton().addActionListener(e -> this.bellManager.pauseBells(this.model.getStopwatch()));
        this.view.getStopButton().addActionListener(e -> this.model.getStopwatch().getBellManager().pauseBells(this.model.getStopwatch()));
        this.view.getStopButton().addActionListener(e -> this.enableSpeakerControlButtons());

        // Reset Button
        this.view.getResetButton().addActionListener(e -> this.view.setElapsedTime(0, 0));
        this.view.getResetButton().addActionListener(e -> this.model.getStopwatch().resetStopwatch());
        this.view.getResetButton().addActionListener(e -> this.stopwatchDisplayUpdate.stop());
        // this.view.getResetButton().addActionListener(e -> this.bellManager.resetBells());
        this.view.getResetButton().addActionListener(e -> this.model.getStopwatch().getBellManager().resetBells());
        this.view.getResetButton().addActionListener(e -> this.enableSpeakerControlButtons());
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

        this.stopwatchDisplayUpdate = new Timer(SECOND_UPDATE_DELAY, displayUpdate);
        this.stopwatchDisplayUpdate.start();

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

    public void enableSpeakerControlButtons() {
        if (this.nextSpeakerAllowed) {
            this.view.getNextSpeakerButton().setEnabled(true);
        }

        if (this.prevSpeakerAllowed) {
            this.view.getPrevSpeakerButton().setEnabled(true);
        }
    }

    public void disableSpeakerControlButtons() {
        this.view.getNextSpeakerButton().setEnabled(false);
        this.view.getPrevSpeakerButton().setEnabled(false);
    }

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
