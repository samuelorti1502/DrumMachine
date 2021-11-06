import javax.sound.midi.*;
import javax.swing.JOptionPane;
public class prueba {
    public static void sonarCampana(Track pista, int nota, int pos, int vol) throws InvalidMidiDataException{
            ShortMessage on = new ShortMessage( );
            on.setMessage(ShortMessage.NOTE_ON,  0, nota, vol);
            pista.add(new MidiEvent(on, pos));
        
            ShortMessage off = new ShortMessage( );
            off.setMessage(ShortMessage.NOTE_OFF, 0, nota, vol);
            pista.add(new MidiEvent(off, pos));     
    }
    
    public static void main(String arg[]){
        try{
            //setUpMidi
            Sequencer sequencer = MidiSystem.getSequencer( );
            sequencer.open( );
            Sequence sequence = new Sequence(Sequence.PPQ, 16);
            Track pista = sequence.createTrack();
            sequencer.setTempoInBPM(120);
            //setUpMidi
            
            sequencer.setSequence(sequence);
            
            ShortMessage ins = new ShortMessage( );
            ins.setMessage(ShortMessage.PROGRAM_CHANGE,  0, 47, 0);
            
            JOptionPane.showMessageDialog(null, ins.getChannel());
            
            pista.add(new MidiEvent(ins, 0));
            for (int n=0;n< 64;n++){
                sonarCampana(pista, 67, n*4+(int)(Math.random()*3-1), 64);
            }
            int nDes=0;
            nDes=64*4+100;
            for (int n=0;n< 4;n++){ //Los cuartos
                    sonarCampana(pista, 71, nDes+n*128,32);
                    sonarCampana(pista, 67, nDes+n*128+40,64);
            }
        nDes+=4*128+60; //Campanadas
        for (int n=0;n< 12;n++){ //Los cuartos
                    sonarCampana(pista, 69, nDes+n*110,64);
            }
        nDes+=12*110+20;
        //Aplausos
        ins = new ShortMessage( );
            ins.setMessage(ShortMessage.PROGRAM_CHANGE,  0, 126, 0);
        pista.add(new MidiEvent(ins, nDes));           
        ShortMessage on; 
        ShortMessage off;
        for (int n=50;n< 70;n+=2){
                    on = new ShortMessage( );
                    on.setMessage(ShortMessage.NOTE_ON,  0, n, 40);
                    pista.add(new MidiEvent(on, nDes));
                    off = new ShortMessage( );
                    off.setMessage(ShortMessage.NOTE_OFF, 0, n, 40);
                    pista.add(new MidiEvent(off, nDes+400));                
        }
            sequencer.addMetaEventListener(new MetaEventListener( ) {
            public void meta(MetaMessage m) {
                    if (m.getType( ) == 47) {
                        try{Thread.sleep(2000);}catch(Exception e){}
                        System.exit(0);
                    }
                }
            });
            sequencer.start( );
      }catch(Exception e){
        e.printStackTrace();
      }
     }
}