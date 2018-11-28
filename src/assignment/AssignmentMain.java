package assignment;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author hanyalbouz
 */
public class AssignmentMain extends Application {

    double lastFrameTime = 0.0;
    Circle player = new Circle();

    ArrayList<GameObject> playerWeapons = new ArrayList<>();
    ArrayList<GameObject> enemies = new ArrayList<>();

    int playerWeapon = -1;

    @Override
    public void start(Stage primaryStage) {
        

        //Creating start menu
        MenuPane menuPane = new MenuPane();
        menuPane.makeMenuPane(primaryStage);



        //Start
        primaryStage.setTitle("Space invaders!");
        primaryStage.show();

    }

    public void createPlayerWeapon() {
        playerWeapon++;

        double circlePosX = player.getCenterX();
        double circlePosY = player.getCenterY() - player.getRadius();

        Vector2D playerWeaponPosition = new Vector2D(circlePosX, circlePosY);
        Vector2D playerWeaponVelocity = new Vector2D(0.0f, -300.0f);
        Vector2D playerWeaponAcceleration = new Vector2D(0, 0);
        playerWeapons.add(new GameObject(playerWeaponPosition, playerWeaponVelocity, playerWeaponAcceleration, 5));
        playerWeapons.get(playerWeapon).getCircle().setFill(Color.GREEN);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
