package assignment;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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

        //Creating start menu
        AnchorPane startMenu = new AnchorPane();
        startMenu.setMinWidth(1280);
        startMenu.setMinHeight(720);
        startMenu.setBackground(AssetManager.getStartBackgroundImage());
        Button startButton = new Button("");
        startButton.setLayoutX(565);
        startButton.setMinHeight(200);
        startButton.setMinWidth(300);
        startButton.setOpacity(0);
        addToPane(startButton, startMenu);

        //Creating game
        AnchorPane game = new AnchorPane();
        game.setMinWidth(1280);
        game.setMinHeight(720);
        game.setBackground(AssetManager.getBackgroundImage());

        //Creating the player and enemies
        Circle player = new Circle();
        player(player, game);
        createEnemies(game);

        Scene start = new Scene(startMenu);
        Scene gamePlay = new Scene(game);

        //Switching from game
        startButton.setOnAction((event) -> {
            primaryStage.setScene(gamePlay);
        });

        //Start
        primaryStage.setTitle("Space invaders!");
        primaryStage.setScene(start);
        primaryStage.show();

    }

    public void addToPane(Node node, AnchorPane pane) {
        pane.getChildren().add(node);
    }

    public void player(Circle c, AnchorPane pane) {
        c.setCenterX(40);
        c.setCenterY(600);
        c.setRadius(25);
        c.setFill(Color.BLUE);
        addToPane(c, pane);
    }

    public void createEnemies(AnchorPane pane) {
        ArrayList<Circle> enemies = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 8; j++) {
                enemies.add(new Circle(320 + (j * 70), 40 + (i * 70), 25, Color.YELLOW));
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            addToPane(enemies.get(i), pane);
        }
    }

    public void loseLives() {

    }

    public void loseGame() {

    }

    public static void main(String[] args) {
        launch(args);
    }

}
