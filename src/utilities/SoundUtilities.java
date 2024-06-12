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

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File file = new File("PassengerBoop.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip= AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();

    }
}

