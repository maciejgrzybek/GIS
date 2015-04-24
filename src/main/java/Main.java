import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {

        final List<Arc> arcs = Reader.readArcs("data.txt");
        System.out.println(arcs);

        ColorMaker colorMaker = new ColorMaker(arcs);
        colorMaker.colorGraph();
        final Map<Integer, List<Arc>> result = colorMaker.getColors();

        for (Map.Entry<Integer, List<Arc>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


}
