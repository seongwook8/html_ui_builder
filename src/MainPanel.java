import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        super(new BorderLayout());
        CanvasArea canvasArea = new CanvasArea();
        canvasArea.setMinimumSize(new Dimension(300, 30));

        HtmlArea htmlArea = new HtmlArea();
        htmlArea.setMinimumSize(new Dimension(300, 30));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvasArea, htmlArea);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);

        add(splitPane, BorderLayout.CENTER);

    }

}
