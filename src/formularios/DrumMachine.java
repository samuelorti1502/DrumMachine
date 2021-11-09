package formularios;

import clases.Sonido;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class DrumMachine {

    JPanel mainPanel;
    JPanel btnPanel;
    ArrayList<JToggleButton> checkboxList;
    JFrame frame;
    JComboBox comboRitmos;

    final Random r = new Random();

    Sonido sonido = new Sonido();

    private final String[] instNames = {"Bombo", "Hi-Hat Cerrado",
        "Hi-Hat Abierto", "Caja acustica", "Plato crash",
        "Aplauso", "High Tom", "Hi Bango", "Maracas",
        "Flauta", "Low Conga", "Cascabel", "Vibraslap", "Low-Mid Tom",
        "High Agogo", "Open Hi Conga"};

    private final String[] listRimos = {"Ritmo1", "Ritmo2", "Ritmo3"};

    public static void main(String[] args) {
        new DrumMachine().buildGUI();
    }

    public void buildGUI() {
        frame = new JFrame("Caja de Ritmos UMG V 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/drum-machine.png"));

        BorderLayout layout = new BorderLayout();
        JPanel bg = new JPanel(layout);
        bg.setOpaque(true);
        bg.setBackground(Color.decode("#5064F7"));

        checkboxList = new ArrayList<JToggleButton>();

        //Box buttonBox = new Box(BoxLayout.Y_AXIS);
        GridLayout btnGrid = new GridLayout(8, 1);
        btnGrid.setHgap(5);
        btnGrid.setVgap(2);

        btnPanel = new JPanel(btnGrid);

        JButton start = new JButton("Iniciar");
        start.addActionListener(new MyStartListener());
        btnPanel.add(start);

        JButton stop = new JButton("Detener");
        stop.addActionListener(new MyStopListener());
        btnPanel.add(stop);

        JButton upTempo = new JButton("Tempo +");
        upTempo.addActionListener(new MyUpTempoListener());
        btnPanel.add(upTempo);

        JButton downTempo = new JButton("Tempo -");
        downTempo.addActionListener(new MyDownTempoListener());
        btnPanel.add(downTempo);

        comboRitmos = new JComboBox(listRimos);
        comboRitmos.addActionListener(new Ritmos());

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(new Limpiar());
        btnPanel.add(btnLimpiar);

        /*switch (seleccion) {
            case 0:
                comboRitmos.addActionListener(new Ritmos());
                break;
            case 1:
                comboRitmos.addActionListener(new Ritmo2());
                break;
        }*/
        btnPanel.add(comboRitmos);

        JButton ritmo1 = new JButton("Ritmo1");
        ritmo1.addActionListener(new Ritmos());
        //btnPanel.add(ritmo1);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instNames[i])).setForeground(Color.white);
        }

        //bg.add(BorderLayout.EAST, buttonBox);
        bg.add(BorderLayout.EAST, btnPanel);

        bg.add(BorderLayout.WEST, nameBox);

        frame.add(bg);

        GridLayout grid = new GridLayout(16, 16);
        grid.setHgap(2);
        grid.setVgap(2);
        mainPanel = new JPanel(grid);

        //Color cl = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), r.nextInt(256));
        for (int i = 0; i < 256; i++) {
            Color cl = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), r.nextInt(256));
            JToggleButton c = new JToggleButton();
            c.setSelected(false);
            //c.setBackground(cl);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        bg.add(BorderLayout.CENTER, mainPanel);

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

            for (int k = 0; k < 256; k++) {
                JToggleButton jc1 = (JToggleButton) checkboxList.get(k);
                if (jc1.isSelected()) {
                    jc1.setSelected(false);
                }
            }

            int[][] ins = {{0, 8, 10, 16, 18, 20, 22, 24, 26, 28, 30, 52, 60},
            {0, 2, 3, 6, 7, 8, 9, 15, 16, 18, 20, 22, 24, 26, 28, 30, 52, 60, 62},
            {0, 2, 3, 5, 6, 9, 10, 16, 18, 20, 22, 24, 26, 28, 30, 52, 60, 84, 92, 113, 125, 161, 162, 168, 169, 240, 247, 255}};

            int ritmo = 0;

            switch (comboRitmos.getSelectedIndex()) {
                case 0:
                    System.out.println("Hola");
                    ritmo = 0;
                    break;
                case 1:
                    System.out.println("Hola1");
                    ritmo = 1;
                    break;
                case 2:
                    System.out.println("Hola2");
                    ritmo = 2;
                    break;
            }

            try {
                //System.out.println("largo = " + ins.length);
                for (int i = 0; i < ins.length; i++) {
                    for (int j = 0; j < ins[i].length; j++) {
                        //System.out.println("largo = " + ins[i].length);
                        JToggleButton jc = (JToggleButton) checkboxList.get(ins[ritmo][j]);
                        jc.setSelected(false);
                        if (jc.isSelected()) {

                        } else {
                            jc.setOpaque(true);
                            jc.setForeground(Color.green);
                            jc.setSelected(true);

                        }
                    }

                }

            } catch (Exception e) {
            }

        }
    }

    public class Limpiar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent a) {
            System.out.println("Hola");
            for (int i = 0; i < 256; i++) {
                JToggleButton jc = (JToggleButton) checkboxList.get(i);
                if (jc.isSelected()) {
                    jc.setSelected(false);
                }
            }
        }

    }
}
