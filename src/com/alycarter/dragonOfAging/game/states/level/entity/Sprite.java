package com.alycarter.dragonOfAging.game.states.level.entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Animation;


public class Sprite {

	public ArrayList<Animation> animaitons = new ArrayList<Animation>();
	public int currentAnimation = 0;
	public double currentFrame = 0;
	public double speedMultiplyer = 1;
	public Game game;
	public Sprite(Game game) {
		this.game=game;
	}
	
	public void update(){
		currentFrame+=game.getDeltaTime()*speedMultiplyer;
		if(currentAnimation>=0 && currentAnimation<animaitons.size()){
			if(currentFrame>=animaitons.get(currentAnimation).frames.size()){
				currentFrame = 0;
			}
		}
	}
	
	public BufferedImage getCurrentFrame(){
		if(currentAnimation>=0 && currentAnimation<animaitons.size()){
			return animaitons.get(currentAnimation).getFrame((int)currentFrame);
		}else{
			return null;
		}
	}

}
