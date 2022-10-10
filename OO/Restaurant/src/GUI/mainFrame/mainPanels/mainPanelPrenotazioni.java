package GUI.mainFrame.mainPanels;

import GUI.CustomButtons.JButtonGrey;
import GUI.DateLabelFormatter.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class mainPanelPrenotazioni extends JPanel {

    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private JButton buttonMostraData;
    private JButton buttonMostraAll;

    private JLabel labelListaSelezione;
    private JList listaSelezione;

    private JButton buttonIndietro;

    private JButton buttonEdit;

    public mainPanelPrenotazioni(){

        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createLineBorder(Color.black);
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Definizione Attributi
        UtilDateModel model = new UtilDateModel();
        model.setDate(2022, 0, 1);
        model.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        buttonMostraData = new JButtonGrey("Mostra prenotazioni di data selezionata");
        buttonMostraAll = new JButtonGrey("Mostra tutte le prenotazioni");

        buttonEdit = new JButtonGrey("Modifica Prenotazione Selezionata");
        labelListaSelezione = new JLabel("Lista Prenotazioni");
        listaSelezione = new JList();

        buttonIndietro = new JButtonGrey("Indietro");

        //Aggiunta Elementi a Layout
        //Label Elenco
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(5, 10, 5, 0);
        add(labelListaSelezione, gbc);

        //Lista Selezione
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(0, 10, 20, 10);
        add(new JScrollPane(listaSelezione), gbc);

        //Date Picker
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(0, 10, 0, 0);
        add(datePicker, gbc);

        //Button mostra prenotazioni data selezionata
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(20, 10, 0, 0);
        add(buttonMostraData, gbc);

        //Button mostra tutte prenotazioni
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(0, 10, 0, 0);
        add(buttonMostraAll, gbc);

        //ButtonEdit
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(20, 20, 10, 10);
        add(buttonEdit, gbc);

        //Button indietro
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(20, 10, 10, 20);
        add(buttonIndietro, gbc);
    }

    public JButton getButtonIndietro() {
        return buttonIndietro;
    }

    public JButton getButtonEdit() {
        return buttonEdit;
    }

    public JList getListaSelezione() {
        return listaSelezione;
    }

    public void setModelListaSeleziona(DefaultListModel defaultListModel){
        listaSelezione.setModel(defaultListModel);
    }

    public JButton getButtonMostraData() {
        return buttonMostraData;
    }

    public JButton getButtonMostraAll() {
        return buttonMostraAll;
    }

    public LocalDate getDate(){
        Date result = (Date) datePicker.getModel().getValue();
        return result.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
