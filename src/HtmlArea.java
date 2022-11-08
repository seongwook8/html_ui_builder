import java.awt.Color;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class HtmlArea extends JTextPane {

    public HtmlArea() {
        List<String> htmlList = getDefaultHtml();
        printHtml(htmlList);

    }

    public List<String> getDefaultHtml() {
        List<String> output = new ArrayList<>();

        try {
            Path path = Paths.get("src/default.html");
            Charset charset = StandardCharsets.UTF_8;

            output = Files.readAllLines(path, charset);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public void printHtml(List<String> htmlList) {
        StyledDocument doc = this.getStyledDocument();
        Style style = this.addStyle("default style", null);
        Color comment = Color.lightGray;
        Color regularText = Color.black;

        for (String line : htmlList) {
            String trimmed = line.trim();
            if (trimmed.startsWith("<!--")) {
                StyleConstants.setForeground(style, comment);
            }
            try {
                doc.insertString(doc.getLength(), line, style);
                doc.insertString(doc.getLength(), "\n", style);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (line.endsWith("-->")) {
                StyleConstants.setForeground(style, regularText);
            }

        }
    }

}
