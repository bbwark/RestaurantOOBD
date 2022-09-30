package GUI.CustomButtons;

import javax.swing.*;
import java.awt.*;

public class JButtonGreen extends JButton {
    public JButtonGreen(String text){
        super(text);
        setBackground(new Color(50,204,50));
        setOpaque(true);
        setBorderPainted(false);
        setForeground(Color.white);
    }
}
