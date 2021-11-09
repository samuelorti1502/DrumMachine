package clases;

import java.util.ArrayList;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JToggleButton;

public class Sonido{

    public Sequencer seqR;
    Sequence seq;
    Track track;
    
    
    int[] instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

    public void setUpMidi() {
        try {
            seqR = MidiSystem.getSequencer();
            seqR.open();
            seq = new Sequence(Sequence.PPQ, 4);
            track = seq.createTrack();
            seqR.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void buildTrackAndStart(ArrayList<JToggleButton> checkboxList) {
        int[] trackList = null;
        seq.deleteTrack(track);
        track = seq.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new int[16];
            int key = instruments[i];

            for (int j = 0; j < 16; j++) {
                JToggleButton jc = (JToggleButton) checkboxList.get(j + (16 * i));
                if (jc.isSelected()) {
                    trackList[j] = key;
                    //jc.setText("Hola");
                } else {
                    trackList[j] = 0;
                }
            }

            makeTracks(trackList);
            //track.add(makeEvent(176, 0, 127, 0, 16));

        }
        track.add(makeEvent(192, 0, 1, 0, 15));
        try {
            seqR.setSequence(seq);
            seqR.setLoopCount(seqR.LOOP_CONTINUOUSLY);
            seqR.start();
            seqR.setTempoInBPM(120);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void makeTracks(int[] list){
        for(int i=0;i<16;i++){
            int key=list[i];
            
            if(key!=0){
                track.add(makeEvent(144,0,key,100,i));
                //track.add(makeEvent(144,0,key,100,i+1));
            }
        }
    }
    
    public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
        MidiEvent event=null;
        try{
            ShortMessage a=new ShortMessage();
            a.setMessage(comd,9,one,two);
         
            event=new MidiEvent(a,tick);
        }
        catch(Exception e){
        e.printStackTrace();}
        return event;
    
    }
}
