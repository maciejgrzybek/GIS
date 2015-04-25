import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ColorMakerTest {

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith1ArcAnd1Load(){
        final List<Arc> arcs = new ArrayList<Arc>() {{ add(new Arc(0,50)); }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue(point >=0 && point <=50);
    }

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith2ArcsAnd1Load(){
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(0,10));
            add(new Arc(20,30));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( (point >= 0 && point <= 10) || (point >= 20 && point <= 30));
    }

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith2ArcsAnd2Load(){
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(0,10));
            add(new Arc(5,10));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( point >= 5 && point <= 10);
    }

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith2ArcsAnd2LoadVersion2(){
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(8,10));
            add(new Arc(9,10));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( point == 10);
    }

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith3ArcsAnd1Load(){
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
    public void getPointWithHighestNumberOfIntersectionsWith2ArcsAnd1LoadModulo(){
        final List<Arc> arcs = new ArrayList<Arc>() {{
            add(new Arc(350,10));
            add(new Arc(355,5));
        }};
        final ColorMaker colorMaker = new ColorMaker(arcs);
        final int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( (point >= 355 && point <= 359) || (point >= 0 && point <= 5) );
    }
}
