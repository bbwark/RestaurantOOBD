package GUI.editFrame;

import GUI.editFrame.editPanels.*;

import javax.swing.*;
import java.awt.*;

public class editFrameContentPane extends JPanel {

    private editPanelRistorante editPanelRistorante;
    private editPanelSala editPanelSala;
    private editPanelTavolo editPanelTavolo;
    private editPanelPrenotazioni editPanelPrenotazioni;
    private editPanelCliente editPanelCliente;
    private editPanelCameriere editPanelCameriere;

    public editFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        editPanelRistorante = new editPanelRistorante(this);
        editPanelSala = new editPanelSala(this);
        editPanelTavolo = new editPanelTavolo(this);
        editPanelPrenotazioni = new editPanelPrenotazioni(this);
        editPanelCliente = new editPanelCliente(this);
        editPanelCameriere = new editPanelCameriere(this);

        add(editPanelRistorante, "Panel Ristorante");
        add(editPanelSala, "Panel Sala");
        add(editPanelTavolo, "Panel Tavolo");
        add(editPanelPrenotazioni, "Panel Prenotazioni");
        add(editPanelCliente, "Panel Cliente");
        add(editPanelCameriere, "Panel Cameriere");
    }
}