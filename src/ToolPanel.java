import javax.swing.JLabel;
import javax.swing.JPanel;

public class ToolPanel extends JPanel {

    private Statusbar statusbar;

    public ToolPanel(Statusbar statusbar) {
        this.statusbar = statusbar;
        JLabel temp = new JLabel("This is for the tool Panel");
        this.add(temp);
    }

}
