/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class MenuPane extends Pane {

    private MediaPlayer startMusic;
    private MediaPlayer gameMusic;
    private Label victories;
    private Label defeats;
    public static Label wins;
    public static Label losses;
    public static int losscount = 0;
    public static int wincount = 0;

    public void makeMenuPane(Stage stage) {
        AssetManager.preloadAllAssets();
        MenuPane menuPane = new MenuPane();
        menuPane.setMinWidth(1280);
        menuPane.setMinHeight(720);
        menuPane.setBackground(AssetManager.getStartBackgroundImage());
        victories = new Label("Victories: ");
        victories.setFont(new Font("Comic Sans MS", 20));
        victories.setTextFill(Color.GRAY);
        victories.setMinSize(100, 20);
        victories.setLayoutX(10);
        victories.setLayoutY(55);
        defeats = new Label("Defeats: ");
        defeats.setFont(new Font("Comic Sans MS", 20));
        defeats.setMinSize(100, 20);
        defeats.setTextFill(Color.GRAY);
        defeats.setLayoutX(175);
        defeats.setLayoutY(210);
        wins = new Label(String.valueOf(wincount));
        wins.setFont(new Font("Comic Sans MS", 40));
        wins.setMinSize(100, 20);
        wins.setTextFill(Color.RED);
        wins.setLayoutX(130);
        wins.setLayoutY(35);
        losses = new Label(String.valueOf(losscount));
        losses.setFont(new Font("Comic Sans MS", 40));
        losses.setMinSize(100, 20);
        losses.setTextFill(Color.RED);
        losses.setLayoutX(280);
        losses.setLayoutY(190);
        menuPane.getChildren().add(defeats);
        menuPane.getChildren().add(victories);
        menuPane.getChildren().add(wins);
        menuPane.getChildren().add(losses);
        startMusic = new MediaPlayer(AssetManager.getStartBackgroundMusic());
        gameMusic = new MediaPlayer(AssetManager.getBackgroundMusic());
        startMusic.play();
        startMusic.setOnEndOfMedia(() -> {
            startMusic.play();
        });
        Scene menu = new Scene(menuPane);
        stage.setScene(menu);
        startButton(menuPane, stage, menu);
    }

    public void startButton(MenuPane menuPane, Stage stage, Scene menu) {
        Button startButton = new Button();
        startButton.setLayoutX(565);
        startButton.setMinHeight(200);
        startButton.setMinWidth(300);
        startButton.setOpacity(0);
        menuPane.getChildren().add(startButton);
        startButton.setOnAction(event -> {
            startGame(stage, menuPane, menu);
        });
    }

    public void startGame(Stage stage, MenuPane menuPane, Scene menu) {
        startMusic.stop();
        AudioClip startGame = AssetManager.getStartSound();
        startGame.play();
        gameMusic.play();
        GamePane gamePane = new GamePane();
        gamePane.gameLoop(gamePane, stage, menuPane, menu, gameMusic, startMusic, wins, losses);
    }

}
