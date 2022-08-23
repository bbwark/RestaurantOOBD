package GUI.statisticFrame;

import javax.swing.*;

public class statisticFrame extends JFrame {

    private statisticFrameContentPane statisticFrameContentPane;

    public statisticFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        statisticFrameContentPane = new statisticFrameContentPane();
        setContentPane(statisticFrameContentPane);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }
}
