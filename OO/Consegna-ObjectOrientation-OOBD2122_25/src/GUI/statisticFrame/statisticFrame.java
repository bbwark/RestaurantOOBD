package GUI.statisticFrame;

import Model.DTO.Ristorante;

import javax.swing.*;

public class statisticFrame extends JFrame {

    private statisticFrameContentPane statisticFrameContentPane;

    public statisticFrame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        statisticFrameContentPane = new statisticFrameContentPane();
        setContentPane(statisticFrameContentPane);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public GUI.statisticFrame.statisticFrameContentPane getStatisticFrameContentPane() {
        return statisticFrameContentPane;
    }

}
