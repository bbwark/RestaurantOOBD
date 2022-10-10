package GUI.editFrame.editPanels;

import GUI.CustomButtons.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editPanelSala extends JPanel {

    private JLabel labelNome;
    private JTextField textFieldNome;

    private JButton buttonAddSelezione;
    private JButton buttonRemoveSelezionato;
    private JList listaSelezione;
    private JButton buttonModificaSelezionato;

    private JButton buttonElimina;
    private JButton buttonConferma;
    private JButton buttonAnnulla;

    private String numTavoli;

    public editPanelSala(){
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Modifica Sala");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15,15,15,15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Dichiarazione Componenti
        labelNome = new JLabel("Nome: ");
        textFieldNome = new JTextField(20);

        buttonAddSelezione = new JButtonBlue("+ Tavolo");
        buttonRemoveSelezionato = new JButtonGrey("Rimuovi Tavolo Selezionato");
        listaSelezione = new JList();

        buttonElimina = new JButtonRed("ELIMINA Sala");
        buttonModificaSelezionato = new JButtonGrey("Modifica Tavolo Selezionato");

        buttonConferma = new JButtonConferma();
        buttonAnnulla = new JButtonAnnulla();


        //Aggiunta Elementi a Layout
        //Label Nome
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor= GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10,10,5,0);
        add(labelNome, gbc);

        //textField Nome
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.insets=new Insets(10,0,5,10);
        add(textFieldNome, gbc);

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

        //button Elimina Sala
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=1;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 10, 5, 10);
        add(buttonElimina, gbc);

    }

    public JButton getButtonModificaSelezionato() {
        return buttonModificaSelezionato;
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

    public JButton getButtonAddSelezione() {
        return buttonAddSelezione;
    }

    public JButton getButtonRemoveSelezionato() {
        return buttonRemoveSelezionato;
    }

    public String getNumTavoli() {
        return numTavoli;
    }

    public void setModelListaSeleziona(DefaultListModel defaultListModel){
        listaSelezione.setModel(defaultListModel);
    }

    public String getTextFieldNome() {
        return textFieldNome.getText();
    }

    public void setTextFieldNome(String nome) {
        this.textFieldNome.setText(nome);
    }

    public JList getListaSelezione() {
        return listaSelezione;
    }
}
