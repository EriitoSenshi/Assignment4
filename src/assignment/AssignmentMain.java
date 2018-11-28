package assignment;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class AssignmentMain extends Application {

    double lastFrameTime = 0.0;
    Circle player = new Circle();

    ArrayList<GameObject> playerWeapons = new ArrayList<>();
    ArrayList<GameObject> enemies = new ArrayList<>();

    int playerWeapon = -1;

    @Override
    public void start(Stage primaryStage) {
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();

        //Creating start menu
        MenuPane menuPane = new MenuPane();
        menuPane.makeMenuPane(primaryStage);

        //Initialize
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                //Moving enemies
                for (GameObject enemy : enemies) {
                    enemy.update(frameDeltaTime);

                    if (enemy.getCircle().getCenterX() - enemy.getCircle().getRadius() < 0) {
                        enemies.forEach((e) -> {
                            e.getVelocity().setX(Math.abs(e.getVelocity().getX()));
                        });
                    }
                    if (enemy.getCircle().getCenterX() + enemy.getCircle().getRadius() > 1280) {
                        enemies.forEach((e) -> {
                            e.getVelocity().setX(-Math.abs(e.getVelocity().getX()));
                        });
                    }
                }

            }
        }.start();

        //Start
        primaryStage.setTitle("Space invaders!");
        primaryStage.show();

    }

    public void createPlayerWeapon() {
        playerWeapon++;

        double circlePosX = player.getCenterX();
        double circlePosY = player.getCenterY() - player.getRadius();

        Vector2D playerWeaponPosition = new Vector2D(circlePosX, circlePosY);
        Vector2D playerWeaponVelocity = new Vector2D(0.0f, -300.0f);
        Vector2D playerWeaponAcceleration = new Vector2D(0, 0);
        playerWeapons.add(new GameObject(playerWeaponPosition, playerWeaponVelocity, playerWeaponAcceleration, 5));
        playerWeapons.get(playerWeapon).getCircle().setFill(Color.GREEN);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
