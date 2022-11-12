import javax.swing.*;
import java.awt.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class App extends Application {

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
        // addJfxPanel(mainPanel);

        mainFrame.setJMenuBar(menubar);
        mainFrame.getContentPane().add(toolPanel, BorderLayout.NORTH);
        mainFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainFrame.getContentPane().add(statusbar, BorderLayout.SOUTH);

        // mainFrame.pack();
        // mainFrame.setVisible(true);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        StackPane root = new StackPane();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    public void addJfxPanel(MainPanel mainPanel) {

        String htmlText = "<html><head><link rel=\"stylesheet\" href=\"styles.css\"></head><body><h1>This is a heading</h1><p style=\"color:red; background-color: gray;\">This is a paragraph.</p><h1>This is a heading</h1><h1>This is a heading</h1><h1>This is a heading</h1><h1>This is a heading</h1><h1>This is a heading</h1></body></html>";
        JFXPanel jfxPanel = new JFXPanel();
        Platform.runLater(() -> {
            WebView webView = new WebView();
            webView.getEngine().loadContent(htmlText);
            // webView.getEngine().load("http://www.stackoverflow.com/");
            jfxPanel.setScene(new Scene(webView));
        });
        mainPanel.getCanvasArea().add(jfxPanel);
    }

    public static void main(String[] args) throws Exception {
        // javax.swing.SwingUtilities.invokeLater(new Runnable() {
        // public void run() {
        // new App();
        // }
        // });
        launch(args);
    }
}
