import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ColorMakerTest {

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith1ArcAnd1Load(){
        List<Arc> arcs = Reader.readArcs("max_load/1arc-1load.txt");
        ColorMaker colorMaker = new ColorMaker(arcs);
        int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue(point >=0 && point <=50);
    }

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith2ArcsAnd1Load(){
        List<Arc> arcs = Reader.readArcs("max_load/2arcs-1load.txt");
        ColorMaker colorMaker = new ColorMaker(arcs);
        int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( (point >= 0 && point <= 10) || (point >= 20 && point <= 30));
    }

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith2ArcsAnd2Load(){
        List<Arc> arcs = Reader.readArcs("max_load/2arcs-2load.txt");
        ColorMaker colorMaker = new ColorMaker(arcs);
        int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( point >= 5 && point <= 10);
    }

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith2ArcsAnd2LoadVerision2(){
        List<Arc> arcs = Reader.readArcs("max_load/2arcs-2load_v2.txt");
        ColorMaker colorMaker = new ColorMaker(arcs);
        int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( point == 10);
    }

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith3ArcsAnd1Load(){
        List<Arc> arcs = Reader.readArcs("max_load/3arcs-1load.txt");
        ColorMaker colorMaker = new ColorMaker(arcs);
        int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( point >= 40 && point <= 50);
    }

    @Test
    public void getPointWithHighestNumberOfIntersectionsWith2ArcsAnd1LoadModulo(){
        List<Arc> arcs = Reader.readArcs("max_load/2arcs_2load_modulo.txt");
        ColorMaker colorMaker = new ColorMaker(arcs);
        int point = colorMaker.getPointWithHighestNumberOfIntersections();
        assertTrue( (point >= 355 && point <= 359) || (point >= 0 && point <= 5) );
    }
}
