package assignment;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    ArrayList<Circle> playerWeapons = new ArrayList<>();
    Vector2D playerWeaponVelocity;
    Vector2D playerWeaponPosition;
    ArrayList<Circle> enemies = new ArrayList<>();
    ArrayList<Vector2D> enemiesVelocityList = new ArrayList<>();
    AnchorPane startMenu = new AnchorPane();
    AnchorPane game = new AnchorPane();

    @Override
    public void start(Stage primaryStage) {
        AssetManager.preloadAllAssets();
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();

        //Creating start menu
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
        game.setMinWidth(1280);
        game.setMinHeight(720);
        game.setBackground(AssetManager.getBackgroundImage());

        //Player and enemy methods
        createPlayer(game);
        createEnemies(game);

        //Initialize
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

                    if (enemies.get(i).getCenterX() - c.getRadius() < 0) {
                        for (int j = 0; j < enemies.size(); j++) {
                            enemiesVelocityList.get(j).setX(Math.abs(enemiesVelocityList.get(j).getX()));
                        }
                    }
                    if (enemies.get(i).getCenterX() + c.getRadius() > 1280) {
                        for (int j = 0; j < enemies.size(); j++) {
                            enemiesVelocityList.get(j).setX(-Math.abs(enemiesVelocityList.get(j).getX()));
                        }
                    }
                }
            }
        }.start();

        //Shooting
        game.setOnMouseClicked(event -> {
            playerShoot();
        });

        game.setOnMouseMoved(event -> {
            player.setCenterX(event.getX());
        });

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

    public void removeFromPane(Node node, AnchorPane pane) {
        pane.getChildren().remove(node);
    }

    public void createPlayer(AnchorPane pane) {
        player.setCenterX(40);
        player.setCenterY(600);
        player.setRadius(25);
        player.setFill(Color.BLUE);
        addToPane(player, pane);
    }

    public void createEnemies(AnchorPane pane) {

        for (int k = 0; k < 4; k++) {
            for (int j = 1; j <= 8; j++) {
                enemies.add(new Circle(320 + (j * 70), 40 + (k * 70), 25, Color.YELLOW));
                enemiesVelocityList.add(new Vector2D(100.0f, 1.0f));
            }
        }
        for (int k = 0; k < enemies.size(); k++) {
            addToPane(enemies.get(k), pane);
        }

    }

    public void loseLife() {

    }

    public void loseGame(Stage stage, Scene scene) {
        stage.setScene(scene);
    }

    int i = -1;

    public void playerShoot() {
        playerWeapons.add(new Circle(player.getCenterX(), player.getCenterY() - player.getRadius(), 5, Color.GREEN));
        i++;

        double circlePosX = playerWeapons.get(i).getCenterX();
        double circlePosY = playerWeapons.get(i).getCenterY();

        playerWeaponPosition = new Vector2D(circlePosX, circlePosY);
        playerWeaponVelocity = new Vector2D(0.0f, -300.0f);

    }

    public void enemyShoot(Circle c) {

    }

    public static void main(String[] args) {
        launch(args);
    }

}
