package GUI.statisticFrame.statisticPanels;

import GUI.DateLabelFormatter.DateLabelFormatter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class statisticPanelRistorante extends JPanel {

    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;

    private JRadioButton radioSettimana;
    private JRadioButton radioMese;
    private JRadioButton radioAnno;
    private ButtonGroup dataRadioGroup;

    private JLabel avventoriMedi;
    private JLabel numeroAvventoriMedi;
    private JLabel crescitaAvventoriMedi;

    private JLabel avventoriTotali;
    private JLabel numeroAvventoriTotali;
    private JLabel crescitaAvventoriTotali;

    private DefaultCategoryDataset avventoriDataset;
    private JFreeChart avventoriLineChart;
    private ChartPanel chartPanel;


    public statisticPanelRistorante(){
        setLayout(new GridBagLayout());

        Border bordoInterno = BorderFactory.createTitledBorder("Statistiche Ristorante");
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Dichiarazione Componenti
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        avventoriDataset = new DefaultCategoryDataset();

        //ESEMPIO DATASET
        //avventoriDataset.addValue(100, "normal", "gino");
        //avventoriDataset.addValue(120, "normal", "pino");
        //avventoriDataset.addValue(80, "normal", "lino");

        avventoriLineChart = ChartFactory.createLineChart("", "Periodo", "Numero Avventori", avventoriDataset, PlotOrientation.VERTICAL, false, false, false);
        chartPanel = new ChartPanel(avventoriLineChart);


        radioSettimana = new JRadioButton("Settimana");
        radioMese = new JRadioButton("Mese");
        radioAnno = new JRadioButton("Anno");
        dataRadioGroup = new ButtonGroup();
        dataRadioGroup.add(radioSettimana);
        dataRadioGroup.add(radioMese);
        dataRadioGroup.add(radioAnno);

        avventoriMedi = new JLabel("Avventori Medi:");
        numeroAvventoriMedi = new JLabel("");
        crescitaAvventoriMedi = new JLabel("%");

        avventoriTotali = new JLabel("Avventori Totali:");
        numeroAvventoriTotali = new JLabel("");
        crescitaAvventoriTotali = new JLabel("%");

        //Aggiunta Elementi a Layout
        //Date Picker
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(10, 65, 5, 0);
        add(datePicker, gbc);

        //Line Chart
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridwidth = 3;
        gbc.gridheight = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 0, 0);
        add(chartPanel, gbc);

        //Settimana radio button
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(40, 0, 0, 0);
        add(radioSettimana, gbc);

        //Mese radio button
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(radioMese, gbc);

        //Anno radio button
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(radioAnno, gbc);

        //Avventori Medi label
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(110, 40, 5,40);
        add(avventoriMedi, gbc);

        //numero Avventori Medi label
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(5, 40, 5,0);
        add(numeroAvventoriMedi, gbc);

        //crescita Avventori Medi label
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(5, 0, 5,40);
        add(crescitaAvventoriMedi, gbc);

        //Avventori Totali label
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(70, 40, 5,40);
        add(avventoriTotali, gbc);

        //numero Avventori Totali label
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(5, 40, 5,0);
        add(numeroAvventoriTotali, gbc);

        //crescita Avventori Totali label
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(5, 0, 5,40);
        add(crescitaAvventoriTotali, gbc);
    }

    public LocalDate getDate(){
        Date result = (Date) datePicker.getModel().getValue();
        if(result != null)
            return result.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return null;
    }

    public void setDate(LocalDate localDate){
        datePicker.getModel().setDate(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
        datePicker.getModel().setSelected(true);
    }

    public JRadioButton getRadioSettimana() {
        return radioSettimana;
    }

    public JRadioButton getRadioMese() {
        return radioMese;
    }

    public JRadioButton getRadioAnno() {
        return radioAnno;
    }

    public void setAvventoriLineChart(String Xaxis, String Yaxis, DefaultCategoryDataset defaultCategoryDataset) {
        this.avventoriLineChart = ChartFactory.createLineChart("", Xaxis, Yaxis, defaultCategoryDataset, PlotOrientation.VERTICAL, false, false, false);
        chartPanel = new ChartPanel(avventoriLineChart);
    }

    public void setNumeroAvventoriMedi(String string) {
        this.numeroAvventoriMedi.setText(string);
    }

    public void setCrescitaAvventoriMedi(String string) {
        this.crescitaAvventoriMedi.setText(string);
    }

    public void setNumeroAvventoriTotali(String string) {
        this.numeroAvventoriTotali.setText(string);
    }

    public void setCrescitaAvventoriTotali(String string) {
        this.crescitaAvventoriTotali.setText(string);
    }

    public DefaultCategoryDataset getAvventoriDataset() {
        return avventoriDataset;
    }

    public void setAvventoriDataset(DefaultCategoryDataset avventoriDataset) {
        this.avventoriDataset = avventoriDataset;
    }
}

























