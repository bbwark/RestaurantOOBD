package GUI.statisticFrame.statisticPanels;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class statisticPanelRistorante extends JPanel {

    private JLabel labelData;
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

    private JFreeChart avventoriLineChart;
    private DefaultCategoryDataset avventoriDataset;


    public statisticPanelRistorante(JPanel contentPane){
        setLayout(new GridBagLayout());

        String nomeRistorante = "*NOME*";
        Border bordoInterno = BorderFactory.createTitledBorder("Statistiche Ristorante " + nomeRistorante);
        Border bordoEsterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        //Dichiarazione Componenti
        labelData = new JLabel("Data: ");
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        //avventoriDataset = CREARE/PASSARE UN DATASET
        avventoriDataset = new DefaultCategoryDataset();

        //ESEMPIO DATASET
        //avventoriDataset.addValue(100, "normal", "gino");
        //avventoriDataset.addValue(120, "normal", "pino");
        //avventoriDataset.addValue(80, "normal", "lino");

        avventoriLineChart = ChartFactory.createLineChart("", "Periodo", "Numero Avventori", avventoriDataset, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel chartPanel = new ChartPanel(avventoriLineChart);


        radioSettimana = new JRadioButton("Settimana");
        radioMese = new JRadioButton("Mese");
        radioAnno = new JRadioButton("Anno");
        dataRadioGroup = new ButtonGroup();
        dataRadioGroup.add(radioSettimana);
        dataRadioGroup.add(radioMese);
        dataRadioGroup.add(radioAnno);

        avventoriMedi = new JLabel("Avventori Medi:");
        int numAvventoriMed = 0;
        numeroAvventoriMedi = new JLabel(Integer.toString(numAvventoriMed));
        int numCrescitaAvventoriMed = 0;
        crescitaAvventoriMedi = new JLabel(Integer.toString(numCrescitaAvventoriMed) + "%");

        avventoriTotali = new JLabel("Avventori Totali:");
        int numAvventoriTot = 0;
        numeroAvventoriTotali = new JLabel(Integer.toString(numAvventoriTot));
        int numCrescitaAvventoriTot = 0;
        crescitaAvventoriTotali = new JLabel(Integer.toString(numCrescitaAvventoriTot) + "%");


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
        gbc.gridwidth = 2;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 0, 0);
        add(chartPanel, gbc);

        //Settimana radio button
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 60, 0, 10);
        add(radioSettimana, gbc);

        //Mese radio button
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 60, 0, 10);
        add(radioMese, gbc);

        //Anno radio button
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 60, 5, 10);
        add(radioAnno, gbc);

        //Avventori Medi label
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(110, 100, 5,40);
        add(avventoriMedi, gbc);

        //numero Avventori Medi label
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(5, 100, 5,0);
        add(numeroAvventoriMedi, gbc);

        //crescita Avventori Medi label
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(5, 0, 5,40);
        add(crescitaAvventoriMedi, gbc);


        //Avventori Totali label
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(70, 100, 5,40);
        add(avventoriTotali, gbc);

        //numero Avventori Totali label
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(5, 100, 5,0);
        add(numeroAvventoriTotali, gbc);

        //crescita Avventori Totali label
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        gbc.insets = new Insets(5, 0, 5,40);
        add(crescitaAvventoriTotali, gbc);
    }
}
























