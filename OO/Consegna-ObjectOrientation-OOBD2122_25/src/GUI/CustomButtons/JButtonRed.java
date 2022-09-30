package GUI.CustomButtons;

import javax.swing.*;
import java.awt.*;

public class JButtonRed extends JButton {
    public JButtonRed(String text){
        super(text);
        setBackground(new Color(204,50,50));
        setOpaque(true);
        setBorderPainted(false);
        setForeground(Color.white);
    }
}
