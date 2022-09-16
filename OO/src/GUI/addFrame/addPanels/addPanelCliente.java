package GUI.addFrame.addPanels;

import GUI.CustomButtons.JButtonAnnulla;
import GUI.CustomButtons.JButtonConferma;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class addPanelCliente extends JPanel {

    private JLabel labelNome;
    private JTextField textFieldNome;
    private JLabel labelCognome;
    private JTextField textFieldCognome;
    private JLabel labelCartaID;
    private JTextField textFieldCartaID;
    private JLabel labelNumeroTel;
    private JTextField textFieldNumerotel;

    private JButton buttonAnnulla;
    private JButton buttonConferma;

    public addPanelCliente(JPanel contentPane) {
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Aggiungi Cliente");
        Border bordoEsterno = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        labelNome = new JLabel("Nome: ");
        textFieldNome = new JTextField(20);
        labelCognome = new JLabel("Cognome: ");
        textFieldCognome = new JTextField(20);
        labelCartaID = new JLabel("Carta ID: ");
        textFieldCartaID = new JTextField(20);
        labelNumeroTel = new JLabel("Numero di Telefono: ");
        textFieldNumerotel = new JTextField(20);

        buttonAnnulla = new JButtonAnnulla();
        buttonConferma = new JButtonConferma();

        //Aggiunta Elementi a Layout
        //label Nome
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 0, 5, 0);
        add(labelNome, gbc);

        //textField Nome
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 0, 5, 10);
        add(textFieldNome, gbc);

        //label Cognome
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(labelCognome, gbc);

        //textField Cognome
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 5, 10);
        add(textFieldCognome, gbc);

        //label CartaID
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(labelCartaID, gbc);

        //textField CartaID
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 5, 10);
        add(textFieldCartaID, gbc);

        //label Numero di Telefono
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(labelNumeroTel, gbc);

        //textField Numero di Telefono
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 5, 10);
        add(textFieldNumerotel, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 0);
        add(buttonAnnulla, gbc);

        //button Conferma
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 0, 10, 10);
        add(buttonConferma, gbc);
    }

    public String getTextFieldNome() {
        return textFieldNome.getText();
    }

    public String getTextFieldCognome() {
        return textFieldCognome.getText();
    }

    public String getTextFieldCartaID() {
        return textFieldCartaID.getText();
    }

    public String getTextFieldNumerotel() {
        return textFieldNumerotel.getText();
    }

    public JButton getButtonAnnulla() {
        return buttonAnnulla;
    }

    public JButton getButtonConferma() {
        return buttonConferma;
    }
}