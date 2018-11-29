/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class MenuPane extends Pane {

    MediaPlayer startMusic;
    MediaPlayer gameMusic;
    
    public void makeMenuPane(Stage stage) {
        AssetManager.preloadAllAssets();
        MenuPane menuPane = new MenuPane();
        menuPane.setMinWidth(1280);
        menuPane.setMinHeight(720);
        menuPane.setBackground(AssetManager.getStartBackgroundImage());
        startMusic = new MediaPlayer(AssetManager.getStartBackgroundMusic());
        gameMusic = new MediaPlayer(AssetManager.getBackgroundMusic());
        startMusic.play();
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
        startButton.setOnAction(event ->{
            startGame(stage, menuPane, menu);
        });
    }

    public void startGame(Stage stage, MenuPane menuPane, Scene menu) {
        startMusic.stop();
        gameMusic.play();
        GamePane gamePane = new GamePane();
        gamePane.gameLoop(gamePane, stage, menuPane, menu, gameMusic, startMusic);
    }

}
