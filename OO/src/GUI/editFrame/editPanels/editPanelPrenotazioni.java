package GUI.editFrame.editPanels;

import GUI.CustomButtons.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editPanelPrenotazioni extends JPanel {

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
    private JButton buttonModificaSelezionatoCameriere;

    private JButton buttonElimina;
    private JButton buttonConferma;
    private JButton buttonAnnulla;

    private JButton buttonIndietro;

    public editPanelPrenotazioni() {
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Modifica Prenotazione");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Dichiarazione Componenti
        labelData = new JLabel("Data: ");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        buttonAddSelezioneCliente = new JButtonBlue(" + ");
        buttonRemoveSelezionatoCliente = new JButtonGrey("Rimuovi Cliente Selezionato");
        listaSelezioneCliente = new JList();

        buttonAddSelezioneCameriere = new JButtonBlue(" + ");
        buttonRemoveSelezionatoCameriere = new JButtonGrey("Rimuovi Cameriere Selezionato");
        listaSelezioneCameriere = new JList();

        buttonElimina = new JButtonRed("ELIMINA Prenotazione");
        buttonModificaSelezionatoCliente = new JButtonGrey("Modifica Cliente Selezionato");
        buttonModificaSelezionatoCameriere = new JButtonGrey("Modifica Cameriere Selezionato");

        buttonConferma = new JButtonConferma();
        buttonAnnulla = new JButtonAnnulla();

        buttonIndietro = new JButtonGrey("Indietro");

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
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(buttonModificaSelezionatoCliente, gbc);

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
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(buttonModificaSelezionatoCameriere, gbc);

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

        //button Indietro
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10,10,10,0);
        add(buttonIndietro, gbc);
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

    public JButton getButtonIndietro() {
        return buttonIndietro;
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

    public String getDate(){
        return (String) datePicker.getModel().getValue();
    }

    public void setModelListaSelezionaCliente(DefaultListModel defaultListModel){
        listaSelezioneCliente.setModel(defaultListModel);
    }

    public void setModelListaSelezionaCameriere(DefaultListModel defaultListModel){
        listaSelezioneCameriere.setModel(defaultListModel);
    }
}