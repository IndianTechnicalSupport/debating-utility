package com.indiantechnicalsupport.debatingutility;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.indiantechnicalsupport.debatingutility.Model.Stopwatch;

public class View extends JFrame {

    // Overall displayPanel
    JPanel displayPanel;

    // TabbedPane Elements
    JTabbedPane tabPane;

    // Timer Elements
    private JPanel timerPanel;

    private JPanel timerControls;
    private JButton start;
    private JButton stop;
    private JButton reset;

    private JPanel timerDisplay;
    private JLabel elapsedTime;

    private JLabel speakerText;
    private JLabel bellText;

    private JPanel speakerControls;
    private JButton nextSpeaker;
    private JButton prevSpeaker;

    private JPanel bellControls;
    private JButton bellPlaySound;
    private JButton bellSettings;

    // Settings Elements
    private JPanel settingsPanel;


    public View() {

        // Create window
        super("Debating Utility");

        // Set window close behaviour
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup GUI elements
        this.initGUI();

        // Add tabbed pane to main window
        this.add(this.tabPane);

        // Clean up and make GUI visible
        this.pack();
        this.setVisible(true);

    }

    private void initGUI() {

        // Setup tabbed pane
        this.tabPane = new JTabbedPane();
        ImageIcon tabIcon = new ImageIcon("./app/src/main/resources/timerTabIcon.png");

        // Setup panel elements
        this.initGUITimerElements();
        this.initGUISettingsElements();

        // Add panels to tab
        this.tabPane.addTab("Timekeeping", tabIcon, this.timerPanel, "Stopwatch and bell functions.");
        this.tabPane.addTab("Settings", tabIcon, this.settingsPanel,
                "Adjust settings, including the speaker and bell configurations.");

    }

