package GUI.addFrame;

import javax.swing.*;

public class addFrame extends JFrame{

    private addFrameContentPane addFrameContentPane;

    public addFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addFrameContentPane = new addFrameContentPane();
        setContentPane(addFrameContentPane);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }
}
