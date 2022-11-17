
import java.util.List;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class CanvasProcessor {
    private DollarQ dollarq;

    public CanvasProcessor() {
        this.dollarq = new DollarQ();

    }

    public HtmlRect addShape(Pane canvas, List<double[]> points, TextArea html_code) {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (double[] point : points) {
            minX = Math.min(minX, point[0]);
            minY = Math.min(minY, point[1]);
            maxX = Math.max(maxX, point[0]);
            maxY = Math.max(maxY, point[1]);
        }

        String shape = dollarq.recognize(points);

        HtmlRect rect = new HtmlRect(minX, minY, maxX - minX, maxY - minY, shape, html_code);

        return rect;

    }

}
