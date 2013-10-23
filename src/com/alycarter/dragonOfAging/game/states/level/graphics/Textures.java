package com.alycarter.dragonOfAging.game.states.level.graphics;

import com.alycarter.crabClawEngine.graphics.TextureTileLoader;

public class Textures {
	public Textures() {
		
	}
		
	public TextureTileLoader playerBase = new TextureTileLoader(Textures.class.getResourceAsStream("/playerBase.png"), 12, 24);
	
	public TextureTileLoader coin = new TextureTileLoader(Textures.class.getResourceAsStream("/coin.png"), 5, 5);
	
	public TextureTileLoader slime = new TextureTileLoader(Textures.class.getResourceAsStream("/slime.png"),12,12);
	
	//public TextureTileLoader heart = new TextureTileLoader(Textures.class.getResourceAsStream("/heart.png"),10,10);
	
	public TextureTileLoader health = new TextureTileLoader(Textures.class.getResourceAsStream("/health.png"),4,10);
	
	//items
	public TextureTileLoader ironChestPlate = new TextureTileLoader(Textures.class.getResourceAsStream("/ironChestPlate.png"),12,24);	
	public TextureTileLoader ironArms = new TextureTileLoader(Textures.class.getResourceAsStream("/ironArms.png"),12,24);	
	public TextureTileLoader ironLegs = new TextureTileLoader(Textures.class.getResourceAsStream("/ironLegs.png"),12,24);	

}
