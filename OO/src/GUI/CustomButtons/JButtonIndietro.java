package GUI.CustomButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonIndietro extends JButton {

    public JButtonIndietro(JPanel contentPane) {
        super("Indietro");
        setBackground(Color.lightGray);
        setOpaque(true);

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.previous(contentPane);
            }
        });
    }
}
