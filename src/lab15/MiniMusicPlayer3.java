package lab15;

import javax.sound.midi.*;
import javax.swing.*;

public class MiniMusicPlayer3 implements ControllerEventListener {
    MyDrawPanel myPanel;

    public static void main(String[] args) {
        MiniMusicPlayer3 mini = new MiniMusicPlayer3();
        mini.go();
    }

    public void go() {
        JFrame frame = new JFrame("Music Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создаем объект панели для рисования
        myPanel = new MyDrawPanel();
        frame.setContentPane(myPanel);
        frame.setSize(500, 500);
        frame.setVisible(true);

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            int[] eventsWant = {127};
            sequencer.addControllerEventListener(this, eventsWant);

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            for (int i = 5; i < 60; i+= 4) {
                track.add(makeEvent(144, 1, i, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, i, 100, i + 2));
            }

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();

        } catch (Exception ex) {ex.printStackTrace();}
    }

    @Override
    public void controlChange(ShortMessage event) {
        myPanel.controlChange(event);
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception ex) {}
        return event;
    }
}