    private void initGUITimerElements() {
        // final int COLUMNS = 2;
        // final int ROWS = 1;

        // Create overall JPanel for organising all components
        this.timerPanel = new JPanel(new GridBagLayout());

        /*
        ADD TIMEKEEPING AND BELL CONTROLS 
        */

        // Create JPanel for timekeeping controls
        this.timerControls = new JPanel();
        GridLayout timerControlsLayout = new GridLayout();
        // timerControlsLayout.setHgap(10);
        // timerControlsLayout.setVgap(10);
        this.timerControls.setLayout(timerControlsLayout);
        this.timerControls.setBorder(BorderFactory.createTitledBorder("Controls"));        

        // Add buttons and dynamic text
        this.start = new JButton("Start");
        this.timerControls.add(this.start);

        this.stop = new JButton("Stop");
        this.timerControls.add(this.stop);

        this.reset = new JButton("Reset");
        this.timerControls.add(this.reset);

        this.bellPlaySound = new JButton("Ding!");
        this.timerControls.add(this.bellPlaySound);

        // Add layout constraints
        GridBagConstraints timerControlsConstraints = new GridBagConstraints();
        // Position row 3, column 0.
        timerControlsConstraints.gridx = 0;
        timerControlsConstraints.gridy = 3;
        timerControlsConstraints.fill = GridBagConstraints.BOTH;
        timerControlsConstraints.insets = new Insets(10, 10, 10, 10);
        timerControlsConstraints.weighty = 0.3;
        
        /*
        ADD SPEAKER CONTROLS
        */

        // Create JPanel for timekeeping controls
        this.speakerControls = new JPanel();
        this.speakerControls.setLayout(new GridLayout());
        this.speakerControls.setBorder(BorderFactory.createTitledBorder("Speakers"));        

        // Add buttons and dynamic text

        this.prevSpeaker = new JButton("Prev Speaker");
        this.speakerControls.add(this.prevSpeaker);

        this.speakerText = new JLabel("P/Hlder");
        this.speakerText.setHorizontalAlignment(JLabel.CENTER);
        this.speakerControls.add(this.speakerText);

        this.nextSpeaker = new JButton("Next Speaker");
        this.speakerControls.add(this.nextSpeaker);

        // Add layout constraints
        GridBagConstraints speakerControlsConstraints = new GridBagConstraints();
        // Position row 0, column 0.
        speakerControlsConstraints.gridx = 0;
        speakerControlsConstraints.gridy = 0;
        speakerControlsConstraints.fill = GridBagConstraints.BOTH;
        speakerControlsConstraints.insets = new Insets(10, 10, 10, 10);
        speakerControlsConstraints.weighty = 0.3;

        // /*
        // ADD BELL CONTROLS
        // */

        // // Create JPanel for bell controls
        // this.bellControls = new JPanel();
        // this.bellControls.setLayout(new GridLayout());
        // this.bellControls.setBorder(BorderFactory.createTitledBorder("Bells"));        

        // // Add buttons and dynamic text
        // this.bellPlaySound = new JButton("Ding!");
        // this.bellControls.add(this.bellPlaySound);

        // this.bellSettings = new JButton("Settings");
        // this.bellControls.add(this.bellSettings);

        // // Add layout constraints
        // GridBagConstraints bellControlsConstraints = new GridBagConstraints();
        // // Position row 2, column 0.
        // bellControlsConstraints.gridx = 0;
        // bellControlsConstraints.gridy = 2;
        // bellControlsConstraints.fill = GridBagConstraints.BOTH;
        // bellControlsConstraints.insets = new Insets(10, 10, 10, 10);

        /*
        ADD TIMER DISPLAY
        */

        // Create JPanel for display
        this.timerDisplay = new JPanel();
        this.timerDisplay.setLayout(new BorderLayout());

        // Add labels
        this.elapsedTime = new JLabel("0:00", JLabel.CENTER);
        this.elapsedTime.setFont(new Font("Segoe UI", Font.PLAIN, 200));
        this.timerDisplay.add(this.elapsedTime, BorderLayout.CENTER);

        // Add Constraints for layout
        GridBagConstraints timerDisplayConstraints = new GridBagConstraints();
        // Position row 1, column 0.
        timerDisplayConstraints.gridx = 0;
        timerDisplayConstraints.gridy = 1; 
        timerDisplayConstraints.fill = GridBagConstraints.BOTH;
        timerDisplayConstraints.gridheight = 2; // spans two rows
        timerDisplayConstraints.weightx = 1.0;
        timerDisplayConstraints.weighty = 1.0;

        // /*
        // ADD BELL/SPEAKER INFO DISPLAY
        // */
        // this.bellText = new JLabel("1 bell at 2 minutes, 2 bells at 4 minutes, cutoff at 4 minutes 30 seconds");
        // this.speakerText = new JLabel("Yeah Speakers");

        // // Add Constraints for layout
        // GridBagConstraints bellTextDisplayConstraints = new GridBagConstraints();
        // // Position row 0, column 1.
        // bellTextDisplayConstraints.gridx = 1;
        // bellTextDisplayConstraints.gridy = 2; 
        // bellTextDisplayConstraints.fill = GridBagConstraints.BOTH;



        // Add sub-panels to parent timer panel
        this.timerPanel.add(this.timerControls, timerControlsConstraints);
        this.timerPanel.add(this.timerDisplay, timerDisplayConstraints);
        this.timerPanel.add(this.speakerControls, speakerControlsConstraints);
        // this.timerPanel.add(this.bellControls, bellControlsConstraints);
        // this.timerPanel.add(this.bellText, bellTextDisplayConstraints);
    }

    public void initGUISettingsElements() {
        this.settingsPanel = new JPanel(new GridBagLayout());
    }

    public JButton getStartButton() {
        return this.start;
    }

    public JButton getStopButton() {
        return this.stop;
    }

    public JButton getResetButton() {
        return this.reset;
    }

    public JButton getDingButton() {
        return this.bellPlaySound;
    }

    public JButton getNextSpeakerButton() {
        return this.nextSpeaker;
    }

    public JButton getPrevSpeakerButton() {
        return this.prevSpeaker;
    }

    public void setElapsedTime(int minutes, int seconds) {
        String display = String.format("%d:%02d", minutes, seconds);
        this.elapsedTime.setText(display);
    }

    public void setTimerOnUpdate(String speaker, Stopwatch stopwatch) {
        // Change speaker heading and update timer to correct one, whether stored or not

    }
}
