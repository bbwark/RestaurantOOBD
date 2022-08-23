package GUI.statisticFrame;

import GUI.statisticFrame.statisticPanels.statisticPanelRistorante;

import javax.swing.*;
import java.awt.*;

public class statisticFrameContentPane extends JPanel {

    private statisticPanelRistorante statisticPanelRistorante;

    public statisticFrameContentPane(){
        setBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setLayout(new CardLayout());


        statisticPanelRistorante = new statisticPanelRistorante(this);

        add(statisticPanelRistorante, "Panel Ristorante");
    }
}
