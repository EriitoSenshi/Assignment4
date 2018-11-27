/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author ganer
 */
public class MenuPane extends Pane {

    public void makeMenuPane(Pane menuPane, Stage stage, Scene scene, Button startButton) {
        AssetManager.preloadAllAssets();
        menuPane.setMinWidth(1280);
        menuPane.setMinHeight(720);
        menuPane.setBackground(AssetManager.getStartBackgroundImage());
        startButton.setLayoutX(565);
        startButton.setMinHeight(200);
        startButton.setMinWidth(300);
        startButton.setOpacity(0);
        menuPane.getChildren().add(startButton);
    }

}
