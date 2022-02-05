package GUI.editFrame.editPanels;

import GUI.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editPanelCameriere extends JPanel {

    private JLabel labelNome;
    private JLabel labelCognome;

    private JTextField textFieldNome;
    private JTextField textFieldCognome;

    private JButton buttonElimina;
    private JButton buttonConferma;
    private JButton buttonAnnulla;

    private JLabel labelPrenotazioni;
    private JList listPrenotazioni;
    private JButton buttonAddPrenotazione;
    private JButton buttonRemovePrenotazione;

    private JButton buttonStatistiche;

    private JButton buttonIndietro;

    public editPanelCameriere(JPanel contentPane){
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Modifica Cameriere");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        labelNome = new JLabel("Nome: ");
        labelCognome = new JLabel("Cognome: ");

        textFieldNome = new JTextField(20);
        textFieldCognome = new JTextField(20);

        buttonElimina = new JButtonRed("ELIMINA Cameriere");
        buttonConferma = new JButtonConferma();
        buttonAnnulla = new JButtonAnnulla();

        labelPrenotazioni = new JLabel("Prenotazioni a cui lavora:");
        listPrenotazioni = new JList();

        DefaultListModel modelloPrenotazioni = new DefaultListModel();
        //Codice per la gestione della lista

        buttonStatistiche = new JButtonGrey("Statistiche");

        buttonAddPrenotazione = new JButtonBlue("+ Prenotazione");
        buttonRemovePrenotazione = new JButtonGrey("Rimuovi Prenotazione Selezionata");

        buttonIndietro=new JButtonIndietro(contentPane);


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

        //button Add Prenotazione
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,0,0,0);
        add(buttonAddPrenotazione, gbc);

        //button Remove Prenotazione
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,0,0,10);
        add(buttonRemovePrenotazione, gbc);

        //list Prenotazioni
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth=2;
        gbc.gridheight=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,10,10);
        add(listPrenotazioni, gbc);

        //button Statistiche
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);
        add(buttonStatistiche, gbc);

        //button Indietro
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor=GridBagConstraints.LAST_LINE_START;
        add(buttonIndietro, gbc);

        //button Elimina
        gbc = new GridBagConstraints();
        gbc.gridx= 2;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,10,0,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonElimina, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,10,5,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonAnnulla, gbc);

        //button Conferma
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=4;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets = new Insets(0,10,0,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonConferma, gbc);
    }
}
