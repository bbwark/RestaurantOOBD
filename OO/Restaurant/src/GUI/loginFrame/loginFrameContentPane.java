package GUI.loginFrame;

import GUI.loginFrame.loginPanels.loginPanel;

import javax.swing.*;
import java.awt.*;

public class loginFrameContentPane extends JPanel {

    private loginPanel loginPanel;

    public loginFrameContentPane (){
        setBorder(
                BorderFactory.createEmptyBorder(3,3,3,3));
        setLayout(new CardLayout());

        loginPanel = new loginPanel();

        add(loginPanel, "Panel Login");
    }

    public GUI.loginFrame.loginPanels.loginPanel getLoginPanel() {
        return loginPanel;
    }
}
