public class Arc implements Comparable<Arc> {
    public Arc(int start, int end) {
        this(start, end, 0);
    }

    public Arc(int start, int end, int color) {
        if (passesThroughModulo(start, end))
            end += 360;

        this.start = start;
        this.end = end;
        this.color = color;
    }

    public boolean isColored() {
        return color != 0;
    }

    public void color(int color) {
        if (isColored())
            throw new UnsupportedOperationException();
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end % 360;
    }

    public boolean intersects(Arc other) {
        return this.contains(other) || other.contains(this);
    }

    public boolean contains(Arc other) {
        if (covers(other))
                return true;

        if (passesThroughModulo()) {
            if (start <= other.start) // other.start between start and 360 degree
                return true;
            else
                return other.start <= getEnd();
        }
        
        return  (start < other.start && end < other.end && end > other.start)
                || (start > other.start && start < other.end && end > other.end);
    }

    public boolean covers(Arc other) {
        if (passesThroughModulo()) {
            if (other.start >= start) // between start and 360 degree
                return end >= other.end;
            else
                return other.start <= getEnd() && other.end <= getEnd(); // between 360 degree and end point
        }
        return start <= other.start && end >= other.end;
    }

    public boolean isWithinRange(int i) {
        if (start <= getEnd()) // not passing through modulo
            return (i >= start && i <= getEnd());

        return (i >= start || i <= getEnd());
    }

    public int getArcLengthFromStartToPointWhichContains(int point) {
        if (!isWithinRange(point)) {
            throw new IllegalStateException("The arc " + this + " doesn't contain point: " + point);
        }

        if (point < start)
            point += 360;

        return point - start;
    }

    public boolean isSpreadingThrough360() {
        return (end >= 360);
    }

    @Override
    public String toString() {
        return "Arc{" +
                "start=" + start +
                ", end=" + end +
                ", color=" + color +
                '}';
    }

    @Override
    public int compareTo(Arc arc) {
        return ((Integer)getStart()).compareTo(arc.getStart());
    }

    private boolean passesThroughModulo() {
        return passesThroughModulo(start, getEnd());
    }

    private static boolean passesThroughModulo(int start, int end) {
        return end < start;
    }

    private final int start;
    private final int end;
    private int color; // 0 = undefined
}
