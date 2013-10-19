package com.alycarter.dragonOfAging.game.states.level;


public class Animations {

	public static Textures textures;
	
	public Animations() {
		
	}
	
	public Animation p1_playerBaseUpCenterWalk = new Animation(textures.playerBase, 4, 0);
	public Animation p2_playerBaseUpLeftWalk= new Animation(textures.playerBase, 4, 4);
	public Animation p3_playerBaseLeftWalk = new Animation(textures.playerBase, 4, 8);
	public Animation p4_playerBaseDownLeftWalk = new Animation(textures.playerBase, 4, 12);
	public Animation p5_playerBaseDownWalk = new Animation(textures.playerBase, 4, 16);
	public Animation p6_playerBaseDownRightWalk = new Animation(textures.playerBase, 4, 20);
	public Animation p7_playerBaseRightWalk = new Animation(textures.playerBase, 4, 24);
	public Animation p8_playerBaseUpRightWalk = new Animation(textures.playerBase, 4, 28);
	public Animation p9_playerBaseUpCenterStand = new Animation(textures.playerBase, 1, 0);
	public Animation p10_playerBaseUpLeftStand= new Animation(textures.playerBase, 1, 4);
	public Animation p11_playerBaseLeftStand = new Animation(textures.playerBase, 1, 8);
	public Animation p12_playerBaseDownLeftStand = new Animation(textures.playerBase, 1, 12);
	public Animation p13_playerBaseDownStand = new Animation(textures.playerBase, 1, 16);
	public Animation p14_playerBaseDownRightStand = new Animation(textures.playerBase, 1, 20);
	public Animation p15_playerBaseRightStand = new Animation(textures.playerBase, 1, 24);
	public Animation p16_playerBaseUpRightStand = new Animation(textures.playerBase, 1, 28);
	
}
