package GUI.mainFrame.mainPanels;

import GUI.CustomButtons.JButtonBlue;
import GUI.CustomButtons.JButtonGrey;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class mainPanelRistorante extends JPanel{

    private JButton buttonStatistics;
    private JButton buttonElencoClienti;
    private JButton buttonEditPrenotazioni;

    private JButton buttonMostraSottoelemento;
    private JButton buttonAddPrenotazione;
    private JButton buttonEditCamerieri;

    private JButton buttonAdd;
    private JButton buttonEdit;

    private JLabel labelListaSelezione;
    private JList listaSelezione;

    private JLabel labelListaVisualizza;
    private JList listaVisualizza;

    private JLabel labelCapienza;
    private JLabel labelNumeroCamerieri;

    public mainPanelRistorante() {

        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createLineBorder(Color.black);
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Definizione Attributi
        buttonAdd = new JButtonBlue("+ Ristorante");
        buttonEdit = new JButtonGrey("Modifica Ristorante");

        labelCapienza = new JLabel("Capienza Clienti: ");
        labelCapienza.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(3,10,3,10)));

        labelNumeroCamerieri = new JLabel("Numero Camerieri: ");
        labelNumeroCamerieri.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(3,10,3,10)));

        labelListaSelezione = new JLabel("Lista Ristoranti");
        listaSelezione = new JList();
        listaSelezione.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXX");

        buttonStatistics = new JButtonGrey("Statistiche");
        buttonElencoClienti = new JButtonGrey("Elenco Clienti");
        buttonEditPrenotazioni = new JButtonGrey("Modifica Prenotazioni");

        labelListaVisualizza = new JLabel("Seleziona un Ristorante per visualizzare la Preview delle Sale");
        listaVisualizza = new JList();
        listaVisualizza.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXX");

        buttonMostraSottoelemento = new JButtonGrey("Mostra Sale di Ristorante Selezionato");
        buttonAddPrenotazione = new JButtonBlue("+ Prenotazione in Ristorante");
        buttonEditCamerieri = new JButtonGrey("Mostra Camerieri di Ristorante Selezionato");


        //Aggiunta Elementi a Layout
        //+ Elemento
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 0);
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

        //Label Capienza
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 0);
        add(labelCapienza, gbc);

        //Label Numero Camerieri
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 0, 10);
        add(labelNumeroCamerieri, gbc);

        //Label Lista Selezione
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(5, 10, 0, 10);
        add(labelListaSelezione, gbc);

        //Lista Selezione
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 7;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(new JScrollPane(listaSelezione), gbc);

        //Statistiche
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.02;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(buttonStatistics, gbc);

        //Elenco Clienti
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.02;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(buttonElencoClienti, gbc);

        //Prenotazioni
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.02;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(buttonEditPrenotazioni, gbc);

        //button Edit Camerieri
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=5;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.02;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(buttonEditCamerieri, gbc);

        //Label Table Sottoelementi
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(15, 10, 0, 10);
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(labelListaVisualizza, gbc);

        //Table Sottoelementi
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(new JScrollPane(listaVisualizza), gbc);

        //Mostra Sottoelementi
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.weightx = 0.0;
        gbc.weighty = 0.3;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(60, 10, 30, 0);
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(buttonMostraSottoelemento, gbc);

        //+ Prenotazione
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 8;
        gbc.weightx = 0.0;
        gbc.weighty = 0.3;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(60, 0, 30, 10);
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(buttonAddPrenotazione, gbc);
    }

    public JButton getButtonStatistics() {
        return buttonStatistics;
    }

    public JButton getButtonElencoClienti() {
        return buttonElencoClienti;
    }

    public JButton getButtonEditPrenotazioni() {
        return buttonEditPrenotazioni;
    }

    public JButton getButtonMostraSottoelemento() {
        return buttonMostraSottoelemento;
    }

    public JButton getButtonAddPrenotazione() {
        return buttonAddPrenotazione;
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

    public void setModelListaSeleziona(DefaultListModel defaultListModel){
        listaSelezione.setModel(defaultListModel);
    }

    public void setModelListaVisualizza(DefaultListModel defaultListModel){
        listaVisualizza.setModel(defaultListModel);
    }

    public JList getListaSelezione() {
        return listaSelezione;
    }

    public JList getListaVisualizza() {
        return listaVisualizza;
    }

    public void setLabelListaVisualizza(String string) {
        labelListaVisualizza.setText("Preview Sale di " + string);
    }

    public void setLabelCapienza(String string) {
        this.labelCapienza.setText("Capienza Clienti: " + string);
    }

    public void setLabelNumeroCamerieri(String string) {
        this.labelNumeroCamerieri.setText("Numero Camerieri: " + string);
    }
}