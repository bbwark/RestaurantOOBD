package GUI.mainFrame;

import GUI.panels.panelRistorante;
import GUI.panels.panelSala;
import GUI.panels.panelTavolo;

import javax.swing.*;
import java.awt.*;

public class mainFrameContentPane extends JPanel {


    private GUI.panels.panelRistorante panelRistorante;
    private GUI.panels.panelSala panelSala;
    private GUI.panels.panelTavolo panelTavolo;

    public mainFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new CardLayout());


        panelRistorante = new panelRistorante(this);
        panelSala = new panelSala(this);
        panelTavolo = new panelTavolo(this);

        add(panelRistorante, "Panel Ristorante");
        add(panelSala, "Panel Sala");
        add(panelTavolo, "Panel Tavolo");

    }
}
