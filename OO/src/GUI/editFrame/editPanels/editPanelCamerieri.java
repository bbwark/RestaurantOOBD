package GUI.editFrame.editPanels;

import GUI.CustomButtons.JButtonAnnulla;
import GUI.CustomButtons.JButtonBlue;
import GUI.CustomButtons.JButtonConferma;
import GUI.CustomButtons.JButtonGrey;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class editPanelCamerieri extends JPanel {

    private JButton buttonAddSelezione;
    private JButton buttonRimuoviSelezionato;

    private JList listaSelezione;

    private JButton buttonAnnulla;
    private JButton buttonConferma;

    private JButton buttonModificaSelezionato;

    private JLabel labelNomeSelezionatoSuperiore;
    private String nomeSelezionatoSuperiore;
    private String tipoSelezionatoSuperiore;


    public editPanelCamerieri(JPanel contentPane){
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Modifica Camerieri");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        buttonAddSelezione = new JButtonBlue(" + ");
        buttonRimuoviSelezionato = new JButtonGrey("Rimuovi Cameriere Selezionato");

        listaSelezione = new JList();

        buttonAnnulla = new JButtonAnnulla();
        buttonConferma = new JButtonConferma();

        buttonModificaSelezionato = new JButtonGrey("Modifica Cameriere Selezionato");

        labelNomeSelezionatoSuperiore = new JLabel("Nome "+tipoSelezionatoSuperiore+": "+nomeSelezionatoSuperiore);
        labelNomeSelezionatoSuperiore.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(3,10,3,10)));

        //Aggiunta Elementi a Layout
        //button Add Selezione
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(10, 10, 0,0);
        add(buttonAddSelezione, gbc);

        //button Rimuovi Selezione
        gbc=new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(10,0,0,10);
        add(buttonRimuoviSelezionato, gbc);

        //button Lista Selezione
        gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.gridheight=4;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;
        add(listaSelezione, gbc);

        //button Modifica Selezionato
        gbc=new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=1;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,10,0,10);
        add(buttonModificaSelezionato, gbc);

        //button Annulla
        gbc=new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30,10,5,10);
        add(buttonAnnulla, gbc);

        //button Conferma
        gbc.gridx=2;
        gbc.gridy=4;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,10,10,10);
        add(buttonConferma, gbc);
    }

    public void setNomeSelezionatoSuperiore(String nomeSelezionatoSuperiore) {
        this.nomeSelezionatoSuperiore = nomeSelezionatoSuperiore;
    }

    public void setTipoSelezionatoSuperiore(String tipoSelezionatoSuperiore) {
        this.tipoSelezionatoSuperiore = tipoSelezionatoSuperiore;
    }

    public JButton getButtonAddSelezione() {
        return buttonAddSelezione;
    }

    public JButton getButtonRimuoviSelezionato() {
        return buttonRimuoviSelezionato;
    }

    public JButton getButtonAnnulla() {
        return buttonAnnulla;
    }

    public JButton getButtonConferma() {
        return buttonConferma;
    }

    public JButton getButtonModificaSelezionato() {
        return buttonModificaSelezionato;
    }

    public void setModelListaSeleziona(DefaultListModel defaultListModel){
        listaSelezione.setModel(defaultListModel);
    }
}