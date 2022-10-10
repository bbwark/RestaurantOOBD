package GUI.addFrame;

import javax.swing.*;
import java.awt.*;

public class addFrame extends JFrame{

    private addFrameContentPane addFrameContentPane;

    public addFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addFrameContentPane = new addFrameContentPane();
        setContentPane(addFrameContentPane);

        setPreferredSize(new Dimension(500,300));
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public GUI.addFrame.addFrameContentPane getAddFrameContentPane() {
        return addFrameContentPane;
    }
}
