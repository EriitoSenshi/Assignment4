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
public class Enemy extends GameObject {

    public Enemy(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius) {
        super(position, velocity, acceleration, radius);
    }

    public void checkEdgeCollision(Enemy enemy) {
        if (enemy.getCircle().getCenterX() - enemy.getCircle().getRadius() < 0) {
                enemy.getVelocity().setX(Math.abs(enemy.getVelocity().getX()));
        }
        if (enemy.getCircle().getCenterX() - enemy.getCircle().getRadius() < 0) {
                enemy.getVelocity().setX(-Math.abs(enemy.getVelocity().getX()));
        }
    }
    
    public void moveEnemies(Enemy enemy, double time){
        enemy.update(time);
    }

}
