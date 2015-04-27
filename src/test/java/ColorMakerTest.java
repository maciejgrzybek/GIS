import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ColorMakerTest {

    @Test
    public void WhenOneArcGivenThePointWithHighestNumberOfIntersectionsLaysOnThisArc() throws Exception {
        final List<Arc> arcs = new ArrayList<Arc>() {{ add(new Arc(0,50)); }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue(point >= 0 && point <= 50);
    }

    @Test
    public void WhenDisjointArcsGivenThePointWithHighestNumberOfIntersectionsCanLayOnAnyArc() throws Exception {
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(0,10));
            add(new Arc(20,30));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( (point >= 0 && point <= 10) || (point >= 20 && point <= 30));
    }

    @Test
    public void WhenTwoArcsWithCommonPartAreGivenThePointWithHighestNumberOfIntersectionsLaysOnTheirsIntersection() throws Exception {
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(0,10));
            add(new Arc(5,10));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( point >= 5 && point <= 10);
    }

    @Test
    public void WhenTwoArcsAreGivenAndIntersectionIsSinglePointThisPointIsTheOneWithHighestNumberOfIntersections() throws Exception {
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(8,10));
            add(new Arc(10,10));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( point == 10);
    }

    @Test
    public void WhenThreeArcsAreGivenWithPairwiseCommonPartsTheMostCommonPartIsChosenAsTheHighestNumberOfIntersections() throws Exception {
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(0,50));
            add(new Arc(40,150));
            add(new Arc(35,200));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( point >= 40 && point <= 50);
    }

    @Test
    public void WhenTwoArcsCrossing360DegreeAreGivenTheirCommonPartOnEachSideOfModuloPointCanBeTheHighestNumberOfIntersectionsPoint() throws Exception {
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(350,10));
            add(new Arc(355,5));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( (point >= 355 && point <= 359) || (point >= 0 && point <= 5) );
    }

    @Test
    public void WhenTwoArcsAreGivenTheOneWithTheShortestDistanceFromStartingPointToGivenPointIsChosenAsTheShortestArcCounterClockwiseToThisPoint() throws Exception {
        final Arc expectedArc = new Arc(15, 30);
        final int point = 20;
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(expectedArc);
            add(new Arc(5, 50));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        assertEquals(expectedArc, colorMaker.getShortestArcCounterClockwiseToThePoint(point));
    }
    
    @Test
    public void WhenTwoArcsAreGivenCrossing360DegreePointTheOneWithTheShortestDistanceFromStartingPointToGivenPointIsChosenAsTheShortestArcCounterClockwiseToThisPoint() throws Exception {
        final Arc expectedArc = new Arc(350,30);
        final int point = 5;
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(315, 50));
            add(expectedArc);
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        assertEquals(expectedArc, colorMaker.getShortestArcCounterClockwiseToThePoint(point));
    }
}
