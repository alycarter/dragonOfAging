package com.alycarter.dragonOfAging.game.states.level.entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.graphics.Animation;


public class Sprite {

	public ArrayList<Animation> animaitons = new ArrayList<Animation>();
	private int currentAnimation = 0;
	private double currentFrame = 0;
	public double speedMultiplyer = 1;
	private Game game;
	
	public Sprite(Game game) {
		this.game=game;
	}
	
	public void update(){
		currentFrame+=game.getDeltaTime()*speedMultiplyer;
		if(currentAnimation>=0 && currentAnimation<animaitons.size()){
			if(currentFrame>=animaitons.get(currentAnimation).frames.size()){
				onAnimationEnd();
				restartAnimation();
			}
		}
	}
	
	public BufferedImage getCurrentFrame(){
		if(currentAnimation>=0 && currentAnimation<animaitons.size()){
			if(currentFrame>=animaitons.get(currentAnimation).frames.size()){
				restartAnimation();
			}
			return animaitons.get(currentAnimation).getFrame((int)currentFrame);
		}else{
			return null;
		}
	}
	
	public void setCurrentAnimationByName(String name){
		int i = 0 ;
		boolean found = false;
		while(!found && i<animaitons.size()){
			if(animaitons.get(i).name.equals(name)){
				found = true;
				currentAnimation=i;
			}
			i++;
		}
	}
	
	public void setAnimationByNumber(int number){
		if(number>=0 && number<animaitons.size()){
			currentAnimation=number;
		}
	}
	
	public int getAnimationNumber(String name){
		int i = 0 ;
		boolean found = false;
		while(!found && i<animaitons.size()){
			if(animaitons.get(i).name.equals(name)){
				found = true;
				return i;
			}
			i++;
		}
		return 0;
	}
	
	public double getCurrentFramePointer(){
		return currentFrame;
	}
	
	public void restartAnimation(){
		currentFrame=0;
	}
	
	public String getAnimationName(int number){
		if(number>=0 && number<animaitons.size()){
			return animaitons.get(number).name;
		}
		return null;
	}
	
	public int getCurrentAnimationNumber(){
		return currentAnimation;
	}
	
	public void onAnimationEnd(){
		
	}
	
	public Animation getCurrentAnimation(){
		return animaitons.get(currentAnimation);
	}

}
