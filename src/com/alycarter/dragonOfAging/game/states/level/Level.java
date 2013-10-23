package com.alycarter.dragonOfAging.game.states.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.alycarter.crabClawEngine.graphics.TextureTileLoader;
import com.alycarter.crabClawEngine.state.State;
import com.alycarter.dragonOfAging.game.Game;
import com.alycarter.dragonOfAging.game.states.level.entity.Entity;
import com.alycarter.dragonOfAging.game.states.level.entity.Slime;
import com.alycarter.dragonOfAging.game.states.level.entity.player.Player;
import com.alycarter.dragonOfAging.game.states.level.graphics.Animations;
import com.alycarter.dragonOfAging.game.states.level.graphics.Textures;

public class Level implements State {
	
	public Textures textures;
	public Animations animations;
	public ArrayList<Entity> entities;
	public ArrayList<Entity> particles;
	public Player player;
	public boolean loaded = false;
	protected Game game;
	public double unitResolution;
	public boolean gameOver = false;
	
	private ArrayList<ArrayList<Boolean>> hitMap;
	public Point mapSize;
	private ArrayList<Node> openNodes;
	private Double leftOffset= new Double(-1,-0.5);
	private Double rightOffset= new Double(0,-0.5);
	private Double topOffset= new Double(-0.5,-1);
	private Double bottomOffset= new Double(-0.5,0);
	private Point spawnPoint = new Point(0,0);
	public static final boolean obstructed=false;
	public static final boolean clear=true;
	
	private BufferedImage backGround;
	private BufferedImage foreGround;
	
	
	public Level(Game game) {
		this.game=game;
		unitResolution=this.game.getResolutionWidth()/10;
	}
	
	public void loadLevel(){
		loaded=false;
		new Thread(){
			public void run() {
				onLoad();
				loaded=true;
			};
		}.start();
	}
	
	public void generateLevel(){
		Node node =new Node(new Point(0, 0), null);
		openNodes.add(node);
		applyPrefabToMap(10, node);
		spawnPoint=new Point(5, 5);
		for(int i =0; i< 50;i++){
			applyPrefabToMap((int)(Math.random()*7)+3, openNodes.get((int)(Math.random()*openNodes.size())));
		}
		int shift = (int)(game.getResolutionWidth()/2/unitResolution);
		shiftMap(shift, shift);
		appendMap(shift, shift);
		backGround= new BufferedImage(mapSize.x*(int)unitResolution, mapSize.y*(int)unitResolution, BufferedImage.TYPE_INT_ARGB);
		foreGround= new BufferedImage(mapSize.x*(int)unitResolution, mapSize.y*(int)unitResolution, BufferedImage.TYPE_INT_ARGB);
		TextureTileLoader tiles =new TextureTileLoader(Level.class.getResourceAsStream("/grass.png"), 12, 12);
		for(int x=0;x<mapSize.x;x++){
			for(int y=0;y<mapSize.y;y++){
				if(getMovable(x, y)==clear){
					backGround.getGraphics().drawImage(tiles.getTile(0), x*(int)unitResolution, y*(int)unitResolution, (int)unitResolution, (int)unitResolution, null);
				}else{
					if(getMovable(x, y+1)==clear){
						backGround.getGraphics().drawImage(tiles.getTile(1), x*(int)unitResolution, y*(int)unitResolution, (int)unitResolution, (int)unitResolution, null);
					}else{
						backGround.getGraphics().drawImage(tiles.getTile(2), x*(int)unitResolution, y*(int)unitResolution, (int)unitResolution, (int)unitResolution, null);
					}
					if(Math.random()<0.5){
						if(Math.random()<0.5){
							backGround.getGraphics().drawImage(tiles.getTile(4), x*(int)unitResolution, y*(int)unitResolution, (int)unitResolution, (int)unitResolution, null);
							foreGround.getGraphics().drawImage(tiles.getTile(5), x*(int)unitResolution, (y-1)*(int)unitResolution, (int)unitResolution, (int)unitResolution, null);
						}else{
							backGround.getGraphics().drawImage(tiles.getTile(3), x*(int)unitResolution, y*(int)unitResolution, (int)unitResolution, (int)unitResolution, null);
						}
					}
				}
			}	
		}
	}
	
