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
        return new HashMap<>();
    }

    private boolean isCoveredByColor(Arc arc, int color) {
        // TODO: implement this
        return false;
    }

    public int getPointWithHighestNumberOfIntersections() {
        final int[] pointToCount = new int[360];

        for (int i = 0; i < 360; ++i) {
            for (Arc arc : arcs) {
                if (arc.isWithinRange(i))
                    ++pointToCount[i];
            }
        }

        return getIdOfBiggestElement(pointToCount);
    }

    private int getIdOfBiggestElement(int[] pointToNumber) {
        int maxId = -1;
        int max = -1;
        for (int i = 0; i < pointToNumber.length; ++i) {
           if (pointToNumber[i] > max) {
               maxId = i;
               max = pointToNumber[i];
           }
        }
        return maxId;
    }

    public Arc getShortestArcCounterClockwiseToThePoint(int startingPoint) {
        Arc shortestArc = null;
        int shortestLength = Integer.MAX_VALUE;

        for (Arc arc : arcs) {
            if (arc.isWithinRange(startingPoint)) {
                int length = arc.getArcLengthFromStartToPointWhichContains(startingPoint);
                if (length < shortestLength) {
                    shortestArc = arc;
                    shortestLength = length;
                }
            }
        }

        return shortestArc;
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
