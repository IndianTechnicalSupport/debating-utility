package com.indiantechnicalsupport.debatingutility;

import java.awt.Color;

import javax.swing.JPanel;

public class ScreenColourChanger implements Runnable {

    private Color colour;
    private JPanel targetPanel;

    public ScreenColourChanger(Color colour, JPanel targetPanel) {
        this.colour = colour;
        this.targetPanel = targetPanel;
    }
    
    public void run() { // Change screen colour
        ScreenColourChanger.changePanelColour(this.targetPanel, this.colour);
    }

    public static void changePanelColour(JPanel targetPanel, Color colour) {
        targetPanel.setBackground(colour);
    }

}
