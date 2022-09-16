package GUI.mainFrame.mainPanels;

import GUI.CustomButtons.JButtonBlue;
import GUI.CustomButtons.JButtonGrey;
import GUI.CustomButtons.JButtonIndietro;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPanelSala extends JPanel {

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
    private JList listaVisualizza;

    private JLabel labelNomeSelezionato;
    private String nomeSelezionato;

    private JButtonIndietro buttonIndietro;

    public mainPanelSala(JPanel contentPane){


        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createLineBorder(Color.black);
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Definizione Attributi
        buttonAdd = new JButtonBlue("+ Sala");
        buttonEdit = new JButtonGrey("Modifica Sala");

        nomeSelezionato="*nome*";
        labelNomeSelezionato = new JLabel("Nome Ristorante Selezionato: "+nomeSelezionato);
        labelNomeSelezionato.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createEmptyBorder(3,10,3,10)));

        labelListaSelezione = new JLabel("Lista Sale");
        listaSelezione = new JList();

        buttonElencoClienti = new JButtonGrey("Elenco Clienti");
        buttonEditPrenotazioni = new JButtonGrey("Modifica Prenotazioni");

        labelTableVisualizza = new JLabel("Preview Tavoli di Sala Selezionata");
        listaVisualizza = new JList();

        buttonMostraSottoelemento = new JButtonGrey("Mostra Tavoli di Sala Selezionata");
        buttonAddPrenotazione = new JButtonBlue("+ Prenotazione in Sala");
        buttonEditCamerieri = new JButtonGrey("Mostra Camerieri di Sala Selezionata");

        buttonMostraSottoelemento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "Panel Tavolo");
            }
        });

        buttonIndietro = new JButtonIndietro(contentPane, "Panel Ristorante");

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

        //Label nomeSelezionato
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth=2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 10);
        add(labelNomeSelezionato, gbc);

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
        add(new JScrollPane(listaVisualizza), gbc);

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

        //Button Indietro
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 10, 10, 0);
        add(buttonIndietro, gbc);
    }

    public JButton getButtonElencoClienti() {
        return buttonElencoClienti;
    }

    public JButton getButtonEditPrenotazioni() {
        return buttonEditPrenotazioni;
    }

    public JButton getButtonMostraSottoelemento() {
        return buttonMostraSottoelemento;
    }

    public JButton getButtonAddPrenotazione() {
        return buttonAddPrenotazione;
    }

    public JButton getButtonEditCamerieri() {
        return buttonEditCamerieri;
    }

    public JButton getButtonAdd() {
        return buttonAdd;
    }

    public JButton getButtonEdit() {
        return buttonEdit;
    }

    public JButtonIndietro getButtonIndietro() {
        return buttonIndietro;
    }

    public void setModelListaSeleziona(DefaultListModel defaultListModel){
        listaSelezione.setModel(defaultListModel);
    }

    public void setModelListaVisualizza(DefaultListModel defaultListModel){
        listaVisualizza.setModel(defaultListModel);
    }

    public void setNomeSelezionato(String nomeSelezionato) {
        this.nomeSelezionato = nomeSelezionato;
    }
}
