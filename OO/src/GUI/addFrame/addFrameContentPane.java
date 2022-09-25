package GUI.addFrame;

import GUI.addFrame.addPanels.*;

import javax.swing.*;
import java.awt.*;

public class addFrameContentPane extends JPanel {

    private addPanelRistorante addPanelRistorante;
    private addPanelSala addPanelSala;
    private addPanelTavolo addPanelTavolo;
    private addPanelPrenotazione addPanelPrenotazione;
    private addPanelCliente addPanelCliente;
    private addPanelCameriere addPanelCameriere;
    private addPanelCameriereToServizio addPanelCameriereToServizio;
    private addPanelClienteToPrenotazione addPanelClienteToPrenotazione;
    private addPanelServizioToCameriere addPanelServizioToCameriere;
    private addPanelPrenotazioneToCliente addPanelPrenotazioneToCliente;
    private addPanelTavoloAdiacenteToTavolo addPanelTavoloAdiacenteToTavolo;

    public addFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        addPanelRistorante = new addPanelRistorante();
        addPanelSala = new addPanelSala();
        addPanelTavolo = new addPanelTavolo();
        addPanelPrenotazione = new addPanelPrenotazione();
        addPanelCliente = new addPanelCliente();
        addPanelCameriere = new addPanelCameriere();
        addPanelCameriereToServizio = new addPanelCameriereToServizio();
        addPanelClienteToPrenotazione = new addPanelClienteToPrenotazione();
        addPanelServizioToCameriere = new addPanelServizioToCameriere();
        addPanelPrenotazioneToCliente = new addPanelPrenotazioneToCliente();
        addPanelTavoloAdiacenteToTavolo = new addPanelTavoloAdiacenteToTavolo();


        add(addPanelRistorante, "Panel Ristorante");
        add(addPanelSala, "Panel Sala");
        add(addPanelTavolo, "Panel Tavolo");
        add(addPanelPrenotazione, "Panel Prenotazione");
        add(addPanelCliente, "Panel Cliente");
        add(addPanelCameriere, "Panel Cameriere");
        add(addPanelCameriereToServizio, "Panel Cameriere Servizio");
        add(addPanelClienteToPrenotazione, "Panel Cliente Prenotazione");
        add(addPanelServizioToCameriere, "Panel Servizio Cameriere");
        add(addPanelPrenotazioneToCliente, "Panel Prenotazione Cliente");
        add(addPanelTavoloAdiacenteToTavolo, "Panel Tavolo Adiacente Tavolo");

    }

    public GUI.addFrame.addPanels.addPanelRistorante getAddPanelRistorante() {
        return addPanelRistorante;
    }

    public GUI.addFrame.addPanels.addPanelSala getAddPanelSala() {
        return addPanelSala;
    }

    public GUI.addFrame.addPanels.addPanelTavolo getAddPanelTavolo() {
        return addPanelTavolo;
    }

    public addPanelPrenotazione getAddPanelPrenotazione() {
        return addPanelPrenotazione;
    }

    public GUI.addFrame.addPanels.addPanelCliente getAddPanelCliente() {
        return addPanelCliente;
    }

    public GUI.addFrame.addPanels.addPanelCameriere getAddPanelCameriere() {
        return addPanelCameriere;
    }

    public GUI.addFrame.addPanels.addPanelCameriereToServizio getAddPanelCameriereToServizio() {
        return addPanelCameriereToServizio;
    }

    public GUI.addFrame.addPanels.addPanelClienteToPrenotazione getAddPanelClienteToPrenotazione() {
        return addPanelClienteToPrenotazione;
    }

    public GUI.addFrame.addPanels.addPanelServizioToCameriere getAddPanelServizioToCameriere() {
        return addPanelServizioToCameriere;
    }

    public GUI.addFrame.addPanels.addPanelPrenotazioneToCliente getAddPanelPrenotazioneToCliente() {
        return addPanelPrenotazioneToCliente;
    }
}