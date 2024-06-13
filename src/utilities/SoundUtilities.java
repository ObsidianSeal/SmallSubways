package utilities;
import java.applet.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;


public class SoundUtilities {
//import and play the sounds.
    //main stores the variables.
    //make sure that there are circumstances for the sounds to be played.
    //make sure to add all sound effects on trello
static Clip clip;
    static URL[] soundURL = new URL[30];

    public SoundUtilities() {

        soundURL[0] = getClass().getResource("/sound/PassengerBoop.wav");
        soundURL[1] = getClass().getResource("/sound/Boop.wav");
        soundURL[2] = getClass().getResource("/sound/PassengerBoop.wav");
        soundURL[3] = getClass().getResource("/sound/PassengerBoop.wav");
        soundURL[4] = getClass().getResource("/sound/PassengerBoop.wav");


    }

    public static void setFile(int i){
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch(Exception e){

        }

    }

    public static void play(){

        clip.start();
    }

    public static void loop(){

        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    public static void stop(){
        clip.stop();
    }
}
