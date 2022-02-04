package GUI;

import GUI.frameRistorante.mainPanelRistorante;
import GUI.frameSala.mainPanelSala;
import GUI.frameTavolo.mainPanelTavolo;

import javax.swing.*;
import java.awt.*;

public class contentPane extends JPanel {


    private GUI.frameRistorante.mainPanelRistorante mainPanelRistorante;
    private GUI.frameSala.mainPanelSala mainPanelSala;
    private GUI.frameTavolo.mainPanelTavolo mainPanelTavolo;

    public contentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new CardLayout());


        mainPanelRistorante = new mainPanelRistorante(this);
        mainPanelSala = new mainPanelSala(this);
        mainPanelTavolo = new mainPanelTavolo(this);

        add(mainPanelRistorante, "Panel 1");
        add(mainPanelSala, "Panel 2");
        add(mainPanelTavolo, "Panel 3");

    }
}
