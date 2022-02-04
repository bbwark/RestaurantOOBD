package GUI;

import GUI.frameRistorante.mainPanelRistorante;
import GUI.frameSala.mainPanelSala;
import GUI.frameTavolo.mainPanelTavolo;

import javax.swing.*;
import java.awt.*;

public class mainFrame extends JFrame {

    private contentPane contentPane;


    public mainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new contentPane();
        setContentPane(contentPane);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }
}