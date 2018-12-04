/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class GamePane extends Pane {

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Enemy> removeEnemies = new ArrayList<>();
    private ArrayList<Life> lives = new ArrayList<>();
    private ArrayList<Shield> shields = new ArrayList<>();
    private ArrayList<Shield> removeShields = new ArrayList<>();
    private Player player = new Player();
    private Scene game;
    private Weapon pw;
    private MediaPlayer victoryMusic;
    private MediaPlayer defeatMusic;
    private ArrayList<Weapon> ew = new ArrayList<>();
    private Label lifeLabel = new Label("Lives: ");
    private Label score = new Label("Score: 0");
    private int scoreCount = 0;
    private double lastFrameTime = 0.0;
    private boolean isGamePlaying = true;
    private boolean win = false;

    public void gameLoop(GamePane gamePane, Stage stage, MenuPane menuPane, Scene menu,
            MediaPlayer gameMusic, MediaPlayer startMusic,
            Label wins, Label losses) {
        game = new Scene(gamePane);
        makeGamePane(game, stage, gamePane);
        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                gameMusic.setOnEndOfMedia(() -> {
                    gameMusic.play();
                });

                enemies.forEach((enemy) -> {
                    enemy.moveEnemies(enemy, frameDeltaTime, enemies);
                });

                game.setOnMouseClicked(event -> {
                    if (!pw.getCircle().isVisible() && isGamePlaying) {
                        AssetManager.getPlayerShootingSounds((int) (Math.random() * 3)).play();
                        pw.getCircle().setVisible(true);
                        pw.setPlayerWeapon(pw, player);
                    }
                });
                pw.movePlayerWeapon(pw, frameDeltaTime);
                pw.checkPlayerWeaponCollision(pw, gamePane);
                try {
                    enemies.forEach((enemy) -> {
                        playerWeaponToEnemy(pw, enemy, gamePane);
                        enemyBottom(enemy, gamePane, menu, stage, gameMusic, startMusic,
                                wins, losses);
                    });
                    enemies.removeAll(removeEnemies);
                } catch (Exception e) {
                }

                if (enemies.isEmpty() && isGamePlaying) {
                    win = true;
                    stopGame(gamePane, menu, stage, gameMusic, startMusic,
                            wins, losses);
                }

                int randomNumber = (int) (Math.random() * 300);
                int random = (int) (Math.random() * (enemies.size() - 1));

                if (randomNumber == 50) {
                    if (!ew.get(0).getCircle().isVisible() && isGamePlaying) {
                        ew.get(0).getCircle().setVisible(true);
                        ew.get(0).setEnemyWeapon(ew.get(0), enemies.get(random));
                    }
                }
                if (randomNumber == 100) {
                    if (!ew.get(1).getCircle().isVisible() && isGamePlaying) {
                        ew.get(1).getCircle().setVisible(true);
                        ew.get(1).setEnemyWeapon(ew.get(1), enemies.get(random));
                    }
                }
                if (randomNumber == 150) {
                    if (!ew.get(2).getCircle().isVisible() && isGamePlaying) {
                        ew.get(2).getCircle().setVisible(true);
                        ew.get(2).setEnemyWeapon(ew.get(2), enemies.get(random));
                    }
                }
                if (randomNumber == 200) {
                    if (!ew.get(3).getCircle().isVisible() && isGamePlaying) {
                        ew.get(3).getCircle().setVisible(true);
                        ew.get(3).setEnemyWeapon(ew.get(3), enemies.get(random));
                    }
                }
                if (randomNumber == 250) {
                    if (!ew.get(4).getCircle().isVisible() && isGamePlaying) {
                        ew.get(4).getCircle().setVisible(true);
                        ew.get(4).setEnemyWeapon(ew.get(4), enemies.get(random));
                    }
                }

                for (Weapon enemyWeapon : ew) {
                    enemyWeapon.moveEnemyWeapon(enemyWeapon, frameDeltaTime);
                    enemyWeapon.checkEnemyWeaponCollision(enemyWeapon, gamePane);
                    enemyWeaponToPlayer(enemyWeapon, player, gamePane, menu, stage, gameMusic, startMusic,
                            wins, losses);
                }
                shields.forEach((shield) -> {
                    playerWeaponToShields(pw, shield, gamePane);
                    for (Weapon enemyWeapon : ew) {
                        enemyWeaponToShields(enemyWeapon, shield, gamePane);
                    }
                    enemies.forEach(enemy -> {
                        enemiesToShields(enemy, shield, gamePane);
                    });
                });
                shields.removeAll(removeShields);

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
        makeLabels(gamePane);
        isGamePlaying = true;
    }

    public void player(GamePane gamePane, Scene game) {
        if (!gamePane.getChildren().contains(player)) {
            player.makePlayer(gamePane, player);
            player.setVisible(true);
        }
        game.setOnMouseMoved(event -> {
            player.movePlayer(event, player);
        });
    }

    public void createEnemies(GamePane gamePane, Scene game) {
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 8; j++) {
                Vector2D enemyPosition = new Vector2D(305 + (j * 70), 40 + (i * 70));
                Vector2D enemyVelocity = new Vector2D(100.0f, 0f);
                Vector2D enemyAcceleration = new Vector2D(0, 0);
                enemies.add(new Enemy(enemyPosition, enemyVelocity, enemyAcceleration, 30));
            }
        }
        for (int i = 0; i <= 31; i++) {
            if (i <= 7) {
                enemies.get(i).getCircle().setFill(AssetManager.getJackFrost());
            }
            if (i > 7 && i <= 15) {
                enemies.get(i).getCircle().setFill(AssetManager.getBlackFrost());
            }
            if (i > 15 && i <= 23) {
                enemies.get(i).getCircle().setFill(AssetManager.getJackFrost());
            }
            if (i > 23 && i <= 31) {
                enemies.get(i).getCircle().setFill(AssetManager.getBlackFrost());
            }
            enemies.get(i).getCircle().setVisible(true);
            gamePane.getChildren().add(enemies.get(i).getCircle());
        }
    }

    public void makeLabels(GamePane gamePane) {
        lifeLabel.setFont(new Font("Comic Sans MS", 28));
        lifeLabel.setLayoutX(990);
        lifeLabel.setLayoutY(650);
        lifeLabel.setTextFill(Color.RED);
        lifeLabel.setVisible(true);
        score.setFont(new Font("Comic Sans MS", 28));
        score.setLayoutX(20);
        score.setLayoutY(650);
        score.setTextFill(Color.RED);
        score.setVisible(true);
        if (!gamePane.getChildren().contains(lifeLabel)) {
            gamePane.getChildren().add(lifeLabel);
        }
        if (!gamePane.getChildren().contains(score)) {
            gamePane.getChildren().add(score);
        }
    }

    public void makePlayerWeapon(GamePane gamePane) {
        Vector2D pwPosition = new Vector2D(player.getCenterX(), player.getCenterY() - player.getRadius());
        Vector2D pwVelocity = new Vector2D(0.0f, -450.0f);
        Vector2D pwAcceleration = new Vector2D(0, 0);
        if (pw == null) {
            pw = new Weapon(pwPosition, pwVelocity, pwAcceleration, 15);
        }
        pw.getCircle().setFill(AssetManager.getPlayerWeapon());
        pw.getCircle().setVisible(false);
        gamePane.getChildren().add(pw.getCircle());
    }

    public void makeEnemyWeapon(GamePane gamePane) {
        Vector2D ewPosition = new Vector2D(0, 0);
        Vector2D ewVelocity = new Vector2D(0.0f, 450.0f);
        Vector2D ewAcceleration = new Vector2D(0, 0);
        if (ew.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                ew.add(new Weapon(ewPosition, ewVelocity, ewAcceleration, 10));
            }
        }
        for (Weapon enemyWeapon : ew) {
            enemyWeapon.getCircle().setFill(AssetManager.getEnemyWeapon());
            enemyWeapon.getCircle().setVisible(false);
            gamePane.getChildren().add(enemyWeapon.getCircle());
        }
    }

    public void playerWeaponToEnemy(Weapon pw, Enemy enemy, GamePane gamePane) {
        double changingDistance = Math.sqrt(Math.pow((pw.getCircle().getCenterX() - enemy.getCircle().getCenterX()), 2)
                + Math.pow((pw.getCircle().getCenterY() - enemy.getCircle().getCenterY()), 2));
        double fixedDistance = pw.getCircle().getRadius() + enemy.getCircle().getRadius();
        if (pw.getCircle().isVisible()) {
            if (changingDistance <= fixedDistance) {
                AudioClip hitEnemy = AssetManager.getHitEnemySound();
                hitEnemy.play();
                scoreCount += 10;
                score.setText("Score: " + scoreCount);
                pw.getCircle().setVisible(false);
                removeEnemies.add(enemy);
                gamePane.getChildren().remove(enemy.getCircle());
            }
        }
    }

    public void enemyWeaponToPlayer(Weapon ew, Player player, GamePane gamePane, Scene menu, Stage stage,
            MediaPlayer gameMusic, MediaPlayer startMusic,
            Label wins, Label losses) {
        double changingDistance = Math.sqrt(Math.pow((ew.getCircle().getCenterX() - player.getCenterX()), 2)
                + Math.pow((ew.getCircle().getCenterY() - player.getCenterY()), 2));
        double fixedDistance = ew.getCircle().getRadius() + player.getRadius();
        if (ew.getCircle().isVisible()) {
            if (changingDistance <= fixedDistance) {
                removeLives(gamePane, menu, stage, gameMusic, startMusic,
                        wins, losses);
                ew.getCircle().setVisible(false);
            }
        }
    }

    public void playerWeaponToShields(Weapon pw, Shield shield, GamePane gamePane) {
        double changingDistance = Math.sqrt(Math.pow((pw.getCircle().getCenterX() - shield.getCenterX()), 2)
                + Math.pow((pw.getCircle().getCenterY() - shield.getCenterY()), 2));
        double fixedDistance = pw.getCircle().getRadius() + shield.getRadius();
        if (pw.getCircle().isVisible()) {
            if (changingDistance <= fixedDistance) {
                pw.getCircle().setVisible(false);
            }
        }
    }

    public void enemyWeaponToShields(Weapon ew, Shield shield, GamePane gamePane) {
        double changingDistance = Math.sqrt(Math.pow((ew.getCircle().getCenterX() - shield.getCenterX()), 2)
                + Math.pow((ew.getCircle().getCenterY() - shield.getCenterY()), 2));
        double fixedDistance = ew.getCircle().getRadius() + shield.getRadius();
        if (ew.getCircle().isVisible()) {
            if (changingDistance <= fixedDistance) {
                ew.getCircle().setVisible(false);
            }
        }
    }

    public void enemyBottom(Enemy enemy, GamePane gamePane, Scene menu, Stage stage,
            MediaPlayer gameMusic, MediaPlayer startMusic,
            Label wins, Label losses) {
        double changingDistance = Math.sqrt(Math.pow((enemy.getCircle().getCenterX() - player.getCenterX()), 2)
                + Math.pow((enemy.getCircle().getCenterY() - player.getCenterY()), 2));
        double fixedDistance = enemy.getCircle().getRadius() + player.getRadius();
        if (changingDistance <= fixedDistance) {
            stopGame(gamePane, menu, stage, gameMusic, startMusic,
                    wins, losses);
        }
        if (enemy.getCircle().getCenterY() >= 600) {
            stopGame(gamePane, menu, stage, gameMusic, startMusic,
                    wins, losses);
        }
    }

    public void enemiesToShields(Enemy enemy, Shield shield, GamePane gamePane) {
        double changingDistance = Math.sqrt(Math.pow((enemy.getCircle().getCenterX() - shield.getCenterX()), 2)
                + Math.pow((enemy.getCircle().getCenterY() - shield.getCenterY()), 2));
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
            lives.add(new Life(1100 + i * 60, 675, 25));
            lives.get(i).setFill(AssetManager.getLife());
            gamePane.getChildren().add(lives.get(i));
        }
    }

    public void makeShields(GamePane gamePane) {
        for (int i = 0; i < 4; i++) {
            shields.add(new Shield(180 + i * 300, 450, 50));
            shields.get(i).setFill(AssetManager.getShield());
            gamePane.getChildren().add(shields.get(i));
        }
    }

    public void removeLives(GamePane gamePane, Scene menu, Stage stage,
            MediaPlayer gameMusic, MediaPlayer startMusic,
            Label wins, Label losses) {
        if (lives.size() == 3) {
            AssetManager.getHitPlayerSound((int) (Math.random() * 3)).play();
            gamePane.getChildren().remove(lives.get(2));
            lives.remove(lives.get(2));
        } else if (lives.size() == 2) {
            AssetManager.getHitPlayerSound((int) (Math.random() * 3)).play();
            gamePane.getChildren().remove(lives.get(1));
            lives.remove(lives.get(1));
        } else if (lives.size() == 1) {
            gamePane.getChildren().remove(lives.get(0));
            lives.remove(lives.get(0));
            stopGame(gamePane, menu, stage, gameMusic, startMusic,
                    wins, losses);
        }
    }

    public void stopGame(GamePane gamePane, Scene menu, Stage stage,
            MediaPlayer gameMusic, MediaPlayer startMusic,
            Label wins, Label losses) {
        cleanup(gamePane);
        gameMusic.stop();
        victoryMusic = new MediaPlayer(AssetManager.getVictoryMusic());
        defeatMusic = new MediaPlayer(AssetManager.getDefeatMusic());
        if (win) {
            gamePane.setBackground(AssetManager.getVictoryImage());
            victoryMusic.play();
            victoryMusic.setOnEndOfMedia(() -> {
                victoryMusic.play();
            });
            AssetManager.getVictorySound().play();
            MenuPane.wincount++;
            wins.setText(String.valueOf(MenuPane.wincount));
            game.setOnKeyPressed((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    victoryMusic.stop();
                    stage.setScene(menu);
                    startMusic.play();
                }
            });

        } else {
            gamePane.setBackground(AssetManager.getDefeatImage());
            defeatMusic.play();
            defeatMusic.setOnEndOfMedia(() -> {
                defeatMusic.play();
            });
            AssetManager.getDefeatSound().play();
            MenuPane.losscount++;
            losses.setText(String.valueOf(MenuPane.losscount));
            game.setOnKeyPressed((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    defeatMusic.stop();
                    stage.setScene(menu);
                    startMusic.play();
                }
            });
        }
    }

    public void cleanup(GamePane gamePane) {
        if (enemies != null) {
            gamePane.getChildren().removeAll(enemies);
            for (Enemy enemy : enemies) {
                enemy.getCircle().setVisible(false);
            }
            removeEnemies.addAll(enemies);
            enemies.removeAll(removeEnemies);

        }
        if (lives != null) {
            gamePane.getChildren().removeAll(lives);
            lives.removeAll(lives);

        }
        if (shields != null) {
            gamePane.getChildren().removeAll(shields);
            removeShields.addAll(shields);
            shields.removeAll(removeShields);

        }
        for (Weapon enemyWeapon : ew) {
            enemyWeapon.getCircle().setVisible(false);
        }
        player.setVisible(false);
        pw.getCircle().setVisible(false);
        lifeLabel.setVisible(false);
        score.setVisible(false);
        isGamePlaying = false;
    }

}
