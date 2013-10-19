package com.alycarter.dragonOfAging.game.states.level;

import com.alycarter.crabClawEngine.graphics.TextureTileLoader;

public class Textures {
	public Textures() {
		
	}
		
	public TextureTileLoader playerBase = new TextureTileLoader(Textures.class.getResourceAsStream("/player.png"), 12, 24);
	
	public TextureTileLoader coin = new TextureTileLoader(Textures.class.getResourceAsStream("/coin.png"), 5, 5);
	
	public TextureTileLoader slime = new TextureTileLoader(Textures.class.getResourceAsStream("/slime.png"),12,12);
	
	

}
