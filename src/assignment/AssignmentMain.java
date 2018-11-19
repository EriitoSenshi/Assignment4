package assignment;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author cstuser
 */
public class AssignmentMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        AnchorPane startMenu = new AnchorPane();
        AnchorPane game = new AnchorPane();

    }

    public void addToPane(Node node, AnchorPane pane) {
        pane.getChildren().add(node);
    }

    public void player(Circle c) {

    }

    public void createEnemies(AnchorPane pane) {

    }
    
    public void lives(){
        
    }
    
    public void loseGame(){
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
