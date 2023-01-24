package com.indiantechnicalsupport.debatingutility;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BellRinger implements Runnable {

    private File bellSoundFile;

    private int numberRings;

    public BellRinger(int numberRings) {

        this.numberRings = numberRings;

        this.bellSoundFile = new File("C:\\Users\\tslcg\\OneDrive\\Documents\\Liam\\Development\\Debating Utility\\app\\src\\main\\resources\\TimekeepingBell.wav"); 
    }

    public void run() {
        for (int i = 0; i < this.numberRings; i ++) {
            try {
                this.soundBellOnce();
                Thread.sleep(800);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void soundBellOnce() {
        try {
            // File bellSoundFile = new File("C:\\Users\\tslcg\\OneDrive\\Documents\\Liam\\Development\\Debating Utility\\app\\src\\main\\resources\\TimekeepingBell.wav"); 
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.bellSoundFile); 
            Clip activeClip = AudioSystem.getClip();
            activeClip.open(audioInputStream);
            activeClip.start();
            System.out.println("Bell rung");
        } catch (IOException e) {
            
        } catch (UnsupportedAudioFileException e) {

        } catch (LineUnavailableException e) {

        }
    }
}
