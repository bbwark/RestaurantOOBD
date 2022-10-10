package GUI.statisticFrame;

import GUI.statisticFrame.statisticPanels.statisticPanelRistorante;
import Model.DTO.Ristorante;

import javax.swing.*;
import java.awt.*;

public class statisticFrameContentPane extends JPanel {

    private statisticPanelRistorante statisticPanelRistorante;

    public statisticFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        statisticPanelRistorante = new statisticPanelRistorante();

        add(statisticPanelRistorante, "Panel Ristorante");
    }

    public GUI.statisticFrame.statisticPanels.statisticPanelRistorante getStatisticPanelRistorante() {
        return statisticPanelRistorante;
    }
}
