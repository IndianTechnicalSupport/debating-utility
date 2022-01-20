package com.indiantechnicalsupport.debatingutility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Controller {

    private Model model;
    private View view;

    private Timer stopwatchDisplayUpdate;

    private BellRinger dedicatedButtonBellRinger;

    public Controller() {
        this.model = new Model(6); // Default is 6 speakers
        this.view = new View();
        this.dedicatedButtonBellRinger = new BellRinger(1);
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
        this.view.getStartButton().addActionListener(e -> this.view.getNextSpeakerButton().setEnabled(false));
        this.view.getStartButton().addActionListener(e -> this.view.getPrevSpeakerButton().setEnabled(false));

        // Stop Button
        this.view.getStopButton().addActionListener(e -> this.model.getStopwatch().stopStopwatch());
        this.view.getStopButton().addActionListener(e -> this.stopwatchDisplayUpdate.stop());
        // this.view.getStopButton().addActionListener(e -> this.pauseBellFunctionality());
        // this.view.getStopButton().addActionListener(e -> this.bellManager.pauseBells(this.model.getStopwatch()));
        this.view.getStopButton().addActionListener(e -> this.model.getStopwatch().getBellManager().pauseBells(this.model.getStopwatch()));
        this.view.getStopButton().addActionListener(e -> this.view.getNextSpeakerButton().setEnabled(true));
        this.view.getStopButton().addActionListener(e -> this.view.getPrevSpeakerButton().setEnabled(true));

        // Reset Button
        this.view.getResetButton().addActionListener(e -> this.view.setElapsedTime(0, 0));
        this.view.getResetButton().addActionListener(e -> this.model.getStopwatch().resetStopwatch());
        this.view.getResetButton().addActionListener(e -> this.stopwatchDisplayUpdate.stop());
        // this.view.getResetButton().addActionListener(e -> this.bellManager.resetBells());
        this.view.getResetButton().addActionListener(e -> this.model.getStopwatch().getBellManager().resetBells());
        this.view.getResetButton().addActionListener(e -> this.view.getNextSpeakerButton().setEnabled(true));
        this.view.getResetButton().addActionListener(e -> this.view.getPrevSpeakerButton().setEnabled(true));
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
    }

    public void initBellControlsFunctionality() {
        this.view.getDingButton().addActionListener(e -> this.dedicatedButtonBellRinger.soundBellOnce());
    }

    public void initSpeakerControlsFunctionality() {
        // Next Speaker Button


        // Previous Speaker Button
        
    }

    public Model getModel() {
        return this.model;
    }

    public View getView() {
        return this.view;
    }
}
