package com.alycarter.dragonOfAging.game.states.level.entity;

import java.awt.Graphics;
import java.awt.geom.Point2D.Double;

import com.alycarter.crabClawEngine.graphics.Animation;
import com.alycarter.crabClawEngine.graphics.TextureTileLoader;
import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;

public class Coin extends Entity {

	private Sprite sprite = new Sprite();
	
	public Coin(Game game, Level level, Double location) {
		super(game, level, Entity.Coin, location, new Double(5d/12d, 5d/12d), 5d/12d,0, 1);
		sprite.addAnimation(new Animation(game, "spin", t, 4, 0.5));
		
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
