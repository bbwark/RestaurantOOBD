package GUI.mainFrame.mainPanels;

import GUI.CustomButtons.JButtonBlue;
import GUI.CustomButtons.JButtonGrey;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class mainPanelTavolo extends JPanel {

    private JButton buttonAddPrenotazione;
    private JButton buttonMostraSottoelemento;
    private JButton buttonEditCamerieri;
    private JButton buttonElencoClienti;

    private JButton buttonAdd;
    private JButton buttonEdit;

    private JLabel labelListaSelezione;
    private JList listaSelezione;

    private JLabel labelNomeSelezionato;
    private JButton buttonIndietro;

    public mainPanelTavolo() {

        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createLineBorder(Color.black);
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Definizione Attributi
        buttonAdd = new JButtonBlue("+ Tavolo");
        buttonEdit = new JButtonGrey("Modifica Tavolo");

        labelNomeSelezionato = new JLabel("Nome Sala Selezionata: ");
        labelNomeSelezionato.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(3,10,3,10)));

        labelListaSelezione = new JLabel("Lista Tavoli");
        listaSelezione = new JList();
        listaSelezione.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXX");

        buttonAddPrenotazione = new JButtonBlue("+ Prenotazione a Tavolo");
        buttonMostraSottoelemento = new JButtonGrey("Modifica Prenotazioni di Tavolo Selezionato");
        buttonEditCamerieri = new JButtonGrey("Mostra Camerieri di Tavolo Selezionato");
        buttonElencoClienti = new JButtonGrey("Elenco Clienti");

        buttonIndietro = new JButtonGrey("Indietro");

        //Aggiunta Elementi a Layout
        //+ Elemento
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(buttonAdd, gbc);

        //Modifica Elemento
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 10);
        add(buttonEdit, gbc);

        //Label nomeSelezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth=2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 0, 8);
        add(labelNomeSelezionato, gbc);

        //Label Lista Selezione
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(5, 0, 0, 10);
        add(labelListaSelezione, gbc);

        //Lista Selezione
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 5;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(new JScrollPane(listaSelezione), gbc);

        //Modifica Tavolo
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.15;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(buttonMostraSottoelemento, gbc);

        //button Edit Camerieri
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.15;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(buttonEditCamerieri, gbc);

        //button Elenco Clienti
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.15;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(buttonElencoClienti, gbc);

        //Add Sottoelemento
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 1000.0;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(buttonAddPrenotazione, gbc);

        //Button Indietro
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 0, 10, 0);
        add(buttonIndietro, gbc);
    }

    public JButton getButtonAddPrenotazione() {
        return buttonAddPrenotazione;
    }

    public JButton getButtonMostraSottoelemento() {
        return buttonMostraSottoelemento;
    }

    public JButton getButtonEditCamerieri() {
        return buttonEditCamerieri;
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }

    public JButton getButtonEdit() {
        return buttonEdit;
    }

    public JButton getButtonIndietro() {
        return buttonIndietro;
    }

    public JList getListaSelezione() {
        return listaSelezione;
    }

    public JButton getButtonElencoClienti() {
        return buttonElencoClienti;
    }
    public void setLabelNomeSelezionato(String string) {
        labelNomeSelezionato.setText("Nome Sala Selezionata: " + string);
    }
}