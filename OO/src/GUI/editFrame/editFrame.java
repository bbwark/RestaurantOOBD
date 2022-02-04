package GUI.editFrame;

import GUI.mainFrame.mainFrameContentPane;

import javax.swing.*;

public class editFrame extends JFrame{

    private GUI.mainFrame.mainFrameContentPane mainFrameContentPane;

    public editFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrameContentPane = new mainFrameContentPane();
        setContentPane(mainFrameContentPane);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }
}
