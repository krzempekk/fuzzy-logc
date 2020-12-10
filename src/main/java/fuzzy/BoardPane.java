package fuzzy;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BoardPane extends Pane {
    int width;
    int height;
    Canvas canvas;
    private final int fieldSize;
    private final double vacuumWidth = 1 / 2.0;
    private final double gridLineWidth = 1 / 15.0;
    private Room room;
    private Vacuum vacuum;

    public BoardPane(int width, int height, Room room, Vacuum vacuum) {
        this.width = width;
        this.height = height;
        this.room = room;
        this.vacuum = vacuum;
        this.canvas = new Canvas(this.width, this.height);
        this.fieldSize = this.width / (room.upperRight.x - room.lowerLeft.x + 1);
        this.getChildren().add(this.canvas);
    }

    public void setEventHandlers() {
        this.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            int x = (int) (event.getSceneX() / this.fieldSize);
            int y = (int) ((this.height - event.getSceneY()) / this.fieldSize);
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("[contamination] at (" + x + ", " + y + ")");
                IField field = this.room.fields.get(new Vector2D(x, y));
                ((Floor) field).setContaminationLevel(100);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("[vacuum] moving to (" + x + ", " + y + ")");
                vacuum.position = new Vector2D(x, y);
            }
            this.update();
        });
    }

    public void update() {
        this.layoutChildren();
    }

    @Override
    protected void layoutChildren() {
        Platform.runLater(() -> {
            super.layoutChildren();

            GraphicsContext gc = this.canvas.getGraphicsContext2D();
            gc.setFill(Color.color(0, 0, 0));
            gc.fillRect(0, 0, this.width, this.height);

            Vector2D lowerLeft = this.room.lowerLeft;
            Vector2D upperRight = this.room.upperRight;

            for (int x = lowerLeft.x; x <= upperRight.x; x++) {
                for (int y = lowerLeft.y; y <= upperRight.y; y++) {
                    Vector2D position = new Vector2D(x, y);
                    IField field = this.room.fields.get(position);

                    if (field instanceof Floor) {
                        Floor floor = (Floor) field;
                        gc.setFill(Color.gray(1 - floor.getContaminationLevel() / 150.0));
                    } else if (field instanceof Obstacle) {
                        gc.setFill(Color.CHOCOLATE);
                    }

                    gc.fillRect(
                        (x + this.gridLineWidth) * this.fieldSize,
                        this.height - (y + 1 - this.gridLineWidth) * this.fieldSize,
                        this.fieldSize * (1 - 2 * this.gridLineWidth),
                        this.fieldSize * (1 - 2 * this.gridLineWidth)
                    );
                }
            }

            gc.setFill(Color.color(1, 0, 0));

            double vacuumX = (vacuum.position.x + ((1 - this.vacuumWidth) / 2.0)) * this.fieldSize;
            double vacuumY = this.height - (vacuum.position.y + ((1 + this.vacuumWidth) / 2.0)) * this.fieldSize;
            double vacuumSize = this.vacuumWidth * this.fieldSize;

            gc.fillOval(vacuumX, vacuumY, vacuumSize, vacuumSize);

            gc.setFill(Color.color(1, 0.5, 0.5));

            double vacuumDirX = vacuumX + vacuumSize / 4;
            double vacuumDirY = vacuumY + vacuumSize / 4;

            gc.fillOval(
                vacuumDirX + vacuum.direction.toVector().x * vacuumSize / 2,
                vacuumDirY - vacuum.direction.toVector().y * vacuumSize / 2,
                vacuumSize / 2,
                vacuumSize / 2
            );

        });
    }
}