import org.junit.Test;

import java.util.*;

import org.hamcrest.CoreMatchers;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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

        assertEquals(0, colorMaker.getShortestArcCounterClockwiseToThePoint(point));
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

        assertEquals(1, colorMaker.getShortestArcCounterClockwiseToThePoint(point));
    }

    @Test
    public void WhenAStartingPointIsAtTheBeginningOfOneArc() throws Exception {
        final Arc expectedArc = new Arc(330,30);
        final int point = 30;
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(30, 50));
            add(expectedArc);
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        assertEquals(1, colorMaker.getShortestArcCounterClockwiseToThePoint(point));
    }

    @Test
    public void WhenAStartingPointIsAtTheBeginningOfOneArcAndThisIsTheOnlyOneArc() throws Exception {
        final Arc expectedArc = new Arc(30,60);
        final int point = 30;
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(expectedArc);
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        assertEquals(0, colorMaker.getShortestArcCounterClockwiseToThePoint(point));
    }

    @Test
    public void HavingTwoDisjointArcsTheSecondOneIsNext() throws Exception {
        final Arc first = new Arc(30, 60);
        final Arc second = new Arc(90, 120);
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(first);
            add(second);
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        assertEquals(1, colorMaker.getNextArcAfterGiven(first));
        assertEquals(0, colorMaker.getNextArcAfterGiven(second));
    }

    @Test
    public void HavingArcOverlappingAndOneStartingAfterArcsEndTheLatterIsNext() throws Exception {
        final Arc first = new Arc(30, 60);
        final Arc second = new Arc(40, 70);
        final Arc third = new Arc(90, 120);
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(first);
            add(second);
            add(third);
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        assertEquals(2, colorMaker.getNextArcAfterGiven(first));
    }

    @Test
    public void WhenThereIsOneArcItsItselfNext() throws Exception {
        final Arc first = new Arc(30, 60);
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(first);
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        assertEquals(0, colorMaker.getNextArcAfterGiven(first));
    }

    @Test
    public void WhenThereIsNoneArcsThereIsNoNextElement() throws Exception {
        final Arc arc = new Arc(30, 60);
        final List<Arc> arcs = new ArrayList<>();
        final ColorMaker colorMaker = new ColorMaker(arcs);

        assertEquals(-1, colorMaker.getNextArcAfterGiven(arc));
    }

    @Test
    public void WhenTwoArcsAreDisjointTheyHaveTheSameColor() throws Exception {
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(10, 20));
            add(new Arc(30, 40));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        final HashMap<Integer, List<Arc>> expectedColors = new HashMap<Integer, List<Arc>>() {{
            put(1, new ArrayList<Arc>() {{
                add(new Arc(10, 20, 1));
                add(new Arc(30, 40, 1));
            }});
        }};

        colorMaker.colorGraph();

        assertThat(colorMaker.getColors().entrySet(), equalTo(expectedColors.entrySet()));
    }

    @Test
    public void WhenTwoArcsHaveCommonPartTheyHaveDifferentColors() throws Exception {
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(10, 30));
            add(new Arc(20, 40));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        final List<HashMap<Integer, List<Arc>>> expectedColors = new ArrayList<HashMap<Integer, List<Arc>>>() {{
            add(new HashMap<Integer, List<Arc>>() {{
                put(1, new ArrayList<Arc>() {{
                    add(new Arc(10, 30, 1));
                }});
                put(2, new ArrayList<Arc>() {{
                    add(new Arc(20, 40, 2));
                }});
            }});
            add(new HashMap<Integer, List<Arc>>() {{
                put(1, new ArrayList<Arc>() {{
                    add(new Arc(20, 40, 1));
                }});
                put(2, new ArrayList<Arc>() {{
                    add(new Arc(10, 30, 2));
                }});
            }});
        }};

        colorMaker.colorGraph();

        assertThat(colorMaker.getColors().entrySet(), anyOf(equalTo(expectedColors.get(0).entrySet()),
                                                            equalTo(expectedColors.get(1).entrySet())));
    }

    @Test
    public void WhenFirstAndSecondArcsHaveCommonPartAndFirstAndThirdTooTheyAreColoredWithTwoColors() throws Exception {
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(10, 30));
            add(new Arc(20, 40));
            add(new Arc(5, 15));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);

        final List<HashMap<Integer, List<Arc>>> expectedColors = new ArrayList<HashMap<Integer, List<Arc>>>() {{
            add(new HashMap<Integer, List<Arc>>() {{
                put(1, new ArrayList<Arc>() {{
                    add(new Arc(10, 30, 1));
                }});
                put(2, new ArrayList<Arc>() {{
                    add(new Arc(20, 40, 2));
                    add(new Arc(5, 15, 2));
                }});
            }});
            add(new HashMap<Integer, List<Arc>>() {{
                put(1, new ArrayList<Arc>() {{
                    add(new Arc(10, 30, 1));
                }});
                put(2, new ArrayList<Arc>() {{
                    add(new Arc(5, 15, 2));
                    add(new Arc(20, 40, 2));
                }});
            }});
            add(new HashMap<Integer, List<Arc>>() {{
                put(1, new ArrayList<Arc>() {{
                    add(new Arc(20, 40, 1));
                    add(new Arc(5, 15, 1));
                }});
                put(2, new ArrayList<Arc>() {{
                    add(new Arc(10, 30, 2));
                }});
            }});
            add(new HashMap<Integer, List<Arc>>() {{
                put(1, new ArrayList<Arc>() {{
                    add(new Arc(5, 15, 1));
                    add(new Arc(20, 40, 1));
                }});
                put(2, new ArrayList<Arc>() {{
                    add(new Arc(10, 30, 2));
                }});
            }});
        }};

        colorMaker.colorGraph();

        assertThat(colorMaker.getColors().entrySet(), anyOf(equalTo(expectedColors.get(0).entrySet()),
                                                            equalTo(expectedColors.get(1).entrySet()),
                                                            equalTo(expectedColors.get(2).entrySet()),
                                                            equalTo(expectedColors.get(3).entrySet())));
    }
}
