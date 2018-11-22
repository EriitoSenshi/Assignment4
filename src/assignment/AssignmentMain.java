package assignment;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author hany albouz
 */
public class AssignmentMain extends Application {
    
    
    
    @Override
    public void start(Stage primaryStage) {
        AssetManager.preloadAllAssets();
        AnchorPane startMenu = new AnchorPane();
        startMenu.setMinWidth(1280);
        startMenu.setMinHeight(720);
        startMenu.setBackground(AssetManager.getStartBackgroundImage());
        AnchorPane game = new AnchorPane();
        game.setMinWidth(1280);
        game.setMinHeight(720);
        game.setBackground(AssetManager.getBackgroundImage());
        Circle player = new Circle();
        Scene start = new Scene(startMenu);
        Scene gamePlay = new Scene(game);
        Button startButton = new Button("START GAME");
        startButton.setLayoutX(565);
        startButton.setMinHeight(200);
        startButton.setMinWidth(300);
        startButton.setOpacity(0);
        addToPane(startButton, startMenu);
        
        startButton.setOnAction((event) -> {
            primaryStage.setScene(gamePlay);
        });
        
        primaryStage.setTitle("Space invaders!");
        primaryStage.setScene(start);
        primaryStage.show();

    }

    public void addToPane(Node node, AnchorPane pane) {
        pane.getChildren().add(node);
    }

    public void player(Circle c) {

    }

    public void createEnemies(AnchorPane pane) {

    }
    
    public void loseLives(){
        
    }
    
    public void loseGame(){
        
    }

    public static void main(String[] args) {
        launch(args);
    }

}
