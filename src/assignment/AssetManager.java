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
    static private Background victoryImage = null;
    static private Background defeatImage = null;
    static private ImagePattern playerSprite = null;
    static private ArrayList<ImagePattern> enemySprites = new ArrayList<>();
    static private ImagePattern playerWeapon = null;
    static private ImagePattern enemyWeapon = null;
    static private ImagePattern life = null;
    static private ImagePattern shield = null;

    static private Media startBackgroundMusic = null;
    static private Media backgroundMusic = null;
    static private Media victoryMusic = null;
    static private Media defeatMusic = null;
    static private AudioClip startSound = null;
    static private AudioClip hitEnemySound = null;
    static private AudioClip hitShieldSound = null;
    static private ArrayList<AudioClip> hitPlayerSounds = new ArrayList<>();
    static private ArrayList<AudioClip> playerShootingSounds = new ArrayList<>();
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

        Image victoryBackground = new Image(fileURL("./assets/images/victoryBackground.png"));
        victoryImage = new Background(
                new BackgroundImage(victoryBackground,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT));

        Image defeatBackground = new Image(fileURL("./assets/images/defeatBackground.png"));
        defeatImage = new Background(
                new BackgroundImage(defeatBackground,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT));

        playerSprite = new ImagePattern(new Image(fileURL("./assets/images/playerSprite.png")));
        enemySprites.add(new ImagePattern(new Image(fileURL("./assets/images/jackFrost.png"))));
        enemySprites.add(new ImagePattern(new Image(fileURL("./assets/images/blackFrost.png"))));
        playerWeapon = new ImagePattern(new Image(fileURL("./assets/images/playerWeapon.png")));
        enemyWeapon = new ImagePattern(new Image(fileURL("./assets/images/enemyWeapon.png")));
        life = new ImagePattern(new Image(fileURL("./assets/images/life.png")));
        shield = new ImagePattern(new Image(fileURL("./assets/images/shields.png")));

        // Preload all music tracks
        backgroundMusic = new Media(fileURL("./assets/music/backgroundMusic.mp3"));
        startBackgroundMusic = new Media(fileURL("./assets/music/startBackgroundMusic.mp3"));
        victoryMusic = new Media(fileURL("./assets/music/victoryMusic.mp3"));
        defeatMusic = new Media(fileURL("./assets/music/defeatMusic.mp3"));

        // Preload all sound effects
        startSound = new AudioClip(fileURL("./assets/soundfx/gameStart.wav"));
        playerShootingSounds.add(new AudioClip(fileURL("./assets/soundfx/playerShoot1.wav")));
        playerShootingSounds.add(new AudioClip(fileURL("./assets/soundfx/playerShoot2.wav")));
        playerShootingSounds.add(new AudioClip(fileURL("./assets/soundfx/playerShoot3.wav")));
        hitPlayerSounds.add(new AudioClip(fileURL("./assets/soundfx/playerHit1.wav")));
        hitPlayerSounds.add(new AudioClip(fileURL("./assets/soundfx/playerHit2.wav")));
        hitPlayerSounds.add(new AudioClip(fileURL("./assets/soundfx/playerHit3.wav")));
        hitEnemySound = new AudioClip(fileURL("./assets/soundfx/enemyHitSound.wav"));
        defeatSound = new AudioClip(fileURL("./assets/soundfx/loss.wav"));
        victorySound = new AudioClip(fileURL("./assets/soundfx/win.wav"));
        hitShieldSound = new AudioClip(fileURL("./assets/soundfx/shield.wav"));
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

    static public ImagePattern getPlayerSprite() {
        return playerSprite;
    }

    static public ImagePattern getJackFrost() {
        return enemySprites.get(0);
    }

    static public ImagePattern getBlackFrost() {
        return enemySprites.get(1);
    }

    public static AudioClip getHitEnemySound() {
        return hitEnemySound;
    }

    public static AudioClip getHitPlayerSound(int i) {
        return hitPlayerSounds.get(i);
    }

    public static AudioClip getVictorySound() {
        return victorySound;
    }

    public static AudioClip getDefeatSound() {
        return defeatSound;
    }

    public static ImagePattern getPlayerWeapon() {
        return playerWeapon;
    }

    public static ImagePattern getLife() {
        return life;
    }

    public static ImagePattern getEnemyWeapon() {
        return enemyWeapon;
    }

    public static ImagePattern getShield() {
        return shield;
    }

    public static AudioClip getStartSound() {
        return startSound;
    }

    public static AudioClip getHitShieldSound() {
        return hitShieldSound;
    }

    public static Background getVictoryImage() {
        return victoryImage;
    }

    public static Background getDefeatImage() {
        return defeatImage;
    }

    public static Media getVictoryMusic() {
        return victoryMusic;
    }

    public static Media getDefeatMusic() {
        return defeatMusic;
    }

}
