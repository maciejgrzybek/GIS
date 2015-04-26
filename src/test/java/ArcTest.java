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

    @Test
    public void AfterColoringAColorIsRetrievable() throws Exception {
        arc.color(5);

        assertEquals(arc.getColor(), 5);
    }

    @Test
    public void StartPointIsRetrievable() throws Exception {
        assertEquals(arc.getStart(), arcStart);
    }

    @Test
    public void EndPointIsRetrievable() throws Exception {
        assertEquals(arc.getEnd(), arcEnd);
    }

    @Test
    public void EndPointIsCorrectEvenWhenArcPasses360Degree() throws Exception {
        final Arc passing360Arc = new Arc(340, 30);

        assertEquals(passing360Arc.getEnd(), 30);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void RecoloringThrows() throws Exception {
        arc.color(2);

        arc.color(3);
    }

    @Test
    public void CoversReturnsTrueWhenGivenArcIsCovered() throws Exception {
        assert arc.getEnd() - arc.getStart() > 2;
        final Arc otherArc = new Arc(arc.getStart() + 1, arc.getEnd() - 1);

        assertTrue(arc.covers(otherArc));
    }

    @Test
    public void CoversReturnsFalseWhenArcsAreDisjoint() throws Exception {
        final Arc otherArc = produceDisjointArc();

        assertFalse(arc.covers(otherArc));
    }

    @Test
    public void CoversReturnsFalseWhenArcIsPartiallyCovered() throws Exception {
        assert arc.getEnd() - arc.getStart() > 1;
        final Arc otherArc = new Arc(arc.getStart() - 1, arc.getStart() + 1);

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
        assert arc.getEnd() - arc.getStart() > 1;
        final Arc stuckArc = new Arc(arc.getEnd() - 1, arc.getEnd() + 1);

        assertTrue(arc.contains(stuckArc));
    }

    @Test
    public void ArcWithCommonStartWithOtherIsContainedByIt() throws Exception {
        assert arc.getStart() > 1;
        assert arc.getEnd() - arc.getStart() > 0;
        final Arc startingArc = new Arc(arc.getStart() - 2, arc.getStart() + 1);

        assertTrue(arc.contains(startingArc));
    }

    @Test
    public void DisjointArcsDoesNotIntersect() throws Exception {
        final Arc disjointArc = produceDisjointArc();

        assertFalse(arc.intersects(disjointArc));
    }

    @Test
    public void ArcStuckWithEndingContainsTheOtherOne() throws Exception {
        assert arc.getEnd() - arc.getStart() > 1;
        final Arc stuckArc = new Arc(arc.getEnd() - 1, arc.getEnd() + 1);

        assertTrue(arc.intersects(stuckArc));
    }

    @Test
    public void ArcStuckedWithStartContainsTheOtherOne() throws Exception {
        assert arc.getStart() > 1;
        assert arc.getEnd() - arc.getStart() > 0;
        final Arc startingArc = new Arc(arc.getStart() - 2, arc.getStart() + 1);

        assertTrue(arc.intersects(startingArc));
    }

    @Test
    public void ArcSpreadingThrough360DegreeContainsAnotherArcSpreadingThrough360() throws Exception {
        final Arc bigArc = new Arc(300, 100);
        final Arc smallArc = new Arc(359, 40);

        assertTrue(bigArc.contains(smallArc));
    }

    @Test
    public void ArcSpreadingThrough360DegreeContainsAnotherArcStartingAfter0() throws Exception {
        final Arc bigArc = new Arc(300, 100);
        final Arc smallArc = new Arc(0, 40);

        assertTrue(bigArc.contains(smallArc));
    }

    @Test
    public void ArcSpreadingThrough360DegreeCoversAnotherArcStartingAfter0() throws Exception {
        final Arc bigArc = new Arc(300, 100);
        final Arc smallArc = new Arc(10, 40);

        assertTrue(bigArc.covers(smallArc));
    }

    @Test
    public void ArcWithCommonEndingInModuloIsContainedByTheOther() throws Exception {
        final Arc firstArc = new Arc(330, 10);
        final Arc secondArc = new Arc(340, 30);

        assertTrue(firstArc.contains(secondArc));
    }

    @Test
    public void ArcBefore360DegreeEnclosedWithinArcSpreadingThrough360DegreeIsCoveredByIt() throws Exception {
        final Arc enclosedArc = new Arc(340, 350);
        final Arc enclosingArc = new Arc(300, 60);

        assertTrue(enclosingArc.covers(enclosedArc));
    }

    @Test
    public void PointWithinArcIsReportedAsInside() throws Exception {
        assert arc.getEnd() - arc.getStart() > 1;
        assertTrue(arc.isWithinRange(arc.getStart() + 1));
    }

    @Test
    public void PointWithinArcIsReportedAsInsideEvenForCross360DegreeArcs() throws Exception {
        final Arc a = new Arc(340, 10);
        assertTrue(a.isWithinRange(350));
    }

    @Test
    public void testGetArcLengthFromStartToPointWhichContains() {
        final Arc a = new Arc(340, 20);

        assertEquals(10, a.getArcLengthFromStartToPointWhichContains(350));
        assertEquals(20, a.getArcLengthFromStartToPointWhichContains(0));
        assertEquals(30, a.getArcLengthFromStartToPointWhichContains(10));

        final Arc b = new Arc(0, 20);

        assertEquals(0, b.getArcLengthFromStartToPointWhichContains(0));
        assertEquals(2, b.getArcLengthFromStartToPointWhichContains(2));
        assertEquals(20, b.getArcLengthFromStartToPointWhichContains(20));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetArcLengthFromStartToPointWhichContainsException(){
        final Arc a = new Arc(10, 20);
        a.getArcLengthFromStartToPointWhichContains(350);
    }

    @Test
    public void testIsWithinRange() {
        final Arc a = new Arc(340, 20);

        assertTrue(a.isWithinRange(350));
        assertTrue(a.isWithinRange(0));
        assertTrue(a.isWithinRange(10));
        assertFalse(a.isWithinRange(40));

        final Arc b = new Arc(40, 120);
        assertTrue(b.isWithinRange(40));
        assertTrue(b.isWithinRange(50));
        assertTrue(b.isWithinRange(120));
        assertFalse(b.isWithinRange(350));

        final Arc c = new Arc(40, 120);
        assertFalse(c.isWithinRange(0));
    }

    @Test
    public void testIsSpreadingThrough360() {
        final Arc a = new Arc(340, 20);
        assertTrue(a.isSpreadingThrough360());

        final Arc b = new Arc(0, 20);
        assertFalse(b.isSpreadingThrough360());
    }

}