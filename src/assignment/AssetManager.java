package assignment;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.ImagePattern;

public class AssetManager {

    static private Background startBackgroundImage = null;
    static private Background backgroundImage = null;
    static private ImagePattern playerSprite = null;
    static private ArrayList<ImagePattern> enemySprites = null;

    static private Media startBackgroundMusic = null;
    static private Media backgroundMusic = null;
    static private AudioClip hitEnemySound = null;
    static private AudioClip hitPlayerSound = null;
    static private ArrayList<AudioClip> playerShootingSounds = null;
    static private AudioClip enemyShootingSound = null;
    static private AudioClip victorySound = null;
    static private AudioClip defeatSound = null;

    static private String fileURL(String relativePath) {
        return new File(relativePath).toURI().toString();
    }

    static public void preloadAllAssets() {
        // Preload all images
        Image startBackground = new Image(fileURL("./assets/images/startBackground.png"));
        startBackgroundImage = new Background(
                new BackgroundImage(startBackground,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT));

        Image background = new Image(fileURL("./assets/images/background.png"));
        backgroundImage = new Background(
                new BackgroundImage(background,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT));

        playerSprite = (new ImagePattern(new Image(fileURL("./assets/images/playerSprite.png"))));

        // Preload all music tracks
        backgroundMusic = new Media(fileURL("./assets/music/backgroundMusic.mp3"));
        startBackgroundMusic = new Media(fileURL("./assets/music/startBackgroundMusic.mp3"));

        // Preload all sound effects
        /*hitEnemySound = new AudioClip(fileURL("./assets/soundfx/newPlanet.wav"));
        playerShootingSound = new AudioClip(fileURL("./assets/soundfx/shooting.wav"));
        enemyShootingSound = new AudioClip(fileURL("./assets/soundfx/...wav"));*/
    }

    public static AudioClip getPlayerShootingSounds(int i) {
        return playerShootingSounds.get(i);
    }

    static public Background getBackgroundImage() {
        return backgroundImage;
    }

    static public Background getStartBackgroundImage() {
        return startBackgroundImage;
    }

    static public Media getBackgroundMusic() {
        return backgroundMusic;
    }

    static public Media getStartBackgroundMusic() {
        return startBackgroundMusic;
    }
    
    static public ImagePattern getPlayerSprite(){
        return playerSprite;
    }
    
    static public ImagePattern getEnemySprites(int i){
        return enemySprites.get(i);
    }

    public static AudioClip getHitEnemySound() {
        return hitEnemySound;
    }

    public static AudioClip getHitPlayerSound() {
        return hitPlayerSound;
    }

    public static AudioClip getEnemyShootingSound() {
        return enemyShootingSound;
    }

    public static AudioClip getVictorySound() {
        return victorySound;
    }

    public static AudioClip getDefeatSound() {
        return defeatSound;
    }
}
