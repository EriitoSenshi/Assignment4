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
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class GamePane extends Pane {

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Enemy> removeEnemies = new ArrayList<>();
    ArrayList<Life> lives = new ArrayList<>();
    ArrayList<Shield> shields = new ArrayList<>();
    ArrayList<Shield> removeShields = new ArrayList<>();
    Player player = new Player();
    Weapon pw;
    Weapon ew;
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

                enemies.forEach((enemy) -> {
                    enemy.moveEnemies(enemy, frameDeltaTime, enemies);
                });

                game.setOnMouseClicked(event -> {
                    if (!pw.getCircle().isVisible()) {
                        pw.getCircle().setVisible(true);
                        pw.setPlayerWeapon(pw, player);
                    }
                });
                pw.movePlayerWeapon(pw, frameDeltaTime);
                pw.checkPlayerWeaponCollision(pw, gamePane);
                enemies.forEach((enemy) -> {
                    playerWeaponToEnemy(pw, enemy, gamePane);
                    enemyBottom(enemy, gamePane, menu, stage, gameMusic, startMusic);
                });
                enemies.removeAll(removeEnemies);

                if (enemies.isEmpty() && isGamePlaying) {
                    win = true;
                    stopGame(gamePane, menu, stage, gameMusic, startMusic);
                }

                int randomNumber = (int) (Math.random() * 75);
                int random = (int) (Math.random() * (enemies.size() - 1));
                if (randomNumber == 8) {
                    if (!ew.getCircle().isVisible() && isGamePlaying) {
                        ew.getCircle().setVisible(true);
                        ew.setEnemyWeapon(ew, enemies.get(random));
                    }
                }
                ew.moveEnemyWeapon(ew, frameDeltaTime);
                ew.checkEnemyWeaponCollision(ew, gamePane);
                shields.forEach((shield) -> {
                    enemyWeaponToShields(ew, shield, gamePane);
                    enemies.forEach(enemy -> {
                        enemiesToShields(enemy, shield, gamePane);
                    });
                });
                shields.removeAll(removeShields);
                enemyWeaponToPlayer(ew, player, gamePane, menu, stage, gameMusic, startMusic);

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
        makeEnemyWeapon(gamePane);
        makeLives(gamePane);
        makeShields(gamePane);
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
                Vector2D enemyVelocity = new Vector2D(100.0f, 2.0f);
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

        Vector2D ewPosition = new Vector2D(0, 0);
        Vector2D ewVelocity = new Vector2D(0.0f, 450.0f);
        Vector2D ewAcceleration = new Vector2D(0, 0);
        if (ew == null) {
            ew = new Weapon(ewPosition, ewVelocity, ewAcceleration, 7);
        }
        ew.getCircle().setFill(Color.GREEN);
        ew.getCircle().setVisible(false);
        gamePane.getChildren().add(ew.getCircle());

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

    public void enemyWeaponToPlayer(Weapon ew, Player player, GamePane gamePane, Scene menu, Stage stage, MediaPlayer gameMusic, MediaPlayer startMusic) {
        double changingDistance = Math.sqrt(Math.pow((ew.getCircle().getCenterX() - player.getCenterX()), 2) + Math.pow((ew.getCircle().getCenterY() - player.getCenterY()), 2));
        double fixedDistance = ew.getCircle().getRadius() + player.getRadius();
        if (ew.getCircle().isVisible()) {
            if (changingDistance <= fixedDistance) {
                removeLives(gamePane, menu, stage, gameMusic, startMusic);
                ew.getCircle().setVisible(false);
            }
        }
    }

    public void enemyWeaponToShields(Weapon ew, Shield shield, GamePane gamePane) {
        double changingDistance = Math.sqrt(Math.pow((ew.getCircle().getCenterX() - shield.getCenterX()), 2) + Math.pow((ew.getCircle().getCenterY() - shield.getCenterY()), 2));
        double fixedDistance = ew.getCircle().getRadius() + shield.getRadius();
        if (ew.getCircle().isVisible()) {
            if (changingDistance <= fixedDistance) {
                ew.getCircle().setVisible(false);
                gamePane.getChildren().remove(shield);
                removeShields.add(shield);
            }
        }
    }

    public void enemyBottom(Enemy enemy, GamePane gamePane, Scene menu, Stage stage, MediaPlayer gameMusic, MediaPlayer startMusic) {
        double changingDistance = Math.sqrt(Math.pow((enemy.getCircle().getCenterX() - player.getCenterX()), 2) + Math.pow((enemy.getCircle().getCenterY() - player.getCenterY()), 2));
        double fixedDistance = enemy.getCircle().getRadius() + player.getRadius();
        if (changingDistance <= fixedDistance) {
            stopGame(gamePane, menu, stage, gameMusic, startMusic);
        }
        if (enemy.getCircle().getCenterY() >= 600) {
            stopGame(gamePane, menu, stage, gameMusic, startMusic);
        }
    }

    public void enemiesToShields(Enemy enemy, Shield shield, GamePane gamePane) {
        double changingDistance = Math.sqrt(Math.pow((enemy.getCircle().getCenterX() - shield.getCenterX()), 2) + Math.pow((enemy.getCircle().getCenterY() - shield.getCenterY()), 2));
        double fixedDistance = enemy.getCircle().getRadius() + shield.getRadius();
        if (changingDistance <= fixedDistance) {
            gamePane.getChildren().remove(shield);
            gamePane.getChildren().remove(enemy.getCircle());
            removeShields.add(shield);
            removeEnemies.add(enemy);
        }
    }

    public void makeLives(GamePane gamePane) {
        for (int i = 0; i < 3; i++) {
            lives.add(new Life(1100 + i * 60, 675, 25, Color.RED));
            gamePane.getChildren().add(lives.get(i));
        }
    }

    public void makeShields(GamePane gamePane) {
        for (int i = 0; i < 3; i++) {
            shields.add(new Shield(320 + i * 300, 450, 40, Color.WHITE));
            gamePane.getChildren().add(shields.get(i));
        }
    }

    public void removeLives(GamePane gamePane, Scene menu, Stage stage, MediaPlayer gameMusic, MediaPlayer startMusic) {
        if (lives.size() == 3) {
            gamePane.getChildren().remove(lives.get(2));
            lives.remove(lives.get(2));
        } else if (lives.size() == 2) {
            gamePane.getChildren().remove(lives.get(1));
            lives.remove(lives.get(1));
        } else if (lives.size() == 1) {
            gamePane.getChildren().remove(lives.get(0));
            lives.remove(lives.get(0));
            stopGame(gamePane, menu, stage, gameMusic, startMusic);
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
            removeEnemies.addAll(enemies);
            enemies.removeAll(removeEnemies);
            gamePane.getChildren().removeAll(enemies);
        }
        if (lives != null) {
            lives.removeAll(lives);
            gamePane.getChildren().removeAll(lives);
        }
        if (shields != null) {
            removeShields.addAll(shields);
            shields.removeAll(removeShields);
            gamePane.getChildren().removeAll(shields);
        }
    }

}
