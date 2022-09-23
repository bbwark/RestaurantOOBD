package GUI.editFrame.editPanels;

import GUI.CustomButtons.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class editPanelTavolo extends JPanel {

    private JLabel labelData;
    private JTextField textFieldMaxAvventori;

    private JButton buttonAddSelezionePrenotazione;
    private JButton buttonRemoveSelezionatoPrenotazione;
    private JList listaSelezionePrenotazione;

    private JButton buttonAddSelezioneTavoloAdiacente;
    private JButton buttonRemoveSelezionatoTavoloAdiacente;
    private JList listaSelezioneTavoloAdiacente;

    private JButton buttonModificaSelezionatoPrenotazione;
    private JButton buttonModificaSelezionatoTavoloAdiacente;

    private JButton buttonElimina;
    private JButton buttonConferma;
    private JButton buttonAnnulla;


    public editPanelTavolo() {
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Modifica Tavolo");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Dichiarazione Componenti
        labelData = new JLabel("Max Avventori: ");
        textFieldMaxAvventori = new JTextField(20);

        buttonAddSelezionePrenotazione = new JButtonBlue(" + ");
        buttonRemoveSelezionatoPrenotazione = new JButtonGrey("Rimuovi Prenotazione Selezionata");
        listaSelezionePrenotazione = new JList();

        buttonAddSelezioneTavoloAdiacente = new JButtonBlue(" + ");
        buttonRemoveSelezionatoTavoloAdiacente = new JButtonGrey("Rimuovi Tavolo Adiacente");
        listaSelezioneTavoloAdiacente = new JList();

        buttonElimina = new JButtonRed("ELIMINA Tavolo");
        buttonModificaSelezionatoPrenotazione = new JButtonGrey("Modifica Prenotazione Selezionata");
        buttonModificaSelezionatoTavoloAdiacente = new JButtonGrey("Modifica Tavolo Adiacente Selezionato");

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
        add(textFieldMaxAvventori, gbc);

        //button Add Selezione Cliente
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 0);
        add(buttonAddSelezionePrenotazione, gbc);

        //button Remove Cliente Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 0, 10);
        add(buttonRemoveSelezionatoPrenotazione, gbc);

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
        add(new JScrollPane(listaSelezionePrenotazione), gbc);

        //button Modifica Cliente Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(buttonModificaSelezionatoPrenotazione, gbc);

        //button Add Selezione Cameriere
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 0, 0);
        add(buttonAddSelezioneTavoloAdiacente, gbc);

        //button Remove Cameriere Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 0, 10);
        add(buttonRemoveSelezionatoTavoloAdiacente, gbc);

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
        add(new JScrollPane(listaSelezioneTavoloAdiacente), gbc);

        //button Modifica Cameriere Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(buttonModificaSelezionatoTavoloAdiacente, gbc);

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

    public JButton getButtonModificaSelezionatoPrenotazione() {
        return buttonModificaSelezionatoPrenotazione;
    }

    public JButton getButtonModificaSelezionatoTavoloAdiacente() {
        return buttonModificaSelezionatoTavoloAdiacente;
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

    public JButton getButtonAddSelezioneTavoloAdiacente() {
        return buttonAddSelezioneTavoloAdiacente;
    }

    public JButton getButtonRemoveSelezionatoTavoloAdiacente() {
        return buttonRemoveSelezionatoTavoloAdiacente;
    }

    public JButton getButtonAddSelezionePrenotazione() {
        return buttonAddSelezionePrenotazione;
    }

    public JButton getButtonRemoveSelezionatoPrenotazione() {
        return buttonRemoveSelezionatoPrenotazione;
    }

    public void setTextFieldMaxAvventori(String string) {
        this.textFieldMaxAvventori.setText(string);
    }

    public void setModelListaSelezionaCliente(DefaultListModel defaultListModel){
        listaSelezionePrenotazione.setModel(defaultListModel);
    }

    public void setModelListaSelezionaCameriere(DefaultListModel defaultListModel){
        listaSelezioneTavoloAdiacente.setModel(defaultListModel);
    }
}