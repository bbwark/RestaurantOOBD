package GUI.mainFrame;

import GUI.mainFrame.mainPanels.*;

import javax.swing.*;
import java.awt.*;

public class mainFrameContentPane extends JPanel {

    private mainPanelRistorante mainPanelRistorante;
    private mainPanelSala mainPanelSala;
    private mainPanelTavolo mainPanelTavolo;
    private mainPanelCliente mainPanelCliente;
    private GUI.mainFrame.mainPanels.mainPanelCameriere mainPanelCameriere;

    public mainFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        mainPanelRistorante = new mainPanelRistorante(this);
        mainPanelSala = new mainPanelSala(this);
        mainPanelTavolo = new mainPanelTavolo(this);
        mainPanelCliente = new mainPanelCliente(this);
        mainPanelCameriere = new mainPanelCameriere(this);

        add(mainPanelRistorante, "Panel Ristorante");
        add(mainPanelSala, "Panel Sala");
        add(mainPanelTavolo, "Panel Tavolo");
        add(mainPanelCliente, "Panel Cliente");
        add(mainPanelCameriere, "Panel Cameriere");
    }
}
