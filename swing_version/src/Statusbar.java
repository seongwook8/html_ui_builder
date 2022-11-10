import javax.swing.*;
import java.awt.*;

public class Statusbar extends JPanel {

    private static JLabel status;

    public Statusbar() {
        this.setLayout(new BorderLayout());
        status = new JLabel("Default Status", SwingConstants.CENTER);
        this.add(status, BorderLayout.CENTER);
    }

    public static void setStatus(String label) {
        status.setText(label);
    }

}
