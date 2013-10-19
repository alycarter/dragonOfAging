package com.alycarter.dragonOfAging.game.states.level.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Double;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;

public class Particle extends Entity {

	private double weight;
	private double life;
	private double zVelocity = 0;
	public Particle(Game game, Level level, Double location,
			Double imageSize, double height, Color color, double speed, Double direction, double weight, double life, double zVelocity) {
		super(game, level, Entity.particle, location, imageSize, imageSize.x, height,1);
		setTravelDirection(direction);
		this.speed=speed;
		this.bloodColor=color;
		this.weight=weight;
		this.life=life;
		this.zVelocity=zVelocity;
	}
	
	@Override
	public void onUpdate() {
		height-=zVelocity*game.getDeltaTime();
		zVelocity+=weight*game.getDeltaTime();
		life-=game.getDeltaTime();
		if(height<0){
			height=0;
			zVelocity=-1*zVelocity/2;
			speed-=speed*game.getDeltaTime()*weight*2;
			if(speed<0){
				speed=0;
			}
		}
		if(life<0){
			level.particles.remove(this);
		}
	}
	
	@Override
	public void onRender(Graphics g) {
		renderColorAsSprite(bloodColor, g);
	}

}
