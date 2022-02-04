package GUI.frameSala;

import javax.swing.*;
import java.awt.*;

public class frameSala extends JFrame {

    private mainPanelSala mainPanelSala;

    public frameSala() {
        setSize(new Dimension(780, 540));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        /*mainPanelSala = new mainPanelSala();
        add(mainPanelSala, BorderLayout.CENTER);*/

        setVisible(true);
    }
}