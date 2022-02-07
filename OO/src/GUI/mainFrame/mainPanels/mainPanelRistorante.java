package GUI.mainFrame.mainPanels;

import GUI.CustomButtons.JButtonBlue;
import GUI.CustomButtons.JButtonGrey;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private JLabel labelTableVisualizza;
    private JTable tableVisualizza;

    public mainPanelRistorante(JPanel contentPane) {

        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createLineBorder(Color.black);
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Definizione Attributi
        buttonAdd = new JButtonBlue("+ Ristorante");
        buttonEdit = new JButtonGrey("Modifica Ristorante");

        labelListaSelezione = new JLabel("Lista Ristoranti");
        listaSelezione = new JList();

        DefaultListModel modelloSelezione = new DefaultListModel();
        //codice che estrae la lista di oggetti selezionabili

        buttonStatistics = new JButtonGrey("Statistiche");
        buttonElencoClienti = new JButtonGrey("Elenco Clienti");
        buttonEditPrenotazioni = new JButtonGrey("Modifica Prenotazioni");

        labelTableVisualizza = new JLabel("Preview Sale di Ristorante " + "*nome*");
        tableVisualizza = new JTable();

        DefaultTableModel modelloVisualizza = new DefaultTableModel();
        //codice che estrae la lista di sottoelementi del selezionato

        buttonMostraSottoelemento = new JButtonGrey("Mostra Sale di Ristorante Selezionato");
        buttonAddPrenotazione = new JButtonBlue("+ Prenotazione in Ristorante");
        buttonEditCamerieri = new JButtonGrey("Mostra Camerieri di Ristorante Selezionato");

        buttonMostraSottoelemento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "Panel Sala");
            }
        });

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
        add(labelTableVisualizza, gbc);

        //Table Sottoelementi
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 10);
        add(new JScrollPane(tableVisualizza), gbc);

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
}