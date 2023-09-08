package midik.musica;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class CanalThread extends Thread {

    int[] notes;
    long[] durations;

    @Override
    public void run() {
        try {
            playNotesWithDurations(notes, durations);
        } catch (MidiUnavailableException | InterruptedException ex) {
            Logger.getLogger(Canal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void playNotesWithDurations(int[] notes, long[] durations) throws MidiUnavailableException, InterruptedException {
        try (Synthesizer synth = MidiSystem.getSynthesizer()) {
            synth.open();

            MidiChannel channel = synth.getChannels()[0];

            for (int i = 0; i < notes.length; i++) {
                int note = notes[i];
                long duration = durations[i];

                channel.noteOn(note, 100);
                Thread.sleep(duration);
                channel.noteOff(note);
            }
        }
    }

    public void agregarMusica(int[] notes, long[] durations) {
        this.notes = notes;
        this.durations = durations;
    }

}
