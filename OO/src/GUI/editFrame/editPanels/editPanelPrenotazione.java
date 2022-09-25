package GUI.editFrame.editPanels;

import GUI.CustomButtons.*;
import GUI.DateLabelFormatter.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class editPanelPrenotazione extends JPanel {

    private JLabel labelData;
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private JButton buttonAddSelezioneCliente;
    private JButton buttonRemoveSelezionatoCliente;
    private JList listaSelezioneCliente;

    private JButton buttonAddSelezioneCameriere;
    private JButton buttonRemoveSelezionatoCameriere;
    private JList listaSelezioneCameriere;

    private JButton buttonModificaSelezionatoCliente;
    private JButton buttonAddClienteEsistente;
    private JButton buttonModificaSelezionatoCameriere;
    private JButton buttonAddCameriereEsistente;

    private JButton buttonElimina;
    private JButton buttonConferma;
    private JButton buttonAnnulla;


    public editPanelPrenotazione() {
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Modifica Prenotazione");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Dichiarazione Componenti
        labelData = new JLabel("Data: ");
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        buttonAddSelezioneCliente = new JButtonBlue(" + Nuovo Cliente");
        buttonRemoveSelezionatoCliente = new JButtonGrey("Rimuovi Cliente Selezionato");
        listaSelezioneCliente = new JList();

        buttonAddSelezioneCameriere = new JButtonBlue(" + Nuovo Cameriere");
        buttonRemoveSelezionatoCameriere = new JButtonGrey("Rimuovi Cameriere Selezionato");
        listaSelezioneCameriere = new JList();

        buttonElimina = new JButtonRed("ELIMINA Prenotazione");
        buttonModificaSelezionatoCliente = new JButtonGrey("Modifica Cliente Selezionato");
        buttonAddClienteEsistente = new JButtonBlue("+ Cliente Esistente");
        buttonModificaSelezionatoCameriere = new JButtonGrey("Modifica Cameriere Selezionato");
        buttonAddCameriereEsistente = new JButtonBlue("+ Cameriere Esistente");


        buttonConferma = new JButtonConferma();
        buttonAnnulla = new JButtonAnnulla();

        //Aggiunta Elementi a Layout
        //Label Data
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 5, 0);
        add(labelData, gbc);

        //textField Data
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 0, 5, 10);
        add(datePicker, gbc);

        //button Add Selezione Cliente
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 0);
        add(buttonAddSelezioneCliente, gbc);

        //button Remove Cliente Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 0, 10);
        add(buttonRemoveSelezionatoCliente, gbc);

        //Lista Selezione Cliente
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(new JScrollPane(listaSelezioneCliente), gbc);

        //button Modifica Cliente Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(buttonModificaSelezionatoCliente, gbc);

        //button Add Cliente Esistente
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 0);
        add(buttonAddClienteEsistente, gbc);

        //button Add Selezione Cameriere
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 0);
        add(buttonAddSelezioneCameriere, gbc);

        //button Remove Cameriere Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 0, 10);
        add(buttonRemoveSelezionatoCameriere, gbc);

        //Lista Selezione Cameriere
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(new JScrollPane(listaSelezioneCameriere), gbc);

        //button Modifica Cameriere Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(buttonModificaSelezionatoCameriere, gbc);

        //button Add Cameriere Esistente
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 0);
        add(buttonAddCameriereEsistente, gbc);

        //button Elimina Prenotazione
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(buttonElimina, gbc);

        //button Conferma
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10,0,10,10);
        add(buttonConferma, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx=3;
        gbc.gridy=5;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10,10,10,0);
        add(buttonAnnulla, gbc);

    }

    public JButton getButtonModificaSelezionatoCliente() {
        return buttonModificaSelezionatoCliente;
    }

    public JButton getButtonModificaSelezionatoCameriere() {
        return buttonModificaSelezionatoCameriere;
    }

    public JButton getButtonElimina() {
        return buttonElimina;
    }

    public JButton getButtonConferma() {
        return buttonConferma;
    }

    public JButton getButtonAnnulla() {
        return buttonAnnulla;
    }

    public JButton getButtonAddSelezioneCameriere() {
        return buttonAddSelezioneCameriere;
    }

    public JButton getButtonRemoveSelezionatoCameriere() {
        return buttonRemoveSelezionatoCameriere;
    }

    public JButton getButtonAddSelezioneCliente() {
        return buttonAddSelezioneCliente;
    }

    public JButton getButtonRemoveSelezionatoCliente() {
        return buttonRemoveSelezionatoCliente;
    }

    public LocalDate getDate(){
        Date result = (Date) datePicker.getModel().getValue();
        return result.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setModelListaSelezionaCliente(DefaultListModel defaultListModel){
        listaSelezioneCliente.setModel(defaultListModel);
    }

    public void setModelListaSelezionaCameriere(DefaultListModel defaultListModel){
        listaSelezioneCameriere.setModel(defaultListModel);
    }
}