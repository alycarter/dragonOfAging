package com.alycarter.dragonOfAging.game.states.level.entity;

import java.awt.Graphics;
import java.awt.geom.Point2D.Double;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;

public class Coin extends Entity {

	private Sprite sprite;
	
	public Coin(Game game, Level level, Double location) {
		super(game, level, Entity.Coin, location, new Double(5d/12d, 5d/12d), 5d/12d,0, 1);
		sprite = new Sprite(game);
		sprite.animaitons.add(level.animations.coinSpin);
		sprite.setAnimationByNumber(0);
		sprite.speedMultiplyer=7;
		
	}
	
	@Override
	public void onUpdate() {
		sprite.update();
		checkEntityCollisions();
	}
	
	@Override
	public void onRender(Graphics g) {
		renderImageAsSprite(sprite.getCurrentFrame(), g);
	}
	
	@Override
	public void onCollide(Entity e) {
		if(e.entityType.equals(Entity.player)){
			level.entities.remove(this);
		}
	}

}
