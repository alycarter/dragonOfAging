package com.alycarter.dragonOfAging.game.states.level.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Double;

import com.alycarter.crabClawEngine.graphics.Animation;
import com.alycarter.crabClawEngine.graphics.TextureTileLoader;
import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;

public class Slime extends Entity {

	private Sprite sprite = new Sprite();
	private double zVelocity=0;
	private boolean inAir=false;
	private double jumpTimeout=1.5;
	
	public Slime(Game game, Level level, Double location) {
		super(game, level, Entity.enemy, location, new Double(1,1), 0.7, 0,3);
		
		Animation ani;
		ani= new Animation(game, "stand", tile, 1, 1);
		sprite.addAnimation(ani);
		ani = new Animation(game, "squish", tile, 2,1, 0.3){
			public void onAnimationEnd() {
				zVelocity=1;
				inAir=true;
				sprite.setCurrentAnimation("leap");
				setTravelDirection(getJumpDirection());
				speed=1.5;
			};
		};
		sprite.addAnimation(ani);
		ani = new Animation(game, "leap", tile, 1,3, 1);
		sprite.addAnimation(ani);
		ani = new Animation(game, "land", tile, 2,4, 0.3){
			public void onAnimationEnd() {
				sprite.setCurrentAnimation("stand");
			};
		};
		sprite.addAnimation(ani);
		sprite.setCurrentAnimation("stand");
		bloodColor= new Color(111,219,61);
	}
	
	@Override
	public void onUpdate() {
		sprite.update();
		if(inAir){
			height+=zVelocity*game.getDeltaTime();
			zVelocity-=game.getDeltaTime()*2;
			if(height<0){
				height=0;
				zVelocity=0;
				sprite.setCurrentAnimation("land");
				inAir=false;
				jumpTimeout=1+Math.random();
				speed=0;
				if(Entity.isEntityOnScreen(game, level, this)){
					for(int i=0;i<40;i++){
						Double start = new Double();
						start.y=location.y+(Math.random()*hitBoxSize.getY())-hitBoxSize.getY()/2;
						start.x=location.x+(Math.random()*hitBoxSize.getX())-hitBoxSize.getX()/2;
						Double vel = new Double();
						vel.x=start.x-location.x;
						vel.y=start.y-location.y;
						Color color = new Color(111,219,61);
						level.particles.add(new Particle(game, level, start, new Double(0.1, 0.1), 0, color, 0.5, vel, 3, 1.2*Math.random(),-1));
					}
				}
			}
		}else{
			jumpTimeout-=game.getDeltaTime();
		}
		if(sprite.getCurrentAnimation().getName().equals("stand")&&jumpTimeout<0){
			sprite.setCurrentAnimation("squish");
		}
		checkEntityCollisions();
		
	}
	
	@Override
	public void onCollide(Entity e) {
		if(e.entityType.equals(Entity.player)){
			e.damage(1);
		}
	}
	
	@Override
	public void onRender(Graphics g) {
		renderImageAsSprite(sprite.getCurrentFrame(), g);
	}
	
	private Double getJumpDirection(){
		if(!Entity.isEntityOnScreen(game, level, this)){
			return angleAsVector(Math.random()*360);	
		}else{
			Double d = new Double(location.x, location.y);
			d.x=level.player.location.x-d.x;
			d.y=level.player.location.y-d.y;
			return d;
		}
		
	}
	
	@Override
	public void onDeath() {
		level.entities.add(new Coin(game,level,location));
		
	}
	
	

	
}
