package GUI.addFrame.addPanels;

import GUI.CustomButtons.JButtonAnnulla;
import GUI.CustomButtons.JButtonGreen;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class addPanelTavoloAdiacenteTavolo extends JPanel {

    private JLabel labelSelezione;
    private JList listSelezione;

    private JButton buttonAnnulla;
    private JButton buttonAggiungi;

    public addPanelTavoloAdiacenteTavolo(){
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Aggiungi Tavolo Adiacente");
        Border bordoEsterno = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        labelSelezione = new JLabel("Lista Tavoli Disponibili");
        listSelezione = new JList();

        buttonAnnulla = new JButtonAnnulla();
        buttonAggiungi = new JButtonGreen("Aggiungi");

        //Aggiunta Elementi a Layout
        //label Selezione
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets=new Insets(10,10,2,10);
        add(labelSelezione, gbc);

        //list Selezione
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=2;
        gbc.gridheight=3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill=GridBagConstraints.BOTH;
        gbc.insets=new Insets(0,10,10,10);
        add(listSelezione, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.insets=new Insets(0,10,40,10);
        add(buttonAnnulla, gbc);

        //button Aggiungi
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.insets=new Insets(20,10,10,10);
        add(buttonAggiungi, gbc);
    }

    public JButton getButtonAnnulla() {
        return buttonAnnulla;
    }

    public JButton getButtonAggiungi() {
        return buttonAggiungi;
    }

    public void setModelListaSeleziona(DefaultListModel defaultListModel){
        listSelezione.setModel(defaultListModel);
    }
}
