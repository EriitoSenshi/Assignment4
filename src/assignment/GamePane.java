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
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class GamePane extends Pane {

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Enemy> removeEnemies = new ArrayList<>();
    ArrayList<Circle> lives = new ArrayList<>();
    Player player = new Player();
    Weapon pw;
    double lastFrameTime = 0.0;
    boolean isGamePlaying = true;
    boolean win = false;

    public void gameLoop(GamePane gamePane, Stage stage, MenuPane menuPane, Scene menu, MediaPlayer gameMusic, MediaPlayer startMusic) {
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
                if (gameMusic == null) {
                    gameMusic.play();
                }
                for (Enemy enemy : enemies) {
                    enemy.moveEnemies(enemy, frameDeltaTime, enemies);
                }
                game.setOnMouseClicked(event -> {
                    if (!pw.getCircle().isVisible()) {
                        pw.getCircle().setVisible(true);
                        pw.setPlayerWeapon(pw, player);
                    }
                });
                pw.movePlayerWeapon(pw, frameDeltaTime);
                pw.checkPlayerWeaponCollision(pw, gamePane);
                for (Enemy enemy : enemies) {
                    playerWeaponToEnemy(pw, enemy, gamePane);
                }
                enemies.removeAll(removeEnemies);

                if (enemies.isEmpty() && isGamePlaying) {
                    win = true;
                    stopGame(gamePane, menu, stage, gameMusic, startMusic);
                }

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
        makePlayerWeapon(gamePane);
        makeLives(gamePane);
        isGamePlaying = true;
    }

    public void player(GamePane gamePane, Scene game) {
        if (!gamePane.getChildren().contains(player)) {
            player.makePlayer(gamePane, player);
        }
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

    public void makePlayerWeapon(GamePane gamePane) {
        Vector2D pwPosition = new Vector2D(player.getCenterX(), player.getCenterY() - player.getRadius());
        Vector2D pwVelocity = new Vector2D(0.0f, -450.0f);
        Vector2D pwAcceleration = new Vector2D(0, 0);
        if (pw == null) {
            pw = new Weapon(pwPosition, pwVelocity, pwAcceleration, 7);
        }
        pw.getCircle().setFill(Color.GREEN);
        pw.getCircle().setVisible(false);
        gamePane.getChildren().add(pw.getCircle());
    }

    public void makeEnemyWeapon(GamePane gamePane) {

    }

    public void playerWeaponToEnemy(Weapon pw, Enemy enemy, GamePane gamePane) {
        double changingDistance = Math.sqrt(Math.pow((pw.getCircle().getCenterX() - enemy.getCircle().getCenterX()), 2) + Math.pow((pw.getCircle().getCenterY() - enemy.getCircle().getCenterY()), 2));
        double fixedDistance = pw.getCircle().getRadius() + enemy.getCircle().getRadius();
        if (pw.getCircle().isVisible()) {
            if (changingDistance <= fixedDistance) {
                pw.getCircle().setVisible(false);
                removeEnemies.add(enemy);
                gamePane.getChildren().remove(enemy.getCircle());
            }
        }
    }

    public void makeLives(GamePane gamePane) {
        for (int i = 0; i < 3; i++) {
            lives.add(new Circle(1100 + i * 60, 675, 25, Color.RED));
            gamePane.getChildren().add(lives.get(i));
        }
    }

    public void stopGame(GamePane gamePane, Scene menu, Stage stage, MediaPlayer gameMusic, MediaPlayer startMusic) {
        cleanup(gamePane);
        stage.setScene(menu);
        gameMusic.stop();
        startMusic.play();
        isGamePlaying = false;
    }

    public void cleanup(GamePane gamePane) {
        if (enemies != null) {
            enemies.removeAll(enemies);
            gamePane.getChildren().removeAll(enemies);
        }
    }

}
