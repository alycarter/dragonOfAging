package com.alycarter.dragonOfAging.game.states.level.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;

public class Entity {

	public Double location;
	public double height;
	public Double imageSize;
	public Double hitBoxSize;
	private Double lookDirection;
	private Double travelDirection;
	public double speed;
	private double knockBackSpeed = 0;
	private Double knockBackDirection = new Double(0, 0);
	public String entityType;
	public Game game;
	public Level level;
	public double health;
	public double maxHealth;
	private double damageProtection =0;
	public Color bloodColor = new Color(1f, 0f, 0f);
	
	public final static String player = "player";
	public final static String enemy = "ememy";
	public final static String particle = "particle";
	public static final String Coin = "coin";
	
	public Entity(Game game, Level level, String entityType, Double location, Double imageSize, double hitBoxWidth, double height, double maxHealth) {
		this.game=game;
		this.level=level;
		this.height=height;
		this.entityType=entityType;
		this.location= new Double();
		this.location.setLocation(location);
		this.imageSize=imageSize;
		hitBoxSize=new Double(hitBoxWidth, hitBoxWidth/3);
		speed=0;
		this.health=maxHealth;
		this.lookDirection=new Double(0, 1);
		this.travelDirection=new Double(0, 1);
	}
	
	public void render(Graphics g){
		Color shadow = new Color(0f,0f,0f,0.5f);
		g.setColor(shadow);
		g.fillOval((int)((location.x-(hitBoxSize.x/2))*level.unitResolution)+level.getRenderOffSet().x,
				   (int)((location.y-(hitBoxSize.y/2))*level.unitResolution)+level.getRenderOffSet().y, 
				(int)(hitBoxSize.x*level.unitResolution), (int)(hitBoxSize.y*level.unitResolution));
		onRender(g);
	}
	
