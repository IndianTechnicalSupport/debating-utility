package com.indiantechnicalsupport.debatingutility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.ArrayList;

import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

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
    private JButton bellPlaySound;

    private JPanel timerDisplay;
    private JLabel elapsedTime;

    private String bellString;
    private JLabel bellText;

    private JPanel speakerControls;
    private JButton prevSpeaker;
    private JLabel speakerText;
    private JButton nextSpeaker;

    // Settings Elements
    private JPanel settingsPanel;

    private JPanel settingsBells;
    private ArrayList<JFormattedTextField> settingsBellsTextFieldArrayList;

    private JPanel settingsSpeakers;
    private ArrayList<JTextField> settingsSpeakersTitleArrayList;
    private ArrayList<JTextField> settingsSpeakersNameArrayList;
    
    private JPanel settingsSummary;
    private JButton generateSummary;
    private JTextArea summaryText;


    public View(int numberSpeakers) {

        // Create window
        super("Debating Utility");

        // Set window close behaviour
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup GUI elements
        this.initGUI(numberSpeakers);

        // Add tabbed pane to main window
        this.add(this.tabPane);

        // Clean up and make GUI visible
        this.pack();
        this.setVisible(true);

    }

    private void initGUI(int numberSpeakers) {

        // Setup tabbed pane
        this.tabPane = new JTabbedPane();
        ImageIcon tabIcon = new ImageIcon();
        // ImageIcon tabIcon = new ImageIcon("./app/src/main/resources/timerTabIcon.png");

        // Setup panel elements
        this.initGUITimerElements();
        this.initGUISettingsElements(numberSpeakers);

        // Add panels to tab
        this.tabPane.addTab("Timekeeping", tabIcon, this.timerPanel, "Stopwatch and bell functions.");
        this.tabPane.addTab("Settings", tabIcon, this.settingsPanel,
                "Adjust settings, including the speaker and bell configurations.");

    }

    private void initGUITimerElements() {

        // Create overall JPanel for organising all components
        this.timerPanel = new JPanel(new GridBagLayout());
        this.timerPanel.setName("Timekeeping");

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
        // Position row 4, column 0.
        timerControlsConstraints.gridx = 0;
        timerControlsConstraints.gridy = 4;
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

        /*
        ADD BELL/SPEAKER INFO DISPLAY
        */
        
        // Add labels
        this.bellText = new JLabel(this.getBellString());
        this.bellText.setHorizontalAlignment(JLabel.CENTER);
        this.setBellText("1 bell at 0:05, 2 bells at 0:10, 3 bells at 0:15"); // Default timings

        // Add Constraints for layout
        GridBagConstraints bellTextDisplayConstraints = new GridBagConstraints();
        // Position row 3, column 0.
        bellTextDisplayConstraints.gridx = 0;
        bellTextDisplayConstraints.gridy = 3; 
        bellTextDisplayConstraints.fill = GridBagConstraints.BOTH;



        // Add sub-panels to parent timer panel
        this.timerPanel.add(this.timerControls, timerControlsConstraints);
        this.timerPanel.add(this.timerDisplay, timerDisplayConstraints);
        this.timerPanel.add(this.speakerControls, speakerControlsConstraints);
        this.timerPanel.add(this.bellText, bellTextDisplayConstraints);
    }

    public void initGUISettingsElements(int speakerNumber) {
        // Create overall JPanel for organising all components
        this.settingsPanel = new JPanel(new GridBagLayout());
        this.settingsPanel.setName("Settings");

        /*
        ADD EDITABLE BELL TIME FIELDS
        */

        // Create JPanel for timekeeping controls
        this.settingsBells = new JPanel();
        this.settingsBells.setLayout(new GridLayout());
        this.settingsBells.setBorder(BorderFactory.createTitledBorder("Bell Times"));        

        // Add buttons and dynamic text

        int bellNumber = 3;

        this.settingsBellsTextFieldArrayList = new ArrayList<JFormattedTextField>();

        for (int i = 0; i < bellNumber; i ++) {
            this.settingsBellsTextFieldArrayList.add(new JFormattedTextField(createTimeFormatter("#:##")));
            this.settingsBellsTextFieldArrayList.get(i).setFont(new Font("Segoe UI", Font.PLAIN, 45));
            this.settingsBells.add(this.settingsBellsTextFieldArrayList.get(i));
        }

        // Add layout constraints
        GridBagConstraints settingsBellsConstraints = new GridBagConstraints();
        // Position row 0, column 0.
        settingsBellsConstraints.gridx = 0;
        settingsBellsConstraints.gridy = 0;
        settingsBellsConstraints.fill = GridBagConstraints.BOTH;
        settingsBellsConstraints.insets = new Insets(10, 10, 10, 10);
        settingsBellsConstraints.weightx = 1.0;
        settingsBellsConstraints.weighty = 0.3;

        /* 
        ADD SPEAKER INFORMATION PANEL 
        */

        // Create JPanel for speaker fields
        this.settingsSpeakers = new JPanel();
        this.settingsSpeakers.setLayout(new GridLayout((speakerNumber + 1), 2));
        this.settingsSpeakers.setBorder(BorderFactory.createTitledBorder("Speaker Details"));        

        // Add static text at top

        this.settingsSpeakers.add(new JLabel("Speaker Title"));
        this.settingsSpeakers.add(new JLabel("Speaker Name"));
        
        // Add buttons and dynamic text

        this.settingsSpeakersTitleArrayList = new ArrayList<JTextField>();
        this.settingsSpeakersNameArrayList = new ArrayList<JTextField>();

        for (int i = 0; i < speakerNumber; i ++) {
            this.settingsSpeakersTitleArrayList.add(new JTextField());
            this.settingsSpeakersNameArrayList.add(new JTextField());
            this.settingsSpeakers.add(this.settingsSpeakersTitleArrayList.get(i));
            this.settingsSpeakers.add(this.settingsSpeakersNameArrayList.get(i));
        }

        this.setDefaultSpeakerTitles();

        // Add layout constraints
        GridBagConstraints settingsSpeakersConstraints = new GridBagConstraints();
        // Position row 1, column 0.
        settingsSpeakersConstraints.gridx = 0;
        settingsSpeakersConstraints.gridy = 1;
        settingsSpeakersConstraints.fill = GridBagConstraints.BOTH;
        settingsSpeakersConstraints.insets = new Insets(10, 10, 10, 10);
        settingsSpeakersConstraints.weightx = 1.0;
        settingsSpeakersConstraints.weighty = 0.5;

        /*
         * ADD SUMMARY GENERATION PANEL
         */

        // Create JPanel for display
        this.settingsSummary = new JPanel();
        this.settingsSummary.setLayout(new GridLayout());
        this.settingsSummary.setBorder(BorderFactory.createTitledBorder("Summary"));

        // Add buttons
        this.generateSummary = new JButton("Generate Text Summary");
        this.settingsSummary.add(this.generateSummary);
        
        // Add text area
        this.summaryText = new JTextArea();
        this.summaryText.setEditable(false);
        this.settingsSummary.add(this.summaryText);

        // Add Constraints for layout
        GridBagConstraints settingsSummaryConstraints = new GridBagConstraints();
        // Position row 0, column 1.
        settingsSummaryConstraints.gridx = 1;
        settingsSummaryConstraints.gridy = 0; 
        settingsSummaryConstraints.fill = GridBagConstraints.BOTH;
        settingsSummaryConstraints.gridheight = 2; // spans two rows
        settingsSummaryConstraints.weightx = 0.4;
        settingsSummaryConstraints.weighty = 0.4;

        // Add sub-panels to parent settings panel
        this.settingsPanel.add(this.settingsBells, settingsBellsConstraints);
        this.settingsPanel.add(this.settingsSpeakers, settingsSpeakersConstraints);
        this.settingsPanel.add(this.settingsSummary, settingsSummaryConstraints);
        
    }

    public void redrawSettingsBellTimeElements(ArrayList<Integer> bellTimes) {
        this.settingsBellsTextFieldArrayList = new ArrayList<JFormattedTextField>();

        this.settingsBells.removeAll(); // Clear JPanel, redraw components

        for (int i = 0; i < bellTimes.size(); i ++) {
            JFormattedTextField field = new JFormattedTextField(createTimeFormatter("#:##"));

            Integer currentBellTime = bellTimes.get(i);
            currentBellTime = currentBellTime / 1000;
            
            Integer currentBellMinutes = currentBellTime / 60;
            Integer currentBellSeconds = currentBellTime - (currentBellMinutes * 60);

            String formattedTime = "";

            if (currentBellSeconds < 10) { // Less than 10 seconds, need auxiliary 0
                formattedTime = currentBellMinutes + ":0" + currentBellSeconds;
            } else {
                formattedTime = currentBellMinutes + ":" + currentBellSeconds;
            }

            field.setText(formattedTime);
            field.setValue(formattedTime);

            this.settingsBells.add(field);
            
            this.settingsBellsTextFieldArrayList.add(field);
        }
    }

    public String getFormattedTime(Integer bellTimeMillis) {
        int bellTimeSeconds = bellTimeMillis / 1000;
            
        Integer currentBellMinutes = bellTimeSeconds / 60;
        Integer currentBellSeconds = bellTimeSeconds - (currentBellMinutes * 60);

        String formattedTime = "";

        if (currentBellSeconds < 10) { // Less than 10 seconds, need auxiliary 0
            formattedTime = currentBellMinutes + ":0" + currentBellSeconds;
        } else {
            formattedTime = currentBellMinutes + ":" + currentBellSeconds;
        }

        return formattedTime;
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

    public JLabel getBellText() {
        return this.bellText;
    }

    public JLabel getSpeakerText() {
        return this.speakerText;
    }

    public JPanel getTimerDisplay() {
        return this.timerDisplay;
    }

    public JTabbedPane getTabbedPane() {
        return this.tabPane;
    }

    public ArrayList<Integer> getSettingsBellsTimes() {
        ArrayList<Integer> bellTimes = new ArrayList<Integer>();

        for (int i = 0; i < this.settingsBellsTextFieldArrayList.size(); i ++) {
            String input = (String) this.settingsBellsTextFieldArrayList.get(i).getValue();

            String[] splitInput = input.split(":");

            Integer minutes = Integer.parseInt(splitInput[0]);
            Integer seconds = Integer.parseInt(splitInput[1]);

            Integer calculatedBellTime = (minutes * 60 + seconds) * 1000; // Calculated in milliseconds

            bellTimes.add(calculatedBellTime);
        }
        
        return bellTimes;
    }

    public String getBellString() {
        return this.bellString;
    }

    public ArrayList<String> getSpeakerTitles() {
        ArrayList<String> speakerTitles = new ArrayList<String>();

        for (int i = 0; i < this.settingsSpeakersTitleArrayList.size(); i ++) {
            speakerTitles.add(this.settingsSpeakersTitleArrayList.get(i).getText());
        }

        return speakerTitles;
    }

    public ArrayList<String> getSpeakerNames() {
        ArrayList<String> speakerNames = new ArrayList<String>();

        for (int i = 0; i < this.settingsSpeakersNameArrayList.size(); i ++) {
            speakerNames.add(this.settingsSpeakersNameArrayList.get(i).getText());
        }

        return speakerNames;
    }

    public void setBellText(String bellString) {
        this.bellText.setText(bellString);
    }

    public void setSpeakerText(String speakerString) {
        this.speakerText.setText(speakerString);
    }

    public void setElapsedTime(int minutes, int seconds) {
        String display = String.format("%d:%02d", minutes, seconds);
        this.elapsedTime.setText(display);
    }

    public void setStopwatchBackground(Color color) {
        this.timerDisplay.setBackground(color);
    }

    public void setSpeakerTitles(String title, int index) {
        this.settingsSpeakersTitleArrayList.get(index).setText(title);
    }

    public void setDefaultSpeakerTitles() {
        this.setSpeakerTitles("1st Aff", 0);
        this.setSpeakerTitles("1st Neg", 1);
        this.setSpeakerTitles("2nd Aff", 2);
        this.setSpeakerTitles("2nd Neg", 3);
        this.setSpeakerTitles("3rd Aff", 4);
        this.setSpeakerTitles("3rd Neg", 5);
        this.setSpeakerTitles("4th Aff", 6);
        this.setSpeakerTitles("4th Neg", 7);
    }

    public void updateBellString(ArrayList<Integer> bellIntegerList) {
        this.bellString = "";

        for (int i = 0; i < bellIntegerList.size(); i ++) {
            Integer milliTime = bellIntegerList.get(i);
            System.out.println(milliTime);

            Integer minutes = (milliTime / 60000);
            System.out.println(minutes);
            Integer seconds = (milliTime % 60000) / 1000; 
            System.out.println(seconds);

            String bellStringFragment = "";

            if (i == 0) { // First pass, 1 bell
                bellStringFragment += "1 bell at ";
            } else {
                bellStringFragment += (i + 1) + " bells at ";
            }

            bellStringFragment += String.format("%d:%02d", minutes, seconds);

            if (i + 1 != bellIntegerList.size()) { // Not last pass, add comma to string
                bellStringFragment += ", ";
            }

            this.bellString += bellStringFragment;
        }
    }

    protected MaskFormatter createTimeFormatter(String string) {

        MaskFormatter formatter = null;

        try {
            formatter = new MaskFormatter(string);
        } catch (ParseException e) {
            System.err.println("formatter is bad: " + e.getMessage());
            System.exit(-1);
        }

        return formatter;
    }
}