	private void shiftMap(int x, int y){
		int xShifts;
		int yShifts;
		for(xShifts=0;xShifts<x;xShifts++){
			ArrayList<Boolean> newCol = new ArrayList<Boolean>();
			for(int i=0;i<mapSize.y;i++){
				newCol.add(new Boolean(obstructed));
			}
			hitMap.add(0,newCol);
		}
		mapSize.x+=x;
		for(yShifts=0;yShifts<y;yShifts++){
			for(int i=0;i<mapSize.x;i++){
				hitMap.get(i).add(0,new Boolean(obstructed));
			}
		}
		mapSize.y+=y;
		for(int i=0;i<openNodes.size();i++){
			Node temp = openNodes.get(i);
			temp.location.x+=x;
			temp.location.y+=y;
		}
		spawnPoint.x+=x;
		spawnPoint.y+=y;
	}
	
	private void appendMap(int x, int y){
		int xAdded;
		int yAdded;
		for(xAdded=0;xAdded<x;xAdded++){
			ArrayList<Boolean> newCol = new ArrayList<Boolean>();
			for(int i=0;i<mapSize.y;i++){
				newCol.add(new Boolean(obstructed));
			}
			hitMap.add(newCol);
		}
		mapSize.x+=x;
		for(yAdded=0;yAdded<y;yAdded++){
			for(int i=0;i<mapSize.x;i++){
				hitMap.get(i).add(new Boolean(obstructed));
			}
		}
		mapSize.y+=y;
	}
	
