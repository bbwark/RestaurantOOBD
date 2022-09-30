package GUI.editFrame.editPanels;

import GUI.CustomButtons.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class editPanelCliente extends JPanel {

    private JLabel labelNome;
    private JLabel labelCognome;
    private JLabel labelCartaID;
    private JLabel labelNumeroTel;

    private JTextField textFieldNome;
    private JTextField textFieldCognome;
    private JTextField textFieldCartaID;
    private JTextField textFieldNumeroTel;

    private JButton buttonElimina;
    private JButton buttonConferma;
    private JButton buttonAnnulla;

    private JList listPrenotazioni;
    private JButton buttonAddPrenotazione;
    private JButton buttonRemovePrenotazione;
    private JButton buttonModificaPrenotazioneSelezionata;

    public editPanelCliente(){
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Modifica Cliente");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        labelNome = new JLabel("Nome: ");
        labelCognome = new JLabel("Cognome: ");
        labelCartaID = new JLabel("Carta di Identità: ");
        labelNumeroTel = new JLabel("Numero di Telefono");

        textFieldNome = new JTextField(20);
        textFieldCognome = new JTextField(20);
        textFieldCartaID = new JTextField(20);
        textFieldNumeroTel = new JTextField(20);

        buttonElimina = new JButtonRed("ELIMINA Cliente");
        buttonConferma = new JButtonConferma();
        buttonAnnulla = new JButtonAnnulla();

        listPrenotazioni = new JList();

        buttonAddPrenotazione = new JButtonBlue("+ Prenotazione");
        buttonRemovePrenotazione = new JButtonGrey("Rimuovi Prenotazione Selezionata");
        buttonModificaPrenotazioneSelezionata = new JButtonGrey("Modifica Prenotazione Selezionata");

        //label Nome
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(labelNome, gbc);

        //textField Nome
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,0,0,10);
        add(textFieldNome, gbc);

        //label Cognome
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0,0,10,0);
        add(labelCognome, gbc);

        //textField Cognome
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,0,10,10);
        add(textFieldCognome, gbc);

        //label Carta di Identità
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0,0,10,0);
        add(labelCartaID, gbc);

        //textField Carta di Identità
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,0,10,10);
        add(textFieldCartaID, gbc);

        //label Numero Telefono
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0,0,10,0);
        add(labelNumeroTel, gbc);

        //textField Numero Telefono
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,0,10,10);
        add(textFieldNumeroTel, gbc);

        //button Add Prenotazione
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,0,0,0);
        add(buttonAddPrenotazione, gbc);

        //button Remove Prenotazione
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=4;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,0,0,10);
        add(buttonRemovePrenotazione, gbc);

        //list Prenotazioni
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.gridwidth=2;
        gbc.gridheight=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,20,10);
        add(new JScrollPane(listPrenotazioni), gbc);

        //button Elimina
        gbc = new GridBagConstraints();
        gbc.gridx= 2;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,10,0,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonElimina, gbc);

        //button modifica prenotazione selezionata
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=5;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(100,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.LAST_LINE_START;
        add(buttonModificaPrenotazioneSelezionata, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=6;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.LAST_LINE_START;
        add(buttonAnnulla, gbc);

        //button Conferma
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=7;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,10,20,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.LAST_LINE_START;
        add(buttonConferma, gbc);
    }

    public String getTextFieldNome() {
        return textFieldNome.getText();
    }

    public void setTextFieldNome(String nome) {
        this.textFieldNome.setText(nome);
    }

    public String getTextFieldCognome() {
        return textFieldCognome.getText();
    }

    public void setTextFieldCognome(String cognome) {
        this.textFieldCognome.setText(cognome);
    }

    public String getTextFieldCartaID() {
        return textFieldCartaID.getText();
    }

    public void setTextFieldCartaID(String cartaID) {
        this.textFieldCartaID.setText(cartaID);
    }

    public String getTextFieldNumeroTel() {
        return textFieldNumeroTel.getText();
    }

    public void setTextFieldNumeroTel(String numeroTel) {
        this.textFieldNumeroTel.setText(numeroTel);
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

    public JButton getButtonAddPrenotazione() {
        return buttonAddPrenotazione;
    }

    public JButton getButtonRemovePrenotazione() {
        return buttonRemovePrenotazione;
    }

    public void setModelListaPrenotazioni(DefaultListModel defaultListModel){
        listPrenotazioni.setModel(defaultListModel);
    }

    public JList getListPrenotazioni() {
        return listPrenotazioni;
    }

    public JButton getButtonModificaPrenotazioneSelezionata() {
        return buttonModificaPrenotazioneSelezionata;
    }
}