	public void onRender(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public void update(){
		onUpdate();
		Double priorLocation = new Double();
		priorLocation.setLocation(location);
		boolean collision = false;
		location.x+=travelDirection.x*speed*game.getDeltaTime();
		location.y+=travelDirection.y*speed*game.getDeltaTime();
		location.x+=knockBackDirection.x*knockBackSpeed*game.getDeltaTime();
		location.y+=knockBackDirection.y*knockBackSpeed*game.getDeltaTime();
		if(knockBackSpeed> game.getDeltaTime()){
			knockBackSpeed-=game.getDeltaTime();
		}else{
			knockBackSpeed=0;
		}
		if(checkHitmapCollision()==Level.obstructed){
			collision=true;
			location.x=priorLocation.x;
			if(checkHitmapCollision()==Level.obstructed){
				location.x+=travelDirection.x*speed*game.getDeltaTime();
				location.y=priorLocation.y;
				if(checkHitmapCollision()==Level.obstructed){
					location.x=priorLocation.x;
					location.y=priorLocation.y;
				}
			}
		}
		if(damageProtection>0){
			damageProtection-=game.getDeltaTime();
		}
		if(collision){
			onHitMapCollide();
		}
	}
	
	public void onHitMapCollide(){
		
	}
	
	public void onUpdate() {
		
	}
	
	private boolean checkHitmapCollision(){
		boolean collision=Level.clear;
		if(level.getMovable((int)(location.x-(hitBoxSize.x/2)), (int)(location.y-(hitBoxSize.y/2)))==Level.obstructed){
			collision =Level.obstructed;
		}
		if(level.getMovable((int)(location.x+(hitBoxSize.x/2)), (int)(location.y-(hitBoxSize.y/2)))==Level.obstructed){
			collision =Level.obstructed;
		}
		if(level.getMovable((int)(location.x-(hitBoxSize.x/2)), (int)(location.y+(hitBoxSize.y/2)))==Level.obstructed){
			collision =Level.obstructed;
		}
		if(level.getMovable((int)(location.x+(hitBoxSize.x/2)), (int)(location.y+(hitBoxSize.y/2)))==Level.obstructed){
			collision =Level.obstructed;
		}
		return collision;
	}
	
	public void checkEntityCollisions(){
		for(int i=0;i<level.entities.size();i++){
			Entity temp =level.entities.get(i);
			if(temp!=this){
				if(Math.abs(location.x-temp.location.x)<(temp.hitBoxSize.x/2)+(hitBoxSize.x/2)){
					if(Math.abs(location.y-temp.location.y)<(temp.hitBoxSize.y/2)+(hitBoxSize.y/2)){
						onCollide(temp);
					}
				}
			}
		}
	}
	
	public void onCollide(Entity e){
		
	}

	public void setLookDirection(Double direction){
		this.lookDirection=angleAsVector(VectorAsAngle(direction));
	}
	
	public void setTravelDirection(Double direction){
		this.travelDirection=angleAsVector(VectorAsAngle(direction));
	}
	
	public Double getLookDirection(){
		return lookDirection;
	}
	
	public Double getTravelDirection(){
		return travelDirection;
	}
	
	public static Double angleAsVector(double angle){ 
		Double vector = new Double();
		vector.x=Math.sin(Math.toRadians(angle));
		vector.y=Math.cos(Math.toRadians(angle));
		return vector;
	}
	
	public static double VectorAsAngle(Double vector){
		return Math.toDegrees(Math.atan2(vector.x, vector.y));
	};
	
	public void renderImageAsSprite(BufferedImage image, Graphics g){
		if(damageProtection>0){
			BufferedImage overlay = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
			overlay.getGraphics().drawImage(image, 0, 0, null);
			Color hurt = new Color(1f,0f,0f,0.5f);
			for(int x=0;x<overlay.getWidth();x++){
				for(int y=0;y<overlay.getHeight();y++){
					float alpha = new Color(overlay.getRGB(x, y),true).getAlpha();
					if(alpha!=0){
						overlay.setRGB(x,y,hurt.getRGB());
					}
				}	
			}
			BufferedImage tempImage = image;
			image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			image.getGraphics().drawImage(tempImage, 0, 0, null);
			image.getGraphics().drawImage(overlay,0,0,null);
		}
		g.drawImage(image,(int)((location.x-(imageSize.x/2))*level.unitResolution)+level.getRenderOffSet().x,
				   (int)((location.y-imageSize.y-height)*level.unitResolution)+level.getRenderOffSet().y,
				(int)(imageSize.x*level.unitResolution), (int)(imageSize.y*level.unitResolution),null);
	}
	
	public void renderColorAsSprite(Color c, Graphics g){
		g.setColor(c);
		g.fillRect((int)((location.x-(imageSize.x/2))*level.unitResolution)+level.getRenderOffSet().x,
				   (int)((location.y-imageSize.y-height)*level.unitResolution)+level.getRenderOffSet().y,
				(int)(imageSize.x*level.unitResolution), (int)(imageSize.y*level.unitResolution));
	}
	
	public void damage(double damage){
		if(damageProtection<=0){
			health-=damage;
			if(health<=0){
				level.entities.remove(this);
				onDeath();
				for(int i=0;i<30;i++){
					level.particles.add(new Particle(game, level, location, new Double(0.1, 0.1), Math.random()*imageSize.y, bloodColor, 0.6, angleAsVector(Math.random()*360), 3, 3*Math.random(),Math.random()*3));
				}
			}
			damageProtection=0.5;
		}
	}
	
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

	public static boolean isEntityOnScreen(Game g, Level l, Entity e){
		double x=0;
		double y=0;
		x=e.location.x*l.unitResolution;
		y=e.location.y*l.unitResolution;
		x+= l.getRenderOffSet().x;
		y+=l.getRenderOffSet().y;
		if(x<g.getResolutionWidth()/2){
			x+=e.imageSize.x*l.unitResolution;
		}
		if(x>g.getResolutionWidth()/2){
			x-=e.imageSize.x*l.unitResolution;
		}
		if(y<g.getResolutionHeight()/2){
			y+=e.imageSize.y*l.unitResolution;
		}
		if(y>g.getResolutionHeight()/2){
			y-=e.imageSize.y*l.unitResolution;
		}
		if((x>0)&&(y>0)&&(x<g.getResolutionWidth())&&(y<g.getResolutionHeight())){
			return true;
		}else{
			return false;
		}
	}
	
	public void knockBack(Double direction, double speed){
		knockBackDirection=angleAsVector(VectorAsAngle(direction));
		knockBackSpeed=speed;
	}
	
}
