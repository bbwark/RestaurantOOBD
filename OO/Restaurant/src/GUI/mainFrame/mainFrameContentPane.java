package GUI.mainFrame;

import GUI.mainFrame.mainPanels.*;

import javax.swing.*;
import java.awt.*;

public class mainFrameContentPane extends JPanel {

    private mainPanelRistorante mainPanelRistorante;
    private mainPanelSala mainPanelSala;
    private mainPanelTavolo mainPanelTavolo;
    private mainPanelClienti mainPanelClienti;
    private mainPanelPrenotazioni mainPanelPrenotazioni;
    private mainPanelSelezionaTavolo mainPanelSelezionaTavolo;
    private mainPanelCamerieri mainPanelCamerieri;
    public mainFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        mainPanelRistorante = new mainPanelRistorante();
        mainPanelSala = new mainPanelSala();
        mainPanelTavolo = new mainPanelTavolo();
        mainPanelClienti = new mainPanelClienti();
        mainPanelPrenotazioni = new mainPanelPrenotazioni();
        mainPanelSelezionaTavolo = new mainPanelSelezionaTavolo();
        mainPanelCamerieri = new mainPanelCamerieri();

        add(mainPanelRistorante, "Panel Ristorante");
        add(mainPanelSala, "Panel Sala");
        add(mainPanelTavolo, "Panel Tavolo");
        add(mainPanelClienti, "Panel Cliente");
        add(mainPanelPrenotazioni, "Panel Prenotazioni");
        add(mainPanelSelezionaTavolo, "Panel Seleziona Tavolo");
        add(mainPanelCamerieri, "Panel Camerieri");
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

    public mainPanelClienti getMainPanelClienti() {
        return mainPanelClienti;
    }

    public GUI.mainFrame.mainPanels.mainPanelPrenotazioni getMainPanelPrenotazioni() {
        return mainPanelPrenotazioni;
    }

    public GUI.mainFrame.mainPanels.mainPanelSelezionaTavolo getMainPanelSelezionaTavolo() {
        return mainPanelSelezionaTavolo;
    }

    public GUI.mainFrame.mainPanels.mainPanelCamerieri getMainPanelCamerieri() {
        return mainPanelCamerieri;
    }
}
