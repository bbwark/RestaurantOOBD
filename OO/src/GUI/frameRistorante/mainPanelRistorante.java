package GUI.frameRistorante;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPanelRistorante extends JPanel{

    private JButton buttonStatistics;
    private JButton buttonElencoClienti;
    private JButton buttonPrenotazioni;

    private JButton buttonMostraSottoelemento;
    private JButton buttonAddPrenotazione;

    private JButton buttonAdd;
    private JButton buttonEdit;

    private JLabel labelListaSelezione;
    private JList listaSelezione;

    private JLabel labelTableVisualizza;
    private JTable tableVisualizza;

    private JLabel labelNomeSelezionato;
    private JLabel labelNomeElementoSelezionato;

    public mainPanelRistorante(JPanel contentPane) {

        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createLineBorder(Color.black);
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Definizione Attributi
        buttonAdd = new JButton("+ Ristorante");
        buttonEdit = new JButton("Modifica Ristorante");

        labelNomeSelezionato = new JLabel("Nome Ristorante Selezionato: ");
        labelNomeElementoSelezionato = new JLabel("*nome*");

        labelListaSelezione = new JLabel("Lista Ristoranti");
        listaSelezione = new JList();

        DefaultListModel modelloSelezione = new DefaultListModel();
        //codice che estrae la lista di oggetti selezionabili

        buttonStatistics = new JButton("Statistiche");
        buttonElencoClienti = new JButton("Elenco Clienti");
        buttonPrenotazioni = new JButton("Prenotazioni");

        labelTableVisualizza = new JLabel("Lista Sale di Ristorante " + "*nome*");
        tableVisualizza = new JTable();

        DefaultTableModel modelloVisualizza = new DefaultTableModel();
        //codice che estrae la lista di sottoelementi del selezionato

        buttonMostraSottoelemento = new JButton("Mostra Sale di Ristorante Selezionato");
        buttonAddPrenotazione = new JButton("+ Prenotazione in Ristorante");

        buttonMostraSottoelemento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.next(contentPane);
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
        gbc.insets = new Insets(10, 0, 0, 0);
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

        //Label nomeSelezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 10, 0, 8);
        add(labelNomeSelezionato, gbc);

        //Label nomeElementoSelezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(labelNomeElementoSelezionato, gbc);

        //Label Lista Selezione
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(5, 0, 0, 10);
        add(labelListaSelezione, gbc);

        //Lista Selezione
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 6;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(new JScrollPane(listaSelezione), gbc);

        //Statistiche
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.02;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 5);
        add(buttonStatistics, gbc);

        //Elenco Clienti
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.02;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 5);
        add(buttonElencoClienti, gbc);

        //Prenotazioni
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.02;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 5);
        add(buttonPrenotazioni, gbc);

        //Label Table Sottoelementi
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(15, 10, 0, 5);
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(labelTableVisualizza, gbc);

        //Table Sottoelementi
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 10, 0, 5);
        add(new JScrollPane(tableVisualizza), gbc);

        //Mostra Sottoelementi
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        gbc.weighty = 0.15;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 30, 5);
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(buttonMostraSottoelemento, gbc);

        //+ Prenotazione
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        gbc.weighty = 0.15;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 30, 5);
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(buttonAddPrenotazione, gbc);
    }
}