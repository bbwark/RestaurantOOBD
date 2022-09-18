import GUI.addFrame.addFrame;
import GUI.editFrame.editFrame;
import GUI.mainFrame.mainFrame;

import java.awt.*;

public class Driver {
    public static void main(String[] args){
        editFrame editFrame = new editFrame();
        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
        cardLayout.show(editFrame.getContentPane(), "Panel Camerieri");
    }
}
