/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author ganer
 */
public class Player extends Circle {

    public void makePlayer(GamePane gamePane, Player player) {
        player.setRadius(25);
        player.setCenterX(40);
        player.setCenterY(600);
        player.setFill(Color.BLUE);
        gamePane.getChildren().add(player);
        
    }
    
    public void movePlayer(MouseEvent event, Player player){
        player.setCenterX(event.getX());
    }
}
