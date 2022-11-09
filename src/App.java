import javax.swing.*;
import java.awt.*;

public class App {

    private int frameMinWidth = 960;
    private int frameMinHeight = 540;

    public App() {

        // create the main frame
        JFrame mainFrame = new JFrame("HUB - HTML UI Builder");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(frameMinWidth, frameMinHeight));

        Menubar menubar = new Menubar();
        ToolPanel toolPanel = new ToolPanel();
        MainPanel mainPanel = new MainPanel();
        Statusbar statusbar = new Statusbar();

        mainFrame.setJMenuBar(menubar);
        mainFrame.getContentPane().add(toolPanel, BorderLayout.NORTH);
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainFrame.getContentPane().add(statusbar, BorderLayout.SOUTH);

        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App();
            }
        });
    }
}
