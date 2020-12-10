package fuzzy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private final int width = 800;
    private final int height = 800;
    private final Vector2D lowerLeft = new Vector2D(0, 0);
    private final Vector2D upperRight = new Vector2D(15, 15);
    private final Vector2D startingPosition = new Vector2D(5, 5);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Fuzzy logic");

        Room room = new Room(this.lowerLeft, this.upperRight);

        Vacuum vacuum = new Vacuum(startingPosition, room);

        BoardPane root = new BoardPane(this.width, this.height, room, vacuum);

        Scene primaryScene = new Scene(root, width, height);
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        root.setEventHandlers();

        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), event -> {
            room.contaminate();
            vacuum.step();
            root.update();
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if (timeline.getStatus() == Animation.Status.PAUSED) {
                    timeline.play();
                } else {
                    timeline.pause();
                }
            }
        });
    }

}
