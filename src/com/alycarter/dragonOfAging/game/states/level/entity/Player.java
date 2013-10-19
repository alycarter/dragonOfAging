package com.alycarter.dragonOfAging.game.states.level.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;

public class Player extends Entity {

	private Sprite sprite;
	private double swordAngle=0;
	private boolean swordOut = false;
	private double swordOutTimer=0;
	private double aim = 0;
	private double animDelay=0;
	private double swingSpeed=360;
	
	public Player(Game game, Level level, Double location) {
		super(game, level, Entity.player ,location, new Double(1,2), 0.6,0,3);
		sprite = new Sprite(game);
		sprite.speedMultiplyer=4;
		sprite.animaitons.add(level.animations.p1_playerBaseUpCenterWalk);
		sprite.animaitons.add(level.animations.p2_playerBaseUpLeftWalk);
		sprite.animaitons.add(level.animations.p3_playerBaseLeftWalk);
		sprite.animaitons.add(level.animations.p4_playerBaseDownLeftWalk);
		sprite.animaitons.add(level.animations.p5_playerBaseDownWalk);
		sprite.animaitons.add(level.animations.p6_playerBaseDownRightWalk);
		sprite.animaitons.add(level.animations.p7_playerBaseRightWalk);
		sprite.animaitons.add(level.animations.p8_playerBaseUpRightWalk);
		sprite.animaitons.add(level.animations.p9_playerBaseUpCenterStand);
		sprite.animaitons.add(level.animations.p10_playerBaseUpLeftStand);
		sprite.animaitons.add(level.animations.p11_playerBaseLeftStand);
		sprite.animaitons.add(level.animations.p12_playerBaseDownLeftStand);
		sprite.animaitons.add(level.animations.p13_playerBaseDownStand);
		sprite.animaitons.add(level.animations.p14_playerBaseDownRightStand);
		sprite.animaitons.add(level.animations.p15_playerBaseRightStand);
		sprite.animaitons.add(level.animations.p16_playerBaseUpRightStand);
		
	}
	
	@Override
	public void onRender(Graphics g) {
		if(swordOut && animDelay<0){
			double swordLength = 1;
			Double swordTip =new Double();
			swordTip.setLocation(location);
			swordTip.x+=angleAsVector(swordAngle).x*swordLength;
			swordTip.y+=angleAsVector(swordAngle).y*swordLength;
			Double handAngle=angleAsVector(VectorAsAngle(getLookDirection())-90);
			handAngle.x*=0.3;
			swordTip.x+=handAngle.x;
			double edge =swordLength;
			while(edge>=0.2){
				level.particles.add(new SwordParticle(game, level, swordTip, new Double(0.1,0.1), 0.6, Color.LIGHT_GRAY, speed, getTravelDirection(), 0, (edge/swordLength)*0.3, 0));
				swordTip.x-= angleAsVector(swordAngle).x*0.1;
				swordTip.y-= angleAsVector(swordAngle).y*0.1;
				edge-=0.1;
			}
			animDelay=0.015;
		}
		renderImageAsSprite(sprite.getCurrentFrame(), g);
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
		int offSet = 8;
		if(x!=0||y!=0){
			offSet=0;
			setTravelDirection(new Point2D.Double(x,y));
			speed=3;
			if(swordOut){
				speed/=2;
			}
		}else{
			this.speed=0;
		}
		x=0;
		y=0;
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
			aim=VectorAsAngle(new Point2D.Double(x,y));
			if(speed==0){
				setTravelDirection(getLookDirection());
			}
			if(!swordOut){
				setLookDirection(new Point2D.Double(x,y));
				swordAngle=VectorAsAngle(getLookDirection());
				swordOut=true;
			}
			swordOutTimer=0.3;
			
		}else{
			setLookDirection(getTravelDirection());
		}
		sprite.update();
		double angle = Entity.VectorAsAngle(getLookDirection());
		double dir = (angle+180);
		dir=dir/45;
		dir=dir%8;
		sprite.currentAnimation = (int)Math.round(dir)+offSet;
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
		setLookDirection(angleAsVector(swordAngle));
		swordOutTimer-=game.getDeltaTime();
		if(swordOutTimer<0){
			swordOut=false;
		}
		animDelay-=game.getDeltaTime();
	}
	
	@Override
	public void onDeath() {
		level.gameOver=true;
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
				level.particles.remove(this);
			}
		}
		
		
		
	}

}
