package assignment;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class AssignmentMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        //Creating start menu
        MenuPane menuPane = new MenuPane();
        menuPane.makeMenuPane(primaryStage);

        //Start
        primaryStage.setTitle("Space invaders!");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
