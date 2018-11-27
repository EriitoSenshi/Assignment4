/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class GamePane extends Pane {

    public void makeGamePane(Stage stage, GamePane gamePane) {
        AssetManager.preloadAllAssets();
        gamePane.setMinWidth(1280);
        gamePane.setMinHeight(720);
        gamePane.setBackground(AssetManager.getBackgroundImage());
        Scene game = new Scene(gamePane);
        stage.setScene(game);
        player(gamePane, game);
    }
    
    public void player(GamePane gamePane, Scene game){
        Player player = new Player();
        player.makePlayer(gamePane, player);
        game.setOnMouseMoved(event -> {
            player.movePlayer(event, player);
        });
        
    }
    
    public void enemy(){
        
    }
    
    public void playerWeapon(){
        
    }
    
    public void enemyWeapon(){
        
    }
}
