package GUI.mainFrame.mainPanels;

import GUI.CustomButtons.JButtonGreen;
import GUI.CustomButtons.JButtonGrey;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class mainPanelSelezionaTavolo extends JPanel {

    private JLabel labelListaSelezione;
    private JList listaSelezione;

    private JButton buttonIndietro;

    private JButton buttonAddPrenotazione;

    public mainPanelSelezionaTavolo(){

        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createLineBorder(Color.black);
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Definizione Attributi
        buttonAddPrenotazione = new JButtonGreen("Seleziona Tavolo per Prenotazione");
        labelListaSelezione = new JLabel("Lista Tavoli");
        listaSelezione = new JList();

        buttonIndietro = new JButtonGrey("Indietro");

        //Aggiunta Elementi a Layout
        //Label Elenco
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(5, 5, 5, 0);
        add(labelListaSelezione, gbc);

        //Lista Selezione
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(0, 10, 20, 10);
        add(new JScrollPane(listaSelezione), gbc);

        //ButtonEdit
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(20, 20, 10, 5);
        add(buttonAddPrenotazione, gbc);

        //Button indietro
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(20, 5, 10, 20);
        add(buttonIndietro, gbc);
    }

    public JButton getButtonIndietro() {
        return buttonIndietro;
    }

    public JButton getButtonAddPrenotazione() {
        return buttonAddPrenotazione;
    }

    public JList getListaSelezione() {
        return listaSelezione;
    }

    public void setModelListaSeleziona(DefaultListModel defaultListModel){
        listaSelezione.setModel(defaultListModel);
    }
}
