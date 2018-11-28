/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
        enemy(gamePane, game);
    }
    
    public void player(GamePane gamePane, Scene game){
        Player player = new Player();
        player.makePlayer(gamePane, player);
        game.setOnMouseMoved(event -> {
            player.movePlayer(event, player);
        });
        
    }
    
    public void enemy(GamePane gamePane, Scene game){
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 8; j++) {
                Vector2D enemyPosition = new Vector2D(320 + (j * 70), 40 + (i * 70));
                Vector2D enemyVelocity = new Vector2D(100.0f, 1.0f);
                Vector2D enemyAcceleration = new Vector2D(0, 0);
                enemies.add(new Enemy(enemyPosition, enemyVelocity, enemyAcceleration, 25));

            }
        }
        enemies.stream().map((enemy) -> {
            enemy.getCircle().setFill(Color.YELLOW);
            return enemy;
        }).forEachOrdered((enemy) -> {
            gamePane.getChildren().add(enemy.getCircle());
        });
    }
    
    public void playerWeapon(){
        
    }
    
    public void enemyWeapon(){
        
    }
}
