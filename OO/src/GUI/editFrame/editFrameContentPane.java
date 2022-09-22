package GUI.editFrame;

import GUI.editFrame.editPanels.*;

import javax.swing.*;
import java.awt.*;

public class editFrameContentPane extends JPanel {

    private editPanelRistorante editPanelRistorante;
    private editPanelSala editPanelSala;
    private editPanelTavolo editPanelTavolo;
    private editPanelPrenotazione editPanelPrenotazione;
    private editPanelCliente editPanelCliente;
    private editPanelCameriere editPanelCameriere;

    public editFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        editPanelRistorante = new editPanelRistorante();
        editPanelSala = new editPanelSala();
        editPanelTavolo = new editPanelTavolo();
        editPanelPrenotazione = new editPanelPrenotazione();
        editPanelCliente = new editPanelCliente();
        editPanelCameriere = new editPanelCameriere();

        add(editPanelRistorante, "Panel Ristorante");
        add(editPanelSala, "Panel Sala");
        add(editPanelTavolo, "Panel Tavolo");
        add(editPanelPrenotazione, "Panel Prenotazione");
        add(editPanelCliente, "Panel Cliente");
        add(editPanelCameriere, "Panel Cameriere");
    }

    public GUI.editFrame.editPanels.editPanelRistorante getEditPanelRistorante() {
        return editPanelRistorante;
    }

    public GUI.editFrame.editPanels.editPanelSala getEditPanelSala() {
        return editPanelSala;
    }

    public GUI.editFrame.editPanels.editPanelTavolo getEditPanelTavolo() {
        return editPanelTavolo;
    }

    public editPanelPrenotazione getEditPanelPrenotazioni() {
        return editPanelPrenotazione;
    }

    public GUI.editFrame.editPanels.editPanelCliente getEditPanelCliente() {
        return editPanelCliente;
    }

    public GUI.editFrame.editPanels.editPanelCameriere getEditPanelCameriere() {
        return editPanelCameriere;
    }

}