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
        return this.contains(other) || other.contains(this)
                || this.covers(other) || other.covers(this);
    }

    public boolean contains(Arc other) {
        return covers(other) || (start < other.start && end < other.end && end > other.start)
                || (start > other.start && start < other.end && end > other.end);
    }

    public boolean covers(Arc other) {
        return start <= other.start && end >= other.end;
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

    private static boolean passesThroughModulo(int start, int end) {
        return end < start;
    }

    private final int start;
    private final int end;
    private int color; // 0 = undefined

}
