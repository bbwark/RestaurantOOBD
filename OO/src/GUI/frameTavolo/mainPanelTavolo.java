package GUI.frameTavolo;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPanelTavolo extends JPanel {

    private JButton buttonMostraSottoelemento;
    private JButton buttonAddPrenotazione;

    private JButton buttonAdd;
    private JButton buttonEdit;

    private JLabel labelListaSelezione;
    private JList listaSelezione;

    private JLabel labelNomeSelezionato;
    private JLabel labelNomeElementoSelezionato;

    public mainPanelTavolo(JPanel contentPane) {

        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createLineBorder(Color.black);
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Definizione Attributi
        buttonAdd = new JButton("+ Tavolo");
        buttonEdit = new JButton("Modifica Tavolo");

        labelNomeSelezionato = new JLabel("Nome Tavolo Selezionata: ");
        labelNomeElementoSelezionato = new JLabel("*nome*");

        labelListaSelezione = new JLabel("Lista Tavoli");
        listaSelezione = new JList();

        DefaultListModel modelloSelezione = new DefaultListModel();
        //codice che estrae la lista di oggetti selezionabili

        buttonMostraSottoelemento = new JButton("Mostra Prenotazioni Tavolo");
        buttonAddPrenotazione = new JButton("+ Prenotazione a Tavolo");

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
        gbc.gridheight = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 10);
        add(new JScrollPane(listaSelezione), gbc);

        //Mostra Sottoelementi
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth=2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.15;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 30, 5);
        add(buttonMostraSottoelemento, gbc);

        //+ Prenotazione
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth=2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.15;
        gbc.ipady = 15;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 30, 5);
        add(buttonAddPrenotazione, gbc);
    }
}
