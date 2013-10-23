package com.alycarter.dragonOfAging.game.states.level.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alycarter.crabClawEngine.graphics.TextureTileLoader;

public class Animation {

	public TextureTileLoader texture;
	public ArrayList<Integer> frames = new ArrayList<Integer>();
	public String name = "";
	
	public Animation(TextureTileLoader spriteSheet, int frames, int startFrame) {
		texture=spriteSheet;
		for(int i=0;i<frames;i++){
			this.frames.add(i+startFrame);
		}
	}
	
	public Animation(TextureTileLoader spriteSheet, int frames, int startFrame, String name) {
		texture=spriteSheet;
		for(int i=0;i<frames;i++){
			this.frames.add(i+startFrame);
		}
		this.name=name;
	}
	
	public BufferedImage getFrame(int frame){
		if(frame>=0 && frame<frames.size()){
			return texture.getTile(frames.get(frame));
		}else{
			return null;
		}
		
	}
	

}
