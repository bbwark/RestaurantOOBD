package GUI.mainFrame;

import GUI.mainFrame.mainPanels.*;

import javax.swing.*;
import java.awt.*;

public class mainFrameContentPane extends JPanel {

    private mainPanelRistorante mainPanelRistorante;
    private mainPanelSala mainPanelSala;
    private mainPanelTavolo mainPanelTavolo;
    private mainPanelCliente mainPanelCliente;
    private mainPanelPrenotazioni mainPanelPrenotazioni;
    private mainPanelSelezionaTavolo mainPanelSelezionaTavolo;
    public mainFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        mainPanelRistorante = new mainPanelRistorante();
        mainPanelSala = new mainPanelSala();
        mainPanelTavolo = new mainPanelTavolo();
        mainPanelCliente = new mainPanelCliente();
        mainPanelPrenotazioni = new mainPanelPrenotazioni();
        mainPanelSelezionaTavolo = new mainPanelSelezionaTavolo();

        add(mainPanelRistorante, "Panel Ristorante");
        add(mainPanelSala, "Panel Sala");
        add(mainPanelTavolo, "Panel Tavolo");
        add(mainPanelCliente, "Panel Cliente");
        add(mainPanelPrenotazioni, "Panel Prenotazioni");
        add(mainPanelSelezionaTavolo, "Panel Seleziona Tavolo");
    }

    public GUI.mainFrame.mainPanels.mainPanelRistorante getMainPanelRistorante() {
        return mainPanelRistorante;
    }

    public GUI.mainFrame.mainPanels.mainPanelSala getMainPanelSala() {
        return mainPanelSala;
    }

    public GUI.mainFrame.mainPanels.mainPanelTavolo getMainPanelTavolo() {
        return mainPanelTavolo;
    }

    public GUI.mainFrame.mainPanels.mainPanelCliente getMainPanelCliente() {
        return mainPanelCliente;
    }
}
