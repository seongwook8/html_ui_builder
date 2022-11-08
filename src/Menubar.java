import javax.swing.JMenuBar;
import javax.swing.*;
import java.awt.event.*;

public class Menubar extends JMenuBar {

    private Statusbar statusbar;

    public Menubar(Statusbar statusbar) {
        this.statusbar = statusbar;
        JMenu files = new JMenu("File");
        this.add(files);
        addSave(files);
        addLoad(files);
        addExit(files);

        JMenu edits = new JMenu("Edit");
        this.add(edits);
        addClearCanvas(edits);

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

    public void addClearCanvas(JMenu menu) {
        JMenuItem clear = new JMenuItem("Clear Canvas");
        menu.add(clear);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CanvasArea.clearPoints();
                getParent().repaint();
            }
        });

    }

}
