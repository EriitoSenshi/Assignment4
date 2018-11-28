/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

/**
 *
 * @author hanyalbouz
 */
public class Weapon extends GameObject {

    public Weapon(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius) {
        super(position, velocity, acceleration, radius);
    }

    public void movePlayerWeapon(Weapon playerWeapon, double time) {
        playerWeapon.update(time);
    }

    public void setPlayerWeapon(Weapon playerWeapon, Player player) {
        playerWeapon.setPosition(new Vector2D(player.getCenterX(), player.getCenterY() - player.getRadius()));
    }

    public void checkPlayerWeaponCollision(Weapon pw, GamePane gamePane) {
        if (pw.getCircle().getCenterY() - pw.getCircle().getRadius() < 0) {
            pw.getCircle().setVisible(false);
        }
    }
}
