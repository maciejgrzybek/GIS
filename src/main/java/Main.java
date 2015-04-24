import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {

        URL resource = Main.class.getResource("data.txt");
        Path path = Paths.get(resource.getPath());

        for (Arc arc : readArcs(path)) {
            System.out.println(arc);
        }
    }

    private static List<Arc> readArcs(Path path) {
        List<Arc> arcs = new ArrayList<>();
        try(Stream<String> lines = Files.lines(path)){
            lines.forEach(p -> {
                p = p.replaceAll("\\s+","");
                String[] values = p.split(",");
                int start = Integer.parseInt(values[0]);
                int end = Integer.parseInt(values[1]);
                arcs.add(new Arc(start, end));
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arcs;
    }
}
