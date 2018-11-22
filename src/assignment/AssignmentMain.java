package assignment;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author hany albouz
 */
public class AssignmentMain extends Application {

    double lastFrameTime = 0.0;
    Circle player = new Circle();

    @Override
    public void start(Stage primaryStage) {
        AssetManager.preloadAllAssets();

        //Creating start menu
        AnchorPane startMenu = new AnchorPane();
        startMenu.setMinWidth(1280);
        startMenu.setMinHeight(720);
        startMenu.setBackground(AssetManager.getStartBackgroundImage());
        Button startButton = new Button("");
        startButton.setLayoutX(565);
        startButton.setMinHeight(200);
        startButton.setMinWidth(300);
        startButton.setOpacity(0);
        addToPane(startButton, startMenu);

        //Creating game
        AnchorPane game = new AnchorPane();
        game.setMinWidth(1280);
        game.setMinHeight(720);
        game.setBackground(AssetManager.getBackgroundImage());

        //Creating the player and enemies
        Circle playerWeapon = new Circle();
        createPlayer(player, playerWeapon, game);
        createEnemies(game);

        Scene start = new Scene(startMenu);
        Scene gamePlay = new Scene(game);

        //Switching from game
        startButton.setOnAction((event) -> {
            primaryStage.setScene(gamePlay);
        });

        //Start
        primaryStage.setTitle("Space invaders!");
        primaryStage.setScene(start);
        primaryStage.show();

    }

    public void addToPane(Node node, AnchorPane pane) {
        pane.getChildren().add(node);
    }

    public void movePlayer(MouseEvent me) {

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.setCenterX(me.getX());
            }
        }.start();
    }

    public void createPlayer(Circle c, Circle r, AnchorPane pane) {
        c.setCenterX(40);
        c.setCenterY(600);
        c.setRadius(25);
        c.setFill(Color.BLUE);
        addToPane(c, pane);
    }

    public void createEnemies(AnchorPane pane) {
        ArrayList<Circle> enemies = new ArrayList<>();
        ArrayList<Vector2D> enemiesVelocityList = new ArrayList<>();
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 8; j++) {
                enemies.add(new Circle(320 + (j * 70), 40 + (i * 70), 25, Color.YELLOW));
                enemiesVelocityList.add(new Vector2D(100, 1));
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            addToPane(enemies.get(i), pane);
        }
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                for (int i = 0; i < enemies.size(); i++) {
                    Circle c = enemies.get(i);
                    Vector2D position = new Vector2D(c.getCenterX(), c.getCenterY());
                    Vector2D v = enemiesVelocityList.get(i);
                    position = position.add(v.mult(frameDeltaTime));
                    c.setCenterX(position.getX());
                    c.setCenterY(position.getY());

                    if (enemies.get(0).getCenterX() - c.getRadius() < 0) {
                        v.setX(Math.abs(v.getX()));
                    }
                    if (enemies.get(7).getCenterX() + c.getRadius() > 1280) {
                        v.setX(-Math.abs(v.getX()));
                    }
                }
            }
        }.start();

    }

    public void loseLife() {

    }

    public void loseGame(Stage stage, Scene scene) {
        stage.setScene(scene);
    }

    public void playerShoot(Circle c) {
        AssetManager.preloadAllAssets();
    }

    public void enemyShoot(Circle c) {
        AssetManager.preloadAllAssets();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
