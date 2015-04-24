import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by krris on 24.04.15.
 * Copyright (c) 2015 krris. All rights reserved.
 */
public class Reader {
    public static List<Arc> readArcs(String resourceFile) {
        URL resource = Main.class.getResource(resourceFile);
        Path path = Paths.get(resource.getPath());

        return readArcs(path);
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
