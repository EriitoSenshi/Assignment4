package assignment;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
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

    ArrayList<GameObject> playerWeapons = new ArrayList<>();
    ArrayList<GameObject> enemies = new ArrayList<>();

    AnchorPane game = new AnchorPane();

    MediaPlayer startMusic;
    MediaPlayer gameMusic;

    int playerWeapon = -1;

    @Override
    public void start(Stage primaryStage) {
        AssetManager.preloadAllAssets();
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();
        startMusic = new MediaPlayer(AssetManager.getStartBackgroundMusic());
        gameMusic = new MediaPlayer(AssetManager.getBackgroundMusic());
        
        Scene gamePlay = new Scene(game);
        
        //Creating start menu
        Button startButton = new Button("");
        MenuPane menuPane = new MenuPane();
        Scene startMenu = new Scene(menuPane);
        menuPane.makeMenuPane(menuPane, primaryStage, startMenu, startButton);
        startMusic.play();

        //Creating game
        game.setMinWidth(1280);
        game.setMinHeight(720);
        game.setBackground(AssetManager.getBackgroundImage());

        //Player and enemy methods
        createPlayer(game);
        createEnemies(game);

        //Switching from start menu to game
        startButton.setOnAction((event) -> {
            primaryStage.setScene(gamePlay);
            startMusic.stop();
            gameMusic.play();
        });

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

                //Shooting
                game.setOnMouseClicked(event -> {
                    createPlayerWeapon();
                    playerWeapons.get(playerWeapon).update(frameDeltaTime);
                });

            }
        }.start();

        //Moving player
        game.setOnMouseMoved(event -> {
            player.setCenterX(event.getX());
        });

        //Start
        primaryStage.setTitle("Space invaders!");
        primaryStage.setScene(startMenu);
        primaryStage.show();

    }

    public void addToPane(Node node, Pane pane) {
        pane.getChildren().add(node);
    }

    public void removeFromPane(Node node, Pane pane) {
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
                Vector2D enemyPosition = new Vector2D(320 + (j * 70), 40 + (k * 70));
                Vector2D enemyVelocity = new Vector2D(100.0f, 1.0f);
                Vector2D enemyAcceleration = new Vector2D(0, 0);
                enemies.add(new GameObject(enemyPosition, enemyVelocity, enemyAcceleration, 25));

            }
        }
        for (int k = 0; k < enemies.size(); k++) {
            enemies.get(k).getCircle().setFill(Color.YELLOW);
            addToPane(enemies.get(k).getCircle(), pane);
        }

    }

    public void loseLife() {

    }

    public void loseGame(MediaPlayer start, MediaPlayer game) {
        game.stop();
        start.play();
    }

    public void restart() {

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
        addToPane(playerWeapons.get(playerWeapon).getCircle(), game);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
