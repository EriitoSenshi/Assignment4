/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.ArrayList;

/**
 *
 * @author hanyalbouz
 */
public class Enemy extends GameObject {

    public Enemy(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius) {
        super(position, velocity, acceleration, radius);
    }

    public void checkEdgeCollision(Enemy enemy, ArrayList<Enemy> enemies) {
        if (enemy.getCircle().getCenterX() - enemy.getCircle().getRadius() < 0) {
            enemies.forEach((e) -> {
                e.getVelocity().setX(Math.abs(e.getVelocity().getX()));
            });
        }
        if (enemy.getCircle().getCenterX() + enemy.getCircle().getRadius() > 1280) {
            enemies.forEach((e) -> {
                e.getVelocity().setX(-Math.abs(e.getVelocity().getX()));
            });
        }
    }

    public void moveEnemies(Enemy enemy, double time, ArrayList<Enemy> enemies) {
        enemy.update(time);
        enemy.checkEdgeCollision(enemy, enemies);
    }

}
