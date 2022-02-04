package GUI.mainFrame;

import GUI.mainFrame.mainPanels.mainPanelRistorante;
import GUI.mainFrame.mainPanels.mainPanelSala;
import GUI.mainFrame.mainPanels.mainPanelTavolo;

import javax.swing.*;
import java.awt.*;

public class mainFrameContentPane extends JPanel {

    private mainPanelRistorante mainPanelRistorante;
    private mainPanelSala mainPanelSala;
    private mainPanelTavolo mainPanelTavolo;

    public mainFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new CardLayout());


        mainPanelRistorante = new mainPanelRistorante(this);
        mainPanelSala = new mainPanelSala(this);
        mainPanelTavolo = new mainPanelTavolo(this);

        add(mainPanelRistorante, "Panel Ristorante");
        add(mainPanelSala, "Panel Sala");
        add(mainPanelTavolo, "Panel Tavolo");
    }
}
