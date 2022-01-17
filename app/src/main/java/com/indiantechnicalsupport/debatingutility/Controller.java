package com.indiantechnicalsupport.debatingutility;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.Timer;

public class Controller {

    private Model model;
    private View view;

    private Timer stopwatchDisplayUpdate;
    private ArrayList<Timer> stopwatchBellUpdate;

    private BellManager bellManager;
    private BellRinger buttonBell;

    public Controller() {
        this.model = new Model();
        this.view = new View();
        this.bellManager = new BellManager();
        this.buttonBell = new BellRinger(1);
    }

    public void initialise() {
        this.initStartStopFunctionality();
        this.initBellControlsFunctionality();
    }

    public void initStartStopFunctionality() {

        this.initStopwatchDisplay();

        // Start Button
        this.view.getStartButton().addActionListener(e -> this.model.getStopwatch().startStopwatch());
        this.view.getStartButton().addActionListener(e -> this.initStopwatchDisplay());
        // this.view.getStartButton().addActionListener(e -> this.startBellFunctionality());
        this.view.getStartButton().addActionListener(e -> this.bellManager.prepareBells());

        // Stop Button
        this.view.getStopButton().addActionListener(e -> this.model.getStopwatch().stopStopwatch());
        this.view.getStopButton().addActionListener(e -> this.stopwatchDisplayUpdate.stop());
        // this.view.getStopButton().addActionListener(e -> this.pauseBellFunctionality());
        this.view.getStopButton().addActionListener(e -> this.bellManager.pauseBells(this.model.getStopwatch()));

        // Reset Button
        this.view.getResetButton().addActionListener(e -> this.view.setElapsedTime(0, 0));
        this.view.getResetButton().addActionListener(e -> this.model.getStopwatch().resetStopwatch());
        this.view.getResetButton().addActionListener(e -> this.stopwatchDisplayUpdate.stop());
        this.view.getResetButton().addActionListener(e -> this.bellManager.resetBells());
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

    // public void startBellFunctionality() {
    //     synchronized (this.bellThread) {
    //         if (this.bellThread.isAlive()) {
    //             this.bellThread.notifyAll();
    //         } else {
    //             this.bellThread.start();
    //         }
    //     }
    // }

    // public void pauseBellFunctionality() {
    //     synchronized (this.bellThread) {
    //         try {
    //             this.bellThread.wait();
    //         } catch (InterruptedException e) {
    
    //         }
    //     }
    // }

    public void initBellControlsFunctionality() {
        this.view.getDingButton().addActionListener(e -> this.buttonBell.soundBellOnce());
    }

    public Model getModel() {
        return this.model;
    }

    public View getView() {
        return this.view;
    }
}
