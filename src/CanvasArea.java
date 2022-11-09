import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.*;
// import java.awt.event.MouseMotionListener;
// import java.awt.event.*;

public class CanvasArea extends JPanel {

    private static List<double[]> points = new ArrayList<>();

    public CanvasArea() {
        // addMouseListener(new MouseInputAdapter() {
        // public void mouseClicked(MouseEvent e) {
        // Statusbar.setStatus("Mouse Clicked");
        // DollarQ dollarQ = new DollarQ();
        // points = dollarQ.resample(points, 32);
        // repaint();
        // }
        // });

        addMouseMotionListener(new MouseInputAdapter() {
            public void mouseDragged(MouseEvent e) {
                Statusbar.setStatus("Mouse Dragged");
                points.add(new double[] { (double) e.getX(), (double) e.getY(), 0 });
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

}
