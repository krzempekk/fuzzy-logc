package fuzzy;

import java.util.HashMap;

public class Room {
    final Vector2D lowerLeft;
    final Vector2D upperRight;
    HashMap<Vector2D, IField> fields;

    int getRandomValue(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public Room(Vector2D lowerLeft, Vector2D upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.fields = new HashMap<>();

        for (int x = lowerLeft.x; x <= upperRight.x; x++) {
            for (int y = lowerLeft.y; y <= upperRight.y; y++) {
                if (Math.random() < 0.05 || x == lowerLeft.x || x == upperRight.x || y == lowerLeft.y || y == upperRight.y) {
                    fields.put(new Vector2D(x, y), new Obstacle());
                } else {
                    fields.put(new Vector2D(x, y), new Floor(getRandomValue(0, 100)));
                }
            }
        }
    }

    public void contaminate() {
        for (IField field : fields.values()) {
            if (field instanceof Floor && Math.random() < 0.005) {
                Floor floor = (Floor) field;
                floor.setContaminationLevel(Math.min(floor.getContaminationLevel() + 10, 100));
            }
        }
    }
}
