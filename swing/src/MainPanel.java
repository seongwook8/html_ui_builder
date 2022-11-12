
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.*;

public class MainPanel extends JPanel {
    private CanvasArea canvasArea;

    public MainPanel() {
        super(new BorderLayout());
        canvasArea = new CanvasArea();
        canvasArea.setMinimumSize(new Dimension(300, 30));

        HtmlArea htmlArea = new HtmlArea();
        htmlArea.setMinimumSize(new Dimension(300, 30));
        trimHtml(htmlArea.getText());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvasArea, htmlArea);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);

        add(splitPane, BorderLayout.CENTER);

    }

    public String trimHtml(String text) {
        int start = text.indexOf("<html>");
        // System.out.println(text.substring(start).replaceAll(">\\s+<",
        // "><").replaceAll("<!--.*?-->", ""));

        return "";
    }

    public CanvasArea getCanvasArea() {
        return canvasArea;
    }

}
