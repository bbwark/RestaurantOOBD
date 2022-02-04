package GUI.editFrame;

import GUI.editFrame.editPanels.editPanelRistorante;
import GUI.editFrame.editPanels.editPanelSala;
import GUI.editFrame.editPanels.editPanelTavolo;

import javax.swing.*;
import java.awt.*;

public class editFrameContentPane extends JPanel {

    private editPanelRistorante editPanelRistorante;
    private editPanelSala editPanelSala;
    private editPanelTavolo editPanelTavolo;

    public editFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new CardLayout());


        editPanelRistorante = new editPanelRistorante(this);
        editPanelSala = new editPanelSala(this);
        editPanelTavolo = new editPanelTavolo(this);

        add(editPanelRistorante, "Panel Ristorante");
        add(editPanelSala, "Panel Sala");
        add(editPanelTavolo, "Panel Tavolo");
    }
}