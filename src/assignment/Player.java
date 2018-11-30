/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 *
 * @author hanyalbouz
 */
public class Player extends Circle {

    public void makePlayer(GamePane gamePane, Player player) {
        player.setRadius(35);
        player.setCenterX(40);
        player.setCenterY(600);
        player.setFill(AssetManager.getPlayerSprite());
        gamePane.getChildren().add(player);

    }

    public void movePlayer(MouseEvent event, Player player) {
        player.setCenterX(event.getX());
        if (player.getCenterX() - player.getRadius() < 0) {
            player.setCenterX(25);
        }
        if (player.getCenterX() + player.getRadius() > 1280) {
            player.setCenterX(1255);
        }
    }
}
