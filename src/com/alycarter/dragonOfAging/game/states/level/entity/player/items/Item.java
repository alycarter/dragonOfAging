package com.alycarter.dragonOfAging.game.states.level.entity.player.items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;
import com.alycarter.dragonOfAging.game.states.level.graphics.Animation;

public class Item {
	
	private ArrayList<Animation> overlayAnimations;
	private String name;
	protected Game game;
	protected Level level;
	private String itemTag;
	
	public static final String chest = "chest";
	public static final String arms = "arms";
	public static final String legs = "legs";
	public static final String weapon = "weapon";
	
	public Item(Game game, Level level, String name, String tag, ArrayList<Animation> overlayAnimations) {
		this.overlayAnimations=overlayAnimations;
		this.name= name;
		this.itemTag=tag;
		this.level = level;
		this.game=game;
	}
	
	public String getTag(){
		return itemTag;
	}
	
	public String getName(){
		return name;
	}
	
	public void onUpdate(){
		
	}
	
	public void onRender(Graphics g){
		
	}
	//getIemPickUpEntity method
	public BufferedImage getAnimationFrame(int frame, int animation){
		if(overlayAnimations != null){
			return overlayAnimations.get(animation).getFrame(frame);
		}else{
			return null;
		}
	}
	
	
	

}
