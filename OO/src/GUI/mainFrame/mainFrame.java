package GUI.mainFrame;

import javax.swing.*;

public class mainFrame extends JFrame {

    private mainFrameContentPane mainFrameContentPane;

    public mainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrameContentPane = new mainFrameContentPane();
        setContentPane(mainFrameContentPane);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public GUI.mainFrame.mainFrameContentPane getMainFrameContentPane() {
        return mainFrameContentPane;
    }
}