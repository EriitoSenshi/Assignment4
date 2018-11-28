/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class GamePane extends Pane {

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Weapon> playerWeapons = new ArrayList<>();
    Player player = new Player();
    Weapon pw;
    int playerWeapon = -1;
    double lastFrameTime = 0.0;

    public void gameLoop(GamePane gamePane, Stage stage) {
        AssetManager.preloadAllAssets();
        Scene game = new Scene(gamePane);
        makeGamePane(game, stage, gamePane);
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;
                for (Enemy enemy : enemies) {
                    enemy.moveEnemies(enemy, frameDeltaTime, enemies);
                }
                game.setOnMouseClicked(event -> {
                    playerWeapon++;
                    makePlayerWeapon(gamePane, frameDeltaTime);
                });
            }
        }.start();
    }

    public void makeGamePane(Scene game, Stage stage, GamePane gamePane) {
        gamePane.setMinWidth(1280);
        gamePane.setMinHeight(720);
        gamePane.setBackground(AssetManager.getBackgroundImage());
        stage.setScene(game);
        player(gamePane, game);
        createEnemies(gamePane, game);
    }

    public void player(GamePane gamePane, Scene game) {

        player.makePlayer(gamePane, player);
        game.setOnMouseMoved(event -> {
            player.movePlayer(event, player);
        });

    }

    public void createEnemies(GamePane gamePane, Scene game) {

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 8; j++) {
                Vector2D enemyPosition = new Vector2D(320 + (j * 70), 40 + (i * 70));
                Vector2D enemyVelocity = new Vector2D(100.0f, 1.0f);
                Vector2D enemyAcceleration = new Vector2D(0, 0);
                enemies.add(new Enemy(enemyPosition, enemyVelocity, enemyAcceleration, 25));

            }
        }
        for (Enemy enemy : enemies) {
            enemy.getCircle().setFill(Color.YELLOW);
            gamePane.getChildren().add(enemy.getCircle());
        }
    }

    public void makePlayerWeapon(GamePane gamePane, double time) {
        Vector2D pwPosition = new Vector2D(player.getCenterX(), player.getCenterY() - player.getRadius());
        Vector2D pwVelocity = new Vector2D(0.0f, -100.0f);
        Vector2D pwAcceleration = new Vector2D(0, 0);
        if (pw == null) {
            pw = new Weapon(pwPosition, pwVelocity, pwAcceleration, 5);
            gamePane.getChildren().add(pw.getCircle());
            pw.update(time);
        }

    }

    public void makeEnemyWeapon(GamePane gamePane) {

    }
}
