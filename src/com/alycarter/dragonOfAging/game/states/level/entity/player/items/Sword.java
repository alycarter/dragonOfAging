package com.alycarter.dragonOfAging.game.states.level.entity.player.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;
import com.alycarter.dragonOfAging.game.states.level.entity.Entity;
import com.alycarter.dragonOfAging.game.states.level.entity.Particle;

public class Sword extends Item {
	private double swordAngle=0;
	private boolean swordOut = false;
	private double swordOutTimer=0;
	private double aim = 0;
	private double animDelay=0;
	private double swingSpeed=360;
	
	public Sword(Game game, Level level, String name) {
		super(game, level, name, Item.weapon, null);

	}
	
	@Override
	public void onRender(Graphics g) {
		if(swordOut && animDelay<0){
			double swordLength = 1;
			Double swordTip =new Double();
			swordTip.setLocation(level.player.location);
			swordTip.x+=Entity.angleAsVector(swordAngle).x*swordLength;
			swordTip.y+=Entity.angleAsVector(swordAngle).y*swordLength;
			Double handAngle=Entity.angleAsVector(Entity.VectorAsAngle(level.player.getLookDirection())-90);
			handAngle.x*=0.3;
			swordTip.x+=handAngle.x;
			double edge =swordLength;
			while(edge>=0.2){
				level.particles.add(new SwordParticle(game, level, swordTip, new Double(0.1,0.1), 0.6, Color.LIGHT_GRAY, level.player.speed, level.player.getTravelDirection(), 0, (edge/swordLength)*0.3, 0));
				swordTip.x-= Entity.angleAsVector(swordAngle).x*0.1;
				swordTip.y-= Entity.angleAsVector(swordAngle).y*0.1;
				edge-=0.1;
			}
			animDelay=0.015;
		}
		
	
	}
	
	@Override
	public void onUpdate() {
		if(swordOut){
			level.player.speed/=2;
		}
		int x=0;
		int y=0;
		if(game.getControls().isPressed(KeyEvent.VK_L)){
			x+=1;
		}
		if(game.getControls().isPressed(KeyEvent.VK_J)){
			x-=1;
		}
		if(game.getControls().isPressed(KeyEvent.VK_I)){
			y-=1;
		}
		if(game.getControls().isPressed(KeyEvent.VK_K)){
			y+=1;
		}
		if(x!=0||y!=0){
			aim=Entity.VectorAsAngle(new Point2D.Double(x,y));
			if(level.player.speed==0){
				level.player.setTravelDirection(level.player.getLookDirection());
			}
			level.player.setLookDirection(new Point2D.Double(x,y));
			if(!swordOut){
				swordAngle=Entity.VectorAsAngle(level.player.getLookDirection());
				swordOut=true;
			}
			swordOutTimer=0.3;
			
		}else{
			if(swordOutTimer>0){
				level.player.setLookDirection(Entity.angleAsVector(swordAngle));
			}
		}
		double angle = Entity.VectorAsAngle(level.player.getLookDirection());
		double dir = (angle+180);
		dir=dir/45;
		dir=dir%8;
		level.player.sprite.setAnimationByNumber((int)Math.round(dir)+level.player.offSet);
		angle = aim - swordAngle;
		if (angle > 180){
			angle -= 360;
		}
		if( angle < -180){
			angle += 360;
		}
		if(angle>0){
			swordAngle+=game.getDeltaTime()*swingSpeed;
			
		}else{
			if(angle<0){
				swordAngle-=game.getDeltaTime()*swingSpeed;
			}
		}
		swordAngle= swordAngle%360;
		level.player.setLookDirection(Entity.angleAsVector(swordAngle));
		swordOutTimer-=game.getDeltaTime();
		if(swordOutTimer<0){
			swordOut=false;
		}
		animDelay-=game.getDeltaTime();
	}
	class SwordParticle extends Particle {

		public SwordParticle(Game game, Level level, Double location,
				Double imageSize, double height, Color color, double speed,
				Double direction, double weight, double life, double zVelocity) {
			super(game, level, location, imageSize, height, color, speed, direction,
					weight, life, zVelocity);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onUpdate() {
			super.onUpdate();
			checkEntityCollisions();
		}
		
		@Override
		public void onCollide(Entity e) {
			if(e.entityType.equals(Entity.enemy)){
				e.damage(1);
				e.knockBack(new Double(e.location.x-level.player.location.x, e.location.y-level.player.location.y), 1);
				level.particles.remove(this);
			}
		}
		
		
		
	}


}
