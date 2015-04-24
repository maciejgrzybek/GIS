import java.util.*;

public class ColorMaker {
    public ColorMaker(List<Arc> arcs) {
        this.arcs = arcs;
    }

    public void colorGraph() {
        Collections.sort(arcs);

        final int startingPoint = getPointWithHighestNumberOfIntersections();
        final Arc startingArc = getShortestArcCounterClockwiseToThePoint(startingPoint);

        colorArc(startingArc);

        Arc currentArc = startingArc;
        while (numberOfColoredArcs < arcs.size()) {
            int nextArcIdx = Collections.binarySearch(arcs, new Arc(currentArc.getEnd()+1, -1));
            if (nextArcIdx == arcs.size())
                nextArcIdx = 0;
            currentArc = arcs.get(nextArcIdx);

            if (isCoveredByColor(currentArc, currentColor))
                ++currentColor;

            colorArc(currentArc);
        }
    }

    public Map<Integer,List<Arc>> getColors() {
        return new HashMap<Integer, List<Arc>>();
    }

    private boolean isCoveredByColor(Arc arc, int color) {
        // TODO: implement this
        return false;
    }

    public int getPointWithHighestNumberOfIntersections() {
        List<Integer> pointToNumber = new ArrayList<>();
        for (int i = 0; i < 360; ++i) {
            pointToNumber.add(0);
        }

        for (int i = 0; i < 360; ++i) {
            for (Arc arc : arcs) {
                if (arc.isWithinRange(i))
                    pointToNumber.set(i, pointToNumber.get(i) + 1);
            }
        }

        int maxId = -1;
        int max = -1;
        for (int i = 0; i < pointToNumber.size(); i++) {
           if (pointToNumber.get(i) > max) {
               maxId = i;
               max = pointToNumber.get(i);
           }
        }

        return maxId;
    }

    private Arc getShortestArcCounterClockwiseToThePoint(int startingPoint) {
        // TODO: implement this
        return null;
    }

    private void colorArc(Arc arc) {
        arc.color(currentColor);
        List<Arc> currentArcs = colors.get(currentColor);

        if (currentArcs == null) {
            currentArcs = new ArrayList<Arc>();
            colors.put(currentColor, currentArcs);
        }
        currentArcs.add(arc);

        ++numberOfColoredArcs;
    }


    private final List<Arc> arcs;
    private final Map<Integer, List<Arc>> colors = new HashMap<Integer, List<Arc>>();
    private int currentColor = 1;
    private int numberOfColoredArcs = 0;
}
