package GUI.frameRistorante;

import javax.swing.*;
import java.awt.*;

public class frameRistorante extends JFrame {

    private mainPanelRistorante mainPanelRistorante;

    public frameRistorante() {
        setSize(new Dimension(760, 540));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);
        setLayout(new BorderLayout());

        mainPanelRistorante = new mainPanelRistorante();
        add(mainPanelRistorante, BorderLayout.CENTER);

        setVisible(true);
    }
}