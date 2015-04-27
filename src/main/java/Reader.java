import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Reader {
    public Reader(String path) {
        this.path = Paths.get(path);
    }

    public Reader(Path path) {
        this.path = path;
    }

    public List<Arc> readArcs() throws IOException {
        final List<Arc> arcs = new ArrayList<>();
        Stream<String> lines = Files.lines(path);
        lines.forEach(p -> {
            p = p.replaceAll("\\s+","");
            String[] values = p.split(",");
            final int start = Integer.parseInt(values[0]);
            final int end = Integer.parseInt(values[1]);
            arcs.add(new Arc(start, end));
        });
        return arcs;
    }

    private final Path path;
}
