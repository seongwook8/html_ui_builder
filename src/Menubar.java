import javax.swing.JMenuBar;
import javax.swing.*;

public class Menubar extends JMenuBar {

    private Statusbar statusbar;

    public Menubar(Statusbar statusbar) {
        this.statusbar = statusbar;
        JMenu files = new JMenu("File");
        this.add(files);
        addSave(files);
        addLoad(files);
        addExit(files);

    }

    public void addSave(JMenu menu) {
        JMenuItem save = new JMenuItem("Save");
        menu.add(save);
        save.addActionListener(e -> this.statusbar.setStatus("Current Layout is being Saved..."));

    }

    public void addLoad(JMenu menu) {
        JMenuItem load = new JMenuItem("Load");
        menu.add(load);
        load.addActionListener(e -> this.statusbar.setStatus("Saved Layout is being Loaded..."));
    }

    public void addExit(JMenu menu) {
        JMenuItem exit = new JMenuItem("Exit");
        menu.add(exit);
        exit.addActionListener(e -> System.exit(0));
    }

}
