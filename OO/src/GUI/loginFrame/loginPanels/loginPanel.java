package GUI.loginFrame.loginPanels;

import GUI.CustomButtons.JButtonAnnulla;
import GUI.CustomButtons.JButtonConferma;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class loginPanel extends JPanel {

    private JLabel labelHost;
    private JTextField textFieldHost;
    private JLabel labelPort;
    private JTextField textFieldPort;
    private JLabel labelDatabaseName;
    private JTextField textFieldDatabaseName;
    private JLabel labelUsername;
    private JTextField textFieldUsername;
    private JLabel labelPassword;
    private JTextField textFieldPassword;

    private JButton buttonAnnulla;
    private JButton buttonConferma;

    public loginPanel() {
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Login");
        Border bordoEsterno = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        labelHost = new JLabel("Host: ");
        textFieldHost = new JTextField(20);
        labelPort = new JLabel("Port: ");
        textFieldPort = new JTextField(20);
        labelDatabaseName = new JLabel("Database Name: ");
        textFieldDatabaseName = new JTextField(20);
        labelUsername = new JLabel("Username: ");
        textFieldUsername = new JTextField(20);
        labelPassword = new JLabel("Password: ");
        textFieldPassword = new JTextField(20);

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
        add(labelHost, gbc);

        //textField Nome
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 0, 5, 10);
        add(textFieldHost, gbc);

        //label Cognome
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(labelPort, gbc);

        //textField Cognome
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 5, 10);
        add(textFieldPort, gbc);

        //label CartaID
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(labelDatabaseName, gbc);

        //textField CartaID
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 5, 10);
        add(textFieldDatabaseName, gbc);

        //label Numero di Telefono
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(labelUsername, gbc);

        //textField Numero di Telefono
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 5, 10);
        add(textFieldUsername, gbc);

        //label password
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(labelPassword, gbc);

        //textField Password
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 0, 5, 10);
        add(textFieldPassword, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 0);
        add(buttonAnnulla, gbc);

        //button Conferma
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 0, 10, 10);
        add(buttonConferma, gbc);
    }

    public String getTextFieldHost() {
        return textFieldHost.getText();
    }

    public String getTextFieldPort() {
        return textFieldPort.getText();
    }

    public String getTextFieldDatabaseName() {
        return textFieldDatabaseName.getText();
    }

    public String getTextFieldUsername() {
        return textFieldUsername.getText();
    }

    public String getTextFieldPassword() {
        return textFieldPassword.getText();
    }

    public void setTextFieldHost(String string) {
        this.textFieldHost.setText(string);
    }

    public void setTextFieldPort(String string) {
        this.textFieldPort.setText(string);
    }

    public void setTextFieldDatabaseName(String string) {
        this.textFieldDatabaseName.setText(string);
    }

    public void setTextFieldUsername(String string) {
        this.textFieldUsername.setText(string);
    }

    public void setTextFieldPassword(String string) {
        this.textFieldPassword.setText(string);
    }

    public JButton getButtonAnnulla() {
        return buttonAnnulla;
    }

    public JButton getButtonConferma() {
        return buttonConferma;
    }
}