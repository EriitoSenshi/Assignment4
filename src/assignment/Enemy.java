/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

/**
 *
 * @author ganer
 */
public class Enemy extends GameObject {

    public Enemy(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius) {
        super(position, velocity, acceleration, 0);
    }
}
