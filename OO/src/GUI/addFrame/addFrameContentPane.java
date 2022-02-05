package GUI.addFrame;

import GUI.addFrame.addPanels.*;

import javax.swing.*;
import java.awt.*;

public class addFrameContentPane extends JPanel {

    private addPanelRistorante addPanelRistorante;
    private addPanelSala addPanelSala;
    private addPanelTavolo addPanelTavolo;
    private addPanelPrenotazioni addPanelPrenotazioni;
    private addPanelCliente addPanelCliente;
    private addPanelCameriere addPanelCameriere;
    private addPanelPrenotazioniCameriere addPanelPrenotazioniCameriere;

    public addFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        addPanelRistorante = new addPanelRistorante(this);
        addPanelSala = new addPanelSala(this);
        addPanelTavolo = new addPanelTavolo(this);
        addPanelPrenotazioni = new addPanelPrenotazioni(this);
        addPanelCliente = new addPanelCliente(this);
        addPanelCameriere = new addPanelCameriere(this);
        addPanelPrenotazioniCameriere = new addPanelPrenotazioniCameriere(this);

        add(addPanelRistorante, "Panel Ristorante");
        add(addPanelSala, "Panel Sala");
        add(addPanelTavolo, "Panel Tavolo");
        add(addPanelPrenotazioni, "Panel Prenotazioni");
        add(addPanelCliente, "Panel Cliente");
        add(addPanelCameriere, "Panel Cameriere");
        add(addPanelPrenotazioniCameriere, "Panel Prenotazioni Cameriere");
    }
}