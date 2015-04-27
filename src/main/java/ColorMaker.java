import java.util.*;
import java.util.stream.Collectors;

public class ColorMaker {
    public ColorMaker(List<Arc> arcs) {
        this.arcs = arcs;
    }

    public void colorGraph() {
        Collections.sort(arcs);

        final int startingPoint = getPointWithHighestNumberOfIntersections();
        final int startingArcIdx = getShortestArcCounterClockwiseToThePoint(startingPoint);

        indexArcs(startingArcIdx);

        for (int i = 0; i < indexedArcs.size(); ++i) {
            final Arc currentArc = indexedArcs.get(i);
            if (existsColoredArcBeforeArcsEnding(i, currentColor))
                ++currentColor;
            colorArc(currentArc);
        }
    }

    private void indexArcs(int startingArcIdx) {
        final int totalNumberOfArcs = arcs.size();
        indexedArcs = new ArrayList<>();
        while (indexedArcs.size() < totalNumberOfArcs) {
            final Arc arc = arcs.get(startingArcIdx);
            indexedArcs.add(arc);
            arcs.remove(startingArcIdx);
            startingArcIdx = getNextArcAfterGiven(arc);
        }
    }

    public int getNextArcAfterGiven(Arc currentArc) {
        if (arcs.size() == 0)
            return -1;

        int nextArcIdx = Collections.binarySearch(arcs, new Arc(currentArc.getEnd()+1, -1));
        if (nextArcIdx < 0)
            nextArcIdx = -(nextArcIdx + 1);

        if (nextArcIdx == arcs.size())
            nextArcIdx = 0;

        return nextArcIdx;
    }

    private boolean existsColoredArcBeforeArcsEnding(int arcIdx, int color) {
        if (arcIdx <= 0)
            return false;
        final List<Arc> x = indexedArcs.stream().limit(arcIdx).collect(Collectors.toList());
        final List<Arc> result = x.stream().filter(a -> a.isColored() && a.getColor() == color && a.intersects(indexedArcs.get(arcIdx))).collect(Collectors.toList());
        return result.size() > 0;
    }

    public Map<Integer,List<Arc>> getColors() {
        return colors;
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

    public int getShortestArcCounterClockwiseToThePoint(int startingPoint) {
        int shortestArcIdx = -1;
        int arcWithZeroLengthFromStartPointIdx = -1;
        int shortestLength = Integer.MAX_VALUE;

        for (int i = 0; i < arcs.size(); ++i) {
            final Arc arc = arcs.get(i);
            if (!arc.isWithinRange(startingPoint))
                continue;
            final int length = arc.getArcLengthFromStartToPointWhichContains(startingPoint);
            if (length == 0) {
                arcWithZeroLengthFromStartPointIdx = i;
            } else if (length < shortestLength) {
                shortestArcIdx = i;
                shortestLength = length;
            }
        }

        if (shortestArcIdx == -1) {
            return arcWithZeroLengthFromStartPointIdx;
        }
        return shortestArcIdx;
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


    private List<Arc> indexedArcs;
    private final List<Arc> arcs;
    private final Map<Integer, List<Arc>> colors = new HashMap<>();
    private int currentColor = 1;
    private int numberOfColoredArcs = 0;
}
