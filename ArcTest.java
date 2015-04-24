import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArcTest {

    private static final int arcStart = 10;
    private static final int arcEnd = 20;
    private Arc arc;

    private static Arc produceDisjointArc() {
        assert arcStart > 1;

        return new Arc(arcStart - 2, arcStart - 1);
    }

    private static Arc produceFullyCoveredArc() {
        assert arcEnd - arcStart > 2;

        return new Arc(arcStart + 1, arcEnd - 1);
    }

    @Before
    public void setUp() {
        this.arc = new Arc(arcStart, arcEnd);
    }

    @Test
    public void DefaultArcIsNotColored() throws Exception {
        assertFalse(arc.isColored());
    }

    @Test
    public void AfterColoringArcBecomesColored() throws Exception {
        arc.color(2);
        assertTrue(arc.isColored());
    }

    @Test(expected=UnsupportedOperationException.class)
    public void RecoloringThrows() throws Exception {
        arc.color(2);

        arc.color(3);
    }

    @Test
    public void CoversReturnsTrueWhenGivenArcIsCovered() throws Exception {
        assert arc.end - arc.start > 2;
        final Arc otherArc = new Arc(arc.start + 1, arc.end - 1);

        assertTrue(arc.covers(otherArc));
    }

    @Test
    public void CoversReturnsFalseWhenArcsAreDisjoint() throws Exception {
        final Arc otherArc = produceDisjointArc();

        assertFalse(arc.covers(otherArc));
    }

    @Test
    public void CoversReturnsFalseWhenArcIsPartiallyCovered() throws Exception {
        assert arc.end - arc.start > 1;
        final Arc otherArc = new Arc(arc.start - 1, arc.start + 1);

        assertFalse(arc.covers(otherArc));
    }

    @Test
    public void CoversReturnsTrueWhenArcIsFullyCovered() throws Exception {
        final Arc fullyCoveredArc = produceFullyCoveredArc();

        assertTrue(arc.covers(fullyCoveredArc));
    }

    @Test
    public void ArcDoesNotContainDisjointOne() throws Exception {
        final Arc disjointArc = produceDisjointArc();

        assertFalse(arc.contains(disjointArc));
    }

    @Test
    public void ArcContainsFullyCoveredOne() throws Exception {
        final Arc fullyCoveredArc = produceFullyCoveredArc();

        assertTrue(arc.contains(fullyCoveredArc));
    }

    @Test
    public void ArcWithCommonEndWithOtherContainsIt() throws Exception {
        assert arc.end - arc.start > 1;
        final Arc stuckArc = new Arc(arc.end - 1, arc.end + 1);

        assertTrue(arc.contains(stuckArc));
    }

    @Test
    public void ArcWithCommonStartWithOtherIsContainedByIt() throws Exception {
        assert arc.start > 1;
        assert arc.end - arc.start > 0;
        final Arc startingArc = new Arc(arc.start - 2, arc.start + 1);

        assertTrue(arc.contains(startingArc));
    }

    @Test
    public void DisjointArcsDoesNotIntersect() throws Exception {
        final Arc disjointArc = produceDisjointArc();

        assertFalse(arc.intersects(disjointArc));
    }

    @Test
    public void ArcStuckWithEndingContainsTheOtherOne() throws Exception {
        assert arc.end - arc.start > 1;
        final Arc stuckArc = new Arc(arc.end - 1, arc.end + 1);

        assertTrue(arc.intersects(stuckArc));
    }

    @Test
    public void ArcStuckedWithStartContainsTheOtherOne() throws Exception {
        assert arc.start > 1;
        assert arc.end - arc.start > 0;
        final Arc startingArc = new Arc(arc.start - 2, arc.start + 1);

        assertTrue(arc.intersects(startingArc));
    }

    @Test
    public void AfterSettingAColorItsRetrievable() throws Exception {
        arc.color(5);

        assertEquals(arc.getColor(), 5);
    }
}