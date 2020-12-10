package fuzzy;

public class Vector2D {
    final public int x;
    final public int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return '(' + String.valueOf(this.x) + ',' + this.y + ')';
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public boolean precedes(Vector2D other) {
        return this.x < other.x && this.y < other.y;
    }

    public boolean follows(Vector2D other) {
        return this.x > other.x && this.y > other.y;
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vector2D)) return false;
        Vector2D that = (Vector2D) other;
        return this.x == that.x && this.y == that.y;
    }
}