import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage:");
            System.out.println("program_name path_to_input.txt");
            return;
        }

        Reader inputReader = new Reader(args[0]);
        List<Arc> arcs = null;
        try {
            arcs = inputReader.readArcs();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read arcs");
        }

        ColorMaker colorMaker = new ColorMaker(arcs);
        colorMaker.colorGraph();
        final Map<Integer, List<Arc>> result = colorMaker.getColors();

        for (Map.Entry<Integer, List<Arc>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


}
