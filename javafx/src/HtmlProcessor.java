import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HtmlProcessor {

    public HtmlProcessor() {

    }

    public static List<String> html2List(String path) {
        List<String> output = new ArrayList<>();

        try {
            Path filePath = Paths.get(path);
            Charset charset = StandardCharsets.UTF_8;

            output = Files.readAllLines(filePath, charset);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

}
