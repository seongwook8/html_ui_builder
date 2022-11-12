import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.JEditorPane;
import java.util.*;

// import java.awt.event.MouseMotionListener;
// import java.awt.event.*;

public class CanvasArea extends JPanel {

    private static List<double[]> points = new ArrayList<>();
    static DollarQ dollarQ = new DollarQ();

    public CanvasArea() {
        // addMouseListener(new MouseInputAdapter() {
        // public void mouseClicked(MouseEvent e) {
        // Statusbar.setStatus("Mouse Clicked");
        // DollarQ dollarQ = new DollarQ();
        // points = dollarQ.resample(points, 32);
        // repaint();
        // }
        // });

        // JLabel htmlLabel = new JLabel(htmlText);
        // Border blackline = BorderFactory.createLineBorder(Color.black);
        // htmlLabel.setBorder(blackline);
        // htmlLabel.setVerticalAlignment(SwingConstants.TOP);
        // setLayout(new BorderLayout());
        // add(htmlLabel, BorderLayout.CENTER)

        // add(jfxPanel);\

        String htmlText = "<html><head><link rel=\"stylesheet\" href=\"styles.css\"></head><body><h1>This is a heading</h1><p style=\"position: absolute; top:0px; color:red; background-color: gray; left: 20px;\">This is a paragraph.</p><h1>This is a heading</h1><h1>This is a heading</h1><h1>This is a heading</h1><h1>This is a heading</h1><h1>This is a heading</h1></body></html>";
        JFXPanel jfxPanel = new JFXPanel();

        Platform.runLater(() -> {
            WebView webView = new WebView();
            webView.getEngine().loadContent(htmlText);
            webView.setDisable(true);
            StackPane stackPane = new StackPane();
            Canvas canvas = new Canvas();
            ObservableList stacks = stackPane.getChildren();
            stacks.addAll(webView, canvas);
            jfxPanel.setScene(new Scene(stackPane));
        });
        add(jfxPanel);
        addMouseMotionListener(new MouseInputAdapter() {
            public void mouseDragged(MouseEvent e) {
                Statusbar.setStatus("Mouse Dragged");
                points.add(new double[] { (double) e.getX(), (double) e.getY(), 1 });
                repaint();

            }
        });

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < points.size(); i++) {
            int x = (int) points.get(i)[0];
            int y = (int) points.get(i)[1];
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x, y);
        }
        setBackground(Color.WHITE);

    }

    public static void clearPoints() {
        points = new ArrayList<>();
    }

    public static String detectShape() {
        return dollarQ.recognize(points);
    }

    public static void addTemplate() {
        dollarQ.addTemplate(points);
    }

}
