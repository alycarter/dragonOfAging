package com.alycarter.dragonOfAging.game;

import java.awt.Graphics;

import com.alycarter.dragonOfAging.game.states.level.Level;
import com.alycarter.dragonOfAging.game.states.level.TestLevel;

public class Game extends com.alycarter.crabClawEngine.Game{
	public Level level;
	
	public Game() {
		super("dragon of aging", 800, 600);
	}

	@Override
	public void onInitialize() {
		level =new TestLevel(this);
		level.loadLevel();
		stateMachine.push(level);
		setFrameLimit(1000);
	}
	
	@Override
	public void onRender(Graphics arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		Game game =new Game();
		game.play();
	}

}
