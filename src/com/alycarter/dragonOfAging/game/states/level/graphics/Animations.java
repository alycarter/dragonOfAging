package com.alycarter.dragonOfAging.game.states.level.graphics;

import java.util.ArrayList;

import com.alycarter.crabClawEngine.graphics.TextureTileLoader;


public class Animations {

	public static Textures textures;
	
	
	public ArrayList<Animation> playerBaseAnimations = getAnimationsForPlayer(textures.playerBase);
	
	public Animation coinSpin = new Animation(textures.coin, 4, 0);
	
	public Animation  slimeLand = new Animation(textures.slime, 2,4,"land");
	public Animation  slimeStand = new Animation(textures.slime, 1,0,"stand");
	public Animation  slimeSquish = new Animation(textures.slime, 1,1,"squish");
	public Animation  slimeLeap = new Animation(textures.slime, 1,3,"leap");
	
	//items
	public ArrayList<Animation> ironChestPlate = getAnimationsForPlayer(textures.ironChestPlate);
	public ArrayList<Animation> ironArms = getAnimationsForPlayer(textures.ironArms);
	public ArrayList<Animation> ironLegs = getAnimationsForPlayer(textures.ironLegs);
	
	
	private ArrayList<Animation> getAnimationsForPlayer(TextureTileLoader spriteSheet){
		int length=4;
		ArrayList<Animation> animations = new ArrayList<Animation>();
		while(length>0){
			for(int i=0; i<=28; i+=4){
				animations.add(new Animation(spriteSheet, length, i));
			}
			length-=3;
		}
		return animations;
	}
}
