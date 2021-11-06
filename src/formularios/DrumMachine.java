package formularios;

import clases.Sonido;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class DrumMachine {

    JPanel mainPanel;
    ArrayList<JToggleButton> checkboxList;
    JFrame frame;

    Sonido sonido = new Sonido();

    private final String[] instNames = {"Campanas tubulares", "Closed Hi-Hat",
        "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
        "Hand Clap", "High Tom", "Hi Bango", "Maracas",
        "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-Mid Tom",
        "High Agogo", "Open Hi Conga"};

    public static void main(String[] args) {
        new DrumMachine().buildGUI();
    }

    public void buildGUI() {
        frame = new JFrame("Caja de Ritmos UMG V 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("E://beatbox.png");

        BorderLayout layout = new BorderLayout();
        JPanel bg = new JPanel(layout);

        checkboxList = new ArrayList<JToggleButton>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("Iniciar");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("Detener");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo +");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo -");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton ritmo1 = new JButton("Ritmo1");
        ritmo1.addActionListener(new Ritmos());
        buttonBox.add(ritmo1);
        
        

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenu = new JMenuItem("New");
        JMenuItem saveMenu = new JMenuItem("Save");
        JMenuItem openMenu = new JMenuItem("Save");
        fileMenu.add(newMenu);
        fileMenu.add(openMenu);
        fileMenu.add(saveMenu);
        menu.add(fileMenu);
        frame.add(BorderLayout.NORTH, menu);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instNames[i]));
        }

        bg.add(BorderLayout.EAST, buttonBox);
        bg.add(BorderLayout.WEST, nameBox);

        frame.add(bg);

        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setVgap(2);
        mainPanel = new JPanel(grid);
        bg.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++) {
            JToggleButton c = new JToggleButton();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        sonido.setUpMidi();

        frame.setBounds(50, 50, 300, 300);
        frame.pack();
        frame.setVisible(true);
        frame.setIconImage(icon.getImage());

    }

    public class MyStartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            sonido.buildTrackAndStart(checkboxList);
        }
    }

    public class MyStopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            sonido.seqR.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sonido.seqR.getTempoFactor();
            sonido.seqR.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    public class MyDownTempoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sonido.seqR.getTempoFactor();
            sonido.seqR.setTempoFactor((float) (tempoFactor * .97));
        }
    }

    public class Ritmos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            int[] ins={0,8,10,16,18,20,22,24,26,28,30,52,60};
            for (int i = 0; i < 16; i++) {

                for (int j = 0; j < 16; j++) {
                    JToggleButton jc = (JToggleButton) checkboxList.get(ins[i]);
                    if (jc.isSelected()) {

                    } else {
                        jc.setSelected(true);
                    }
                }
            }
        }
    }
}
