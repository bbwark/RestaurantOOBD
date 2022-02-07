package GUI.CustomButtons;

import javax.swing.*;
import java.awt.*;

public class JButtonRed extends JButton {
    public JButtonRed(String text){
        super(text);
        setBackground(new Color(204,0,0));
        setOpaque(true);
        setForeground(Color.white);
    }
}
