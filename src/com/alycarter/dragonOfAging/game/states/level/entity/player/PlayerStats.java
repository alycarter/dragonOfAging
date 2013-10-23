package com.alycarter.dragonOfAging.game.states.level.entity.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.Level;
import com.alycarter.dragonOfAging.game.states.level.entity.player.items.Item;
import com.alycarter.dragonOfAging.game.states.level.entity.player.items.Sword;

public class PlayerStats {
	private ArrayList<Item> items = new ArrayList<Item>();
	private int coins = 0;
	private ArrayList<String> limitedItemTypeTags =new ArrayList<String>(); 
	
	public PlayerStats(Game game, Level level) {
		items.add(new Item(game, level, "ironChestPlate", Item.chest, level.animations.ironChestPlate));
		items.add(new Item(game, level, "ironArms", Item.arms, level.animations.ironArms));
		items.add(new Item(game, level, "ironLegs", Item.legs, level.animations.ironLegs));
		items.add(new Sword(game,level, "sword"));
		limitedItemTypeTags.add(Item.arms);
		limitedItemTypeTags.add(Item.legs);
		limitedItemTypeTags.add(Item.chest);
		limitedItemTypeTags.add(Item.weapon);
	}
	
	public void addItem(Item newItem){
		boolean added = false;
		for(int tag=0; tag <limitedItemTypeTags.size() && !added; tag++){
			if(limitedItemTypeTags.get(tag).equals(newItem.getTag())){
				boolean found = false;
				for(int item =0 ; item<items.size() && !found; item++){
					if(items.get(item).getTag().equals(limitedItemTypeTags.get(tag))){
						removeItem(items.get(item));
						items.add(0, newItem);
						found=true;
						added=true;
					}
				}
			}
			
		}
		if(!added){
			items.add(newItem);
		}
	}
	
	private void removeItem(Item i){
		items.remove(i);
	}
	
	public void updateAll(){
		for(int item = 0; item < items.size(); item++){
			items.get(item).onUpdate();
		}
	}
	
	public void renderAll(Graphics g){
		for(int item = 0; item < items.size(); item++){
			items.get(item).onRender(g);
		}
	}
	
	public BufferedImage getPlayerItemOverlays(int framenumber, int animationNumber){
		BufferedImage overlay = null;
		for(int item = 0; item < items.size(); item++){
			BufferedImage frame = items.get(item).getAnimationFrame(framenumber, animationNumber);
			if(overlay== null){
				overlay = new BufferedImage(frame.getWidth(), frame.getHeight(),BufferedImage.TYPE_INT_ARGB);
			}
			overlay.getGraphics().drawImage(frame, 0, 0, null);
		}
		return overlay;
	}
	
	public int getCoins(){
		return coins;
	}

}
