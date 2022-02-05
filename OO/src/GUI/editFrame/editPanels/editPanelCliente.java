package GUI.editFrame.editPanels;

import GUI.JButtonGreen;
import GUI.JButtonGrey;
import GUI.JButtonRed;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editPanelCliente extends JPanel {

    private JLabel labelNome;
    private JLabel labelCognome;
    private JLabel labelCartaID;
    private JLabel labelNumeroTel;
    private JLabel labelNumeroPrenotazioni;

    private JTextField textFieldNome;
    private JTextField textFieldCognome;
    private JTextField textFieldCartaID;
    private JTextField textFieldNumeroTel;

    private JButton buttonElimina;
    private JButton buttonConferma;
    private JButton buttonAnnulla;

    private JButton buttonIndietro;

    public editPanelCliente(JPanel contentPane){
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Modifica Cliente");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        labelNome = new JLabel("Nome: ");
        labelCognome = new JLabel("Cognome: ");
        labelCartaID = new JLabel("Carta di Identità: ");
        labelNumeroTel = new JLabel("Numero di Telefono: ");

        textFieldNome = new JTextField(20);
        textFieldCognome = new JTextField(20);
        textFieldCartaID = new JTextField(20);
        textFieldNumeroTel=new JTextField(20);

        buttonElimina = new JButtonRed("ELIMINA Cliente");
        buttonConferma = new JButtonGreen("Conferma");
        buttonAnnulla = new JButtonGrey("Annulla");

        buttonIndietro=new JButtonGrey("Indietro");

        buttonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "Panel Prenotazioni");
            }
        });

        //label Nome
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(labelNome, gbc);

        //textField Nome
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        add(textFieldNome, gbc);

        //label Cognome
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(labelCognome, gbc);

        //textField Cognome
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        add(textFieldCognome, gbc);

        //label Carta ID
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(labelCartaID, gbc);

        //textField Carta ID
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        add(textFieldCartaID, gbc);

        //label Numero di Telefono
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(labelNumeroTel, gbc);

        //textField Numero di Telefono
        gbc = new GridBagConstraints();
        gbc.gridx=1;
        gbc.gridy=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        add(textFieldNumeroTel, gbc);

        //button Indietro
        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(15,0,10,0);
        add(buttonIndietro, gbc);

        //button Elimina
        gbc = new GridBagConstraints();
        gbc.gridx= 2;
        gbc.gridy=0;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets=new Insets(10,10,0,10);
        add(buttonElimina, gbc);

        //button Annulla
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=2;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets=new Insets(0,10,0,10);
        add(buttonAnnulla, gbc);

        //button Conferma
        gbc = new GridBagConstraints();
        gbc.gridx=2;
        gbc.gridy=3;
        gbc.weightx=0.0;
        gbc.weighty=0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets=new Insets(0,10,10,10);
        add(buttonConferma, gbc);
    }
}
