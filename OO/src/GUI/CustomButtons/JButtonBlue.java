package GUI.CustomButtons;

import javax.swing.*;
import java.awt.*;

public class JButtonBlue extends JButton {
    public JButtonBlue(String text){
        super(text);
        setBackground(new Color(0,0,204));
        setOpaque(true);
        setForeground(Color.white);
    }
}
