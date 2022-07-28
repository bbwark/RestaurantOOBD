package GUI.editFrame.editPanels;

import GUI.CustomButtons.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editPanelTavolo extends JPanel {

    private JLabel labelMaxAvventori;
    private JTextField textFieldMaxAvventori;

    private JButton buttonAddSelezione;
    private JButton buttonRemoveSelezionato;
    private JList listaSelezione;

    private JButton buttonModificaSelezionato;

    private JButton buttonElimina;
    private JButton buttonConferma;
    private JButton buttonAnnulla;

    private JButton buttonIndietro;

    public editPanelTavolo(JPanel contentPane){

        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Modifica Tavolo");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15,15,15,15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Dichiarazione Componenti
        labelMaxAvventori = new JLabel("Max Avventori: ");
        textFieldMaxAvventori = new JTextField(20);

        buttonAddSelezione = new JButtonBlue("+ Prenotazione");
        buttonRemoveSelezionato = new JButtonGrey("Rimuovi Prenotazione Selezionata");
        listaSelezione = new JList();

        DefaultListModel modelloSelezione = new DefaultListModel();
        //codice che estrae la lista di oggetti selezionabili

        buttonElimina = new JButtonRed("ELIMINA Tavolo");
        buttonModificaSelezionato = new JButtonGrey("Modifica Prenotazione Selezionata");

        buttonConferma = new JButtonConferma();
        buttonAnnulla = new JButtonAnnulla();

        buttonIndietro=new JButtonIndietro(contentPane, "Panel Sala");

        //Creazione Listeners
        buttonModificaSelezionato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "Panel Prenotazioni");
            }
        });


        //Aggiunta Elementi a Layout
        //Label Max Avventori
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor= GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10,10,5,0);
        add(labelMaxAvventori, gbc);

        //textField Max Avventori
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets=new Insets(10,0,5,10);
        add(textFieldMaxAvventori, gbc);

        //buttonAdd Selezione
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets= new Insets(5, 10, 0, 0);
        add(buttonAddSelezione, gbc);

        //buttonRemove Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.insets= new Insets(5,0,0,10);
        add(buttonRemoveSelezionato, gbc);

        //Lista Selezione
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth=2;
        gbc.gridheight=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets= new Insets(0,10,10,10);
        add(new JScrollPane(listaSelezione), gbc);

        //button Modifica Selezionato
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets= new Insets(0,10,0,10);
        add(buttonModificaSelezionato, gbc);

        //button Conferma
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=4;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(0, 10, 10, 10);
        add(buttonConferma, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(buttonAnnulla, gbc);

        //button Elimina Tavolo
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=1;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 10, 5, 10);
        add(buttonElimina, gbc);

        //button Indietro
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5,10,10,0);
        add(buttonIndietro, gbc);
    }
}
