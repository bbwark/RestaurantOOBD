package GUI.loginFrame;

import javax.swing.*;

public class loginFrame extends JFrame {

    private loginFrameContentPane loginFrameContentPane;

    public loginFrame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loginFrameContentPane = new loginFrameContentPane();
        setContentPane(loginFrameContentPane);

        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public GUI.loginFrame.loginFrameContentPane getLoginFrameContentPane() {
        return loginFrameContentPane;
    }
}
