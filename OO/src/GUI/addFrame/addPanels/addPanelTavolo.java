package GUI.addFrame.addPanels;

import GUI.CustomButtons.JButtonAnnulla;
import GUI.CustomButtons.JButtonConferma;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class addPanelTavolo extends JPanel {

    private JLabel labelMaxAvventori;
    private JTextField textFieldMaxAvventori;

    private JButton buttonAnnulla;
    private JButton buttonConferma;

    public addPanelTavolo(JPanel contentPane) {
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Aggiungi Tavolo");
        Border bordoEsterno = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        labelMaxAvventori = new JLabel("Massimo Avventori: ");
        textFieldMaxAvventori = new JTextField(20);

        buttonAnnulla = new JButtonAnnulla();
        buttonConferma = new JButtonConferma();

        //Aggiunta Elementi a Layout
        //label Nome
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 0);
        add(labelMaxAvventori, gbc);

        //textField Nome
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 0, 10, 10);
        add(textFieldMaxAvventori, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 0);
        add(buttonAnnulla, gbc);

        //button Conferma
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 0, 10, 10);
        add(buttonConferma, gbc);
    }

    public JButton getButtonAnnulla() {
        return buttonAnnulla;
    }

    public JButton getButtonConferma() {
        return buttonConferma;
    }

    public String getTextFieldMaxAvventori() {
        return textFieldMaxAvventori.getText();
    }
}