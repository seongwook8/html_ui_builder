
import java.util.List;

import javafx.scene.layout.Pane;

public class CanvasProcessor {
    private DollarQ dollarq;

    public CanvasProcessor() {
        this.dollarq = new DollarQ();

    }

    public HtmlRect addShape(Pane canvas, List<double[]> points, HubController controller) {
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

        maxX = Math.max(maxX - minX, 50) + minX;
        maxY = Math.max(maxY - minY, 50) + minY;

        HtmlRect rect = new HtmlRect(minX, minY, maxX - minX, maxY - minY, shape, controller);

        rect.setNumElements(3);
        canvas.getChildren().add(rect);

        return rect;

    }

}
