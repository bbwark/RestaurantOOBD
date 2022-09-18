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
    private editPanelCamerieri editPanelCamerieri;

    public editFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        editPanelRistorante = new editPanelRistorante();
        editPanelSala = new editPanelSala();
        editPanelTavolo = new editPanelTavolo();
        editPanelPrenotazioni = new editPanelPrenotazioni();
        editPanelCliente = new editPanelCliente();
        editPanelCameriere = new editPanelCameriere();
        editPanelCamerieri = new editPanelCamerieri();

        add(editPanelRistorante, "Panel Ristorante");
        add(editPanelSala, "Panel Sala");
        add(editPanelTavolo, "Panel Tavolo");
        add(editPanelPrenotazioni, "Panel Prenotazioni");
        add(editPanelCliente, "Panel Cliente");
        add(editPanelCameriere, "Panel Cameriere");
        add(editPanelCamerieri, "Panel Camerieri");
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

    public GUI.editFrame.editPanels.editPanelPrenotazioni getEditPanelPrenotazioni() {
        return editPanelPrenotazioni;
    }

    public GUI.editFrame.editPanels.editPanelCliente getEditPanelCliente() {
        return editPanelCliente;
    }

    public GUI.editFrame.editPanels.editPanelCameriere getEditPanelCameriere() {
        return editPanelCameriere;
    }

    public GUI.editFrame.editPanels.editPanelCamerieri getEditPanelCamerieri() {
        return editPanelCamerieri;
    }
}