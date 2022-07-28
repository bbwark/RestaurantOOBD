package GUI.mainFrame;

import GUI.mainFrame.mainPanels.mainPanelCliente;
import GUI.mainFrame.mainPanels.mainPanelRistorante;
import GUI.mainFrame.mainPanels.mainPanelSala;
import GUI.mainFrame.mainPanels.mainPanelTavolo;

import javax.swing.*;
import java.awt.*;

public class mainFrameContentPane extends JPanel {

    private mainPanelRistorante mainPanelRistorante;
    private mainPanelSala mainPanelSala;
    private mainPanelTavolo mainPanelTavolo;
    private mainPanelCliente mainPanelCliente;

    public mainFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        mainPanelRistorante = new mainPanelRistorante(this);
        mainPanelSala = new mainPanelSala(this);
        mainPanelTavolo = new mainPanelTavolo(this);
        mainPanelCliente = new mainPanelCliente(this);

        add(mainPanelRistorante, "Panel Ristorante");
        add(mainPanelSala, "Panel Sala");
        add(mainPanelTavolo, "Panel Tavolo");
        add(mainPanelCliente, "Panel Cliente");
    }
}
