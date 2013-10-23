package com.alycarter.dragonOfAging.game.states.level.entity.player;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;
import com.alycarter.dragonOfAging.game.states.level.entity.Entity;
import com.alycarter.dragonOfAging.game.states.level.entity.Sprite;

public class Player extends Entity {

	public Sprite sprite;
	private PlayerStats stats;
	public int offSet;
	
	public Player(Game game, Level level, Double location) {
		super(game, level, Entity.player ,location, new Double(1,2), 0.6,0,50);
		sprite = new Sprite(game);
		sprite.speedMultiplyer=4;
		sprite.animaitons=level.animations.playerBaseAnimations;
		stats= new PlayerStats(game, level);
	}
	
	@Override
	public void onRender(Graphics g) {		
		renderImageAsSprite(sprite.getCurrentFrame(), g);
		renderImageAsSprite(stats.getPlayerItemOverlays((int)sprite.getCurrentFramePointer(), sprite.getCurrentAnimationNumber()), g);
		stats.renderAll(g);
	}
	
	@Override
	public void onUpdate() {
		int x=0;
		int y=0;
		if(game.getControls().isPressed(KeyEvent.VK_D)){
			x+=1;
		}
		if(game.getControls().isPressed(KeyEvent.VK_A)){
			x-=1;
		}
		if(game.getControls().isPressed(KeyEvent.VK_W)){
			y-=1;
		}
		if(game.getControls().isPressed(KeyEvent.VK_S)){
			y+=1;
		}
		offSet = 8;
		if(x!=0||y!=0){
			offSet=0;
			setTravelDirection(new Point2D.Double(x,y));
			setLookDirection(getTravelDirection());
			speed=3;
		}else{
			this.speed=0;
		}
		double angle = Entity.VectorAsAngle(level.player.getLookDirection());
		double dir = (angle+180);
		dir=dir/45;
		dir=dir%8;
		level.player.sprite.setAnimationByNumber((int)Math.round(dir)+level.player.offSet);
		stats.updateAll();
		sprite.update();
	}
	
	@Override
	public void onDeath() {
		level.gameOver=true;
	}
	
	
}
