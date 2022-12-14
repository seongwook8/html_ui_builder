import javax.swing.JMenuBar;
import javax.swing.*;
import java.awt.event.*;

public class Menubar extends JMenuBar {

    public Menubar() {
        JMenu files = new JMenu("File");
        this.add(files);
        addSave(files);
        addLoad(files);
        addExit(files);

        JMenu edits = new JMenu("Edit");
        this.add(edits);
        addClearCanvas(edits);
        addDetectShape(edits);
        addAddTemplate(edits);

    }

    public void addSave(JMenu menu) {
        JMenuItem save = new JMenuItem("Save");
        menu.add(save);
        save.addActionListener(e -> Statusbar.setStatus("Current Layout is being Saved..."));

    }

    public void addLoad(JMenu menu) {
        JMenuItem load = new JMenuItem("Load");
        menu.add(load);
        load.addActionListener(e -> Statusbar.setStatus("Saved Layout is being Loaded..."));
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
                Statusbar.setStatus("Canvas is Cleared!");
                getParent().repaint();
            }
        });

    }

    public void addDetectShape(JMenu menu) {
        JMenuItem detectShape = new JMenuItem("Detect Shape");
        menu.add(detectShape);
        detectShape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String detectedShape = CanvasArea.detectShape();
                Statusbar.setStatus(detectedShape + " Detected!");
                CanvasArea.clearPoints();
                getParent().repaint();
            }
        });
    }

    public void addAddTemplate(JMenu menu) {
        JMenuItem addTemplate = new JMenuItem("Add Shape");
        menu.add(addTemplate);
        addTemplate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CanvasArea.addTemplate();
                Statusbar.setStatus("Shape Added!");
                CanvasArea.clearPoints();
                getParent().repaint();
            }
        });
    }

}
