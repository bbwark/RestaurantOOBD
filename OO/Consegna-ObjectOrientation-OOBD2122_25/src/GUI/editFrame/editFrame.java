package GUI.editFrame;

import GUI.mainFrame.mainFrameContentPane;

import javax.swing.*;

public class editFrame extends JFrame{

    private editFrameContentPane editFrameContentPane;

    public editFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        editFrameContentPane = new editFrameContentPane();
        setContentPane(editFrameContentPane);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public GUI.editFrame.editFrameContentPane getEditFrameContentPane() {
        return editFrameContentPane;
    }
}
