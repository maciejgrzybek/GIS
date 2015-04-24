import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {

        URL resource = Main.class.getResource("data.txt");
        String pathStr = URLDecoder.decode(resource.getPath(), "UTF-8");
        Path path = Paths.get(new File(pathStr).getPath());

        final List<Arc> arcs = readArcs(path);

        ColorMaker colorMaker = new ColorMaker(arcs);
        colorMaker.colorGraph();
        final Map<Integer, List<Arc>> result = colorMaker.getColors();

        for (Map.Entry<Integer, List<Arc>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
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
