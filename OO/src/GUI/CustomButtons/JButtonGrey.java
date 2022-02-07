package GUI.CustomButtons;

import javax.swing.*;
import java.awt.*;

public class JButtonGrey extends JButton {
    public JButtonGrey(String text){
        super(text);
        setBackground(Color.lightGray);
        setOpaque(true);
    }
}
