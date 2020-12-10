package fuzzy;

public enum Direction {
    North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest;

    public Direction previous() {
        return Direction.values()[(this.ordinal() + 7) % 8];
    }

    public Direction next() {
        return Direction.values()[(this.ordinal() + 1) % 8];
    }

    public Direction opposite() {
        return Direction.values()[(this.ordinal() + 4) % 8];
    }

    public Vector2D toVector() {
        return switch (this) {
            case North -> new Vector2D(0, 1);
            case NorthEast -> new Vector2D(1, 1);
            case East -> new Vector2D(1, 0);
            case SouthEast -> new Vector2D(1, -1);
            case South -> new Vector2D(0, -1);
            case SouthWest -> new Vector2D(-1, -1);
            case West -> new Vector2D(-1, 0);
            case NorthWest -> new Vector2D(-1, 1);
        };
    }
}
