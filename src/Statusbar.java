import javax.swing.*;
import java.awt.*;

public class Statusbar extends JPanel {

    private JLabel status;

    public Statusbar() {
        this.setLayout(new BorderLayout());
        this.status = new JLabel("Default Status", SwingConstants.CENTER);
        this.add(status, BorderLayout.CENTER);
    }

    public void setStatus(String label) {
        status.setText(label);
        repaint();
    }

}
