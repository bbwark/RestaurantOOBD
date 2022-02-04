package GUI.frameTavolo;

import javax.swing.*;
import java.awt.*;

public class frameTavolo extends JFrame {

    private mainPanelTavolo mainPanelTavolo;

    public frameTavolo(){
        setSize(new Dimension(780, 540));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        /*mainPanelTavolo = new mainPanelTavolo();
        add(mainPanelTavolo, BorderLayout.CENTER);*/

        setVisible(true);
    }
}
