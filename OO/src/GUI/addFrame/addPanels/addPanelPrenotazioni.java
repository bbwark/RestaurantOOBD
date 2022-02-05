package GUI.addFrame.addPanels;

import GUI.JButtonAnnulla;
import GUI.JButtonBlue;
import GUI.JButtonConferma;
import GUI.JButtonGrey;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class addPanelPrenotazioni extends JPanel {

    private JLabel labelData;
    private JTextField textFieldData;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private JButton buttonAddSelezione;
    private JButton buttonRemoveSelezionato;

    private JList listaSelezione;

    private JButton buttonAnnulla;
    private JButton buttonConferma;

    public addPanelPrenotazioni(JPanel contentPane) {
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Aggiungi Prenotazione");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Dichiarazione Componenti
        labelData = new JLabel("Data: ");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        buttonAddSelezione = new JButtonBlue(" + ");
        buttonRemoveSelezionato = new JButtonGrey("Rimuovi Prenotazione Selezionata");

        buttonAnnulla = new JButtonAnnulla();
        buttonConferma = new JButtonConferma();

        listaSelezione = new JList<>();

        DefaultListModel modelloSelezione = new DefaultListModel();


        //Aggiunta Elementi a Layout
        //label Data
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor=GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10,10,10,0);
        add(labelData, gbc);

        //textField Data
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10,0,10,10);
        add(datePicker, gbc);

        //button Add Selezione
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10,10,0,0);
        add(buttonAddSelezione, gbc);

        //button Remove Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10,0,0,10);
        add(buttonRemoveSelezionato, gbc);

        //list Selezione
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=2;
        gbc.gridheight=3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,10,10,10);
        add(listaSelezione, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30,10,5,10);
        add(buttonAnnulla, gbc);

        //button Conferma
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,10,10,10);
        add(buttonConferma, gbc);
    }
}