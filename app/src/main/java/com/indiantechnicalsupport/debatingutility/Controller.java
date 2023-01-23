package com.indiantechnicalsupport.debatingutility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.indiantechnicalsupport.debatingutility.Model.Stopwatch;

public class Controller {

    private Model model;
    private View view;

    private Timer stopwatchDisplayUpdate;

    private BellRinger dedicatedButtonBellRinger;

    private boolean nextSpeakerAllowed;
    private boolean prevSpeakerAllowed;

    private int numberSpeakers;

    public Controller() {
        this.numberSpeakers = 8; // Default is 8 speakers

        this.view = new View(this.numberSpeakers);
        this.model = new Model(numberSpeakers, this, this.view); 
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
        this.initSettingsFunctionality();
    }

    private void initStartStopFunctionality() {

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
        this.view.getResetButton().addActionListener(e -> this.view.getTimerDisplay().setBackground(new JPanel().getBackground()));
    }

    private void initStopwatchDisplay() {
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

    private void initBellControlsFunctionality() {
        this.view.getDingButton().addActionListener(e -> this.dedicatedButtonBellRinger.soundBellOnce());
    }

    private void initSpeakerControlsFunctionality() {
        // Next Speaker Button
        this.view.getNextSpeakerButton().addActionListener(e -> this.model.nextSpeaker());
        // Temp fix for colour changing
        this.view.getNextSpeakerButton().addActionListener(e -> this.view.getTimerDisplay().setBackground(new JPanel().getBackground()));

        // Previous Speaker Button
        this.view.getPrevSpeakerButton().addActionListener(e -> this.model.prevSpeaker());
        // Temp fix for colour changing
        this.view.getPrevSpeakerButton().addActionListener(e -> this.view.getTimerDisplay().setBackground(new JPanel().getBackground()));
        this.view.getPrevSpeakerButton().setEnabled(false); // Starts on first speaker by default
    }

    private void toggleSpeakerControlButtons(boolean permitted) {
        if (this.nextSpeakerAllowed) {
            this.view.getNextSpeakerButton().setEnabled(permitted);
        }

        if (this.prevSpeakerAllowed) {
            this.view.getPrevSpeakerButton().setEnabled(permitted);
        }
    }

    private void initSettingsFunctionality() {
        // Settings Tab
        this.view.getTabbedPane().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateBellTimes();
            }
        });

        // Summary 
        this.view.getGenerateSummaryButton().addActionListener(e -> this.model.generateCogitoSummary());
    }

    // Getter/Setter Functions

    public Model getModel() {
        return this.model;
    }

    public View getView() {
        return this.view;
    }

    public int getNumberSpeakers() {
        return this.numberSpeakers;
    }

    public void setNumberSpeakers(int updatedNumber) {
        this.numberSpeakers = updatedNumber;
    }

    public void setNextSpeakerAllowed(boolean allowed) {
        this.nextSpeakerAllowed = allowed;
        this.getView().getNextSpeakerButton().setEnabled(true);
    }

    public void setPrevSpeakerAllowed(boolean allowed) {
        this.prevSpeakerAllowed = allowed;
        this.getView().getPrevSpeakerButton().setEnabled(true);
    }

    private void updateBellTimes() {
        if (this.getView().getTabbedPane().getSelectedComponent().getName().equals("Settings")) { // Selected Settings, load text box
            ArrayList<Integer> bellTimes = this.getModel().getStopwatch().getBellManager().getOriginalBellTimes();
            this.view.redrawSettingsBellTimeElements(bellTimes);
        } else { // Selected Timekeeping, need to update bell times
            ArrayList<Stopwatch> stopwatches = this.getModel().getStopwatchList();
            ArrayList<Integer> updatedBellTimes = this.getView().getSettingsBellsTimes();

            for (int i = 0; i < stopwatches.size(); i ++) {
                stopwatches.get(i).getBellManager().setOriginalBellTimes(updatedBellTimes);
            }

            this.view.updateBellString(updatedBellTimes);
            this.view.setBellText(this.view.getBellString());
        }
    }
}