	private void applyPrefabToMap(int prefabSize, Node node){
		try {
			BufferedImage prefab;
			prefab= ImageIO.read(Level.class.getResourceAsStream("/prefabs/"+prefabSize+"x"+prefabSize+".png"));
			int x=node.location.x;
			int y= node.location.y;
			if(node.prefabOffset!=null){
				x+=Math.ceil(prefabSize*node.prefabOffset.getX());
				y+=Math.ceil(prefabSize*node.prefabOffset.getY());
			}
			int pixelx, pixely;
			boolean blocked = false;
			for(pixelx=0;pixelx<prefab.getWidth();pixelx++){
				for(pixely=0;pixely<prefab.getHeight();pixely++){
					if(getMovable(pixelx+x, pixely+y)==clear){
						blocked=true;
					}
				}
			}
			int xShift=0;
			int yShift=0;
			if(x<0){
				xShift=Math.abs(x);
				x=0;
			}
			if(y<0){
				yShift=Math.abs(y);
				y=0;
			}
			shiftMap(xShift, yShift);
			int xOver=0;
			int yOver=0;
			if(x+prefabSize>mapSize.x){
				xOver=x+prefabSize-mapSize.x;
			}
			if(y+prefabSize>mapSize.y){
				yOver=y+prefabSize-mapSize.y;
			}
			appendMap(xOver, yOver);
			for(pixelx=0;pixelx<prefab.getWidth();pixelx++){
				for(pixely=0;pixely<prefab.getHeight();pixely++){
					if(prefab.getRGB(pixelx, pixely)==Color.WHITE.getRGB()){
						setMovable(pixelx+x, pixely+y, clear);
					}
				}
			}
			if(!blocked){
				if(node.prefabOffset!=leftOffset){
					openNodes.add(new Node(new Point(x+prefabSize, y+(prefabSize/2)),rightOffset));
				}
				if(node.prefabOffset!=rightOffset){
					openNodes.add(new Node(new Point(x, y+(prefabSize/2)),leftOffset));
				}
				if(node.prefabOffset!=topOffset){
					openNodes.add(new Node(new Point(x+(prefabSize/2), y+prefabSize),bottomOffset));
				}
				if(node.prefabOffset!=bottomOffset){
					openNodes.add(new Node(new Point(x+(prefabSize/2), y),topOffset));
				}
			}
			openNodes.remove(node);
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public Point getRenderOffSet(){
		double x= ((double)game.getResolutionWidth()/2)-(player.location.x*unitResolution);
		double y= ((double)game.getResolutionHeight()/2)-(player.location.y*unitResolution);
		
		return new Point((int)x, (int) y);
	}
	
	private void onLoad(){
		gameOver=false;
		textures=new Textures();
		Animations.textures=textures;
		animations= new Animations();
		entities =new ArrayList<Entity>();
		particles= new ArrayList<Entity>();
		hitMap= new ArrayList<ArrayList<Boolean>>();
		mapSize=new Point(0, 0);
		openNodes=new ArrayList<Level.Node>();
		generateLevel();
		player =new Player(game, this, new Double(spawnPoint.x+0.5, spawnPoint.y+0.5));
		entities.add(player);
		for(int x=0;x<mapSize.x;x++){
			for(int y=0;y<mapSize.y;y++){
				if(getMovable(x, y)==clear){
					if(Math.random()*10<0.5){
//						entities.add(new Slime(game,this,new Double(x+0.5, y+0.5)));
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, game.getResolutionWidth(), game.getResolutionHeight());
		if(loaded){
			g.drawImage(backGround, getRenderOffSet().x, getRenderOffSet().y, null);
			ArrayList<Entity> sorted= new ArrayList<Entity>();
			for(int i=0;i<entities.size();i++){
				if(Entity.isEntityOnScreen(game, this, entities.get(i))){
					sorted.add(entities.get(i));
				}
			}
			for(int i=0;i<particles.size();i++){
				if(Entity.isEntityOnScreen(game, this, particles.get(i))){
					sorted.add(particles.get(i));
				}
			}
			boolean swapped;
			do{     
				swapped = false;
				for (int i = 1; i<sorted.size();i++){
					if (sorted.get(i-1).location.y >sorted.get(i).location.y){
						sorted.add(i+1,sorted.get(i-1));
						sorted.remove(i-1);
						swapped = true;
					}
				}
			}while (swapped);
			for(int i=0;i<sorted.size(); i++){
				sorted.get(i).render(g);
			}
		       
			g.drawImage(foreGround, getRenderOffSet().x, getRenderOffSet().y, null);
			for(int i=1;i<=player.health;i++){
				g.drawImage(textures.health.getTile(0), 12*i, 10, 12, 30, null);
			}
			g.setColor(Color.BLACK);
			g.drawString("rendered: "+sorted.size()+" entites: "+entities.size()+" particles: "+particles.size(), 10, 65);
			if(gameOver){
				g.drawString("game over ! press 'R' to restart", game.getResolutionWidth()/2, game.getResolutionHeight()/2);
			}
		}else{
			g.setColor(Color.BLACK);
			g.drawString("loading", game.getResolutionWidth()/2, game.getResolutionHeight()/2);
		}
		g.setColor(Color.BLACK);
		g.drawString(String.valueOf(game.getFramesLastSecond()), 10, 50);		
	}

	@Override
	public void update() {
		if (loaded){
			for(int i=0;i<entities.size(); i++){
				entities.get(i).update();
			}
			for(int i=0;i<particles.size(); i++){
				particles.get(i).update();
			}
			if(gameOver){
				if(game.getControls().isTyped(KeyEvent.VK_R)){
					loadLevel();
				}
			}
		}
	}
	
	public boolean getMovable(int x, int y){
		if(x>=0 &&y>=0 && x<mapSize.x &&y<mapSize.y){
			return hitMap.get(x).get(y).booleanValue();
		}else{
			return false;
		}
	}
	
	public void setMovable(int x, int y, boolean movable){
		if(x>=0 &&y>=0 && x<mapSize.x &&y<mapSize.y){
			hitMap.get(x).set(y, new Boolean(movable));
		}
	}
	
	class Node{
		public Point location;
		public Double prefabOffset;
		
		public Node(Point location, Double offset){
			this.location=location;
			this.prefabOffset=offset;
		}
	}
}
