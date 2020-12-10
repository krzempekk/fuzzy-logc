package fuzzy;

public class Vacuum {
    Vector2D position;
    Direction direction;
    Fuzzy fuzzy;
    Room room;

    public Vacuum(Vector2D position, Room room) {
        this.position = position;
        this.room = room;
        this.direction = Direction.North;
        this.fuzzy = new Fuzzy();
    }

    public void step() {
        int frontObstacleDistance = 0;
        Vector2D currentPosition = this.position;
        while (currentPosition.follows(this.room.lowerLeft) && currentPosition.precedes(this.room.upperRight)) {
            currentPosition = currentPosition.add(this.direction.toVector());
            if (this.room.fields.get(currentPosition) instanceof Obstacle) break;
            frontObstacleDistance++;
        }

        IField frontField = this.room.fields.get(this.position.add(this.direction.toVector()));
        int frontContaminationLevel = frontField instanceof Floor ? ((Floor) frontField).getContaminationLevel() : 0;

        IField frontLeftField = this.room.fields.get(this.position.add(this.direction.previous().toVector()));
        int frontLeftContaminationLevel =
            frontLeftField instanceof Floor ? ((Floor) frontLeftField).getContaminationLevel() : 0;

        IField frontRightField = this.room.fields.get(this.position.add(this.direction.next().toVector()));
        int frontRightContaminationLevel =
            frontRightField instanceof Floor ? ((Floor) frontRightField).getContaminationLevel() : 0;

        int result = this.fuzzy.evaluate(frontContaminationLevel,
            frontObstacleDistance,
            frontLeftContaminationLevel,
            frontRightContaminationLevel
        );
        
        //        System.out.println("D " + frontObstacleDistance + ", F " + frontContaminationLevel + ", FL " + frontLeftContaminationLevel + ", FR " + frontRightContaminationLevel + ", result " + result);

        if (result <= -22.5) {
            this.direction = this.direction.previous();
        } else if (result >= 22.5) {
            this.direction = this.direction.next();
        }

        Vector2D newPosition = this.position.add(this.direction.toVector());
        IField newField = this.room.fields.get(newPosition);
        if (newField instanceof Floor) {
            ((Floor) newField).setContaminationLevel(0);
            this.position = newPosition;
        } else {
            this.direction = this.direction.next();
        }
    }
}
