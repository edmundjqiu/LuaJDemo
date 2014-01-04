package main;

import scripting.ScriptingEngine;
import entities.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

public class LuaJDemo extends BasicGame {

	public static ScriptingEngine scr;
	public static Stage stage;
	
	private boolean renderStage = true;
	private boolean showSamples = false;
	private boolean toggleBlocker = false;
	
	private double duration = 0;
	private double[][] samples;
	public static final int numRows = 30;
	public static final int numColumns = 10;
	private int row = 0;
	private int col = 0;
	
	public LuaJDemo(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException
	{
		//Minimum updating time
        gc.setMinimumLogicUpdateInterval(20);
        gc.setMaximumLogicUpdateInterval(20);
        
        //Setup scripting engine
		scr = new ScriptingEngine();
		scr.loadScript("scripts/entityScript.lua");
        scr.loadScript("scripts/testing.lua");

        //Test
        scr.asdf();

		//Add one entity to the stage
		stage = new Stage();
		
		//Initialize sample elements to zero
		samples = new double[numRows][numColumns];
		for (int r = 0; r < numRows; r++)
		{
			for (int c = 0; c < numColumns; c++)
			{
				samples[r][c] = 0.0d;
			}
		}
		
	}

	@Override
	public void update(GameContainer gc, int time) throws SlickException
	{
		//Update and perform benchmark timing
		Util.startStopwatch();
		stage.update(time);
		duration = Util.stopStopwatch();
		Util.resetStopwatch();
		
		//Put the duration in the matrix
		samples[row][col] = duration/1000000;
		row++;
		if (row + 1 > numRows)
		{
			row = 0;
			col++;
			if (col + 1 > numColumns) col = 0;
		}

		//Now, detect keyboard entry for continuous spawning
        Input input = gc.getInput();
        if (input.isKeyDown(Input.KEY_Z)) {

            Entity e = new Entity(500, 200);
            stage.addEntity(e);
        }

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//Render scene
		if (renderStage) stage.render(gc, g);
		
		//Clear a small area
		g.setColor(new Color(0, 0, 0));
		g.fillRoundRect(5, 30, 400, 100, 4);
		g.fillRoundRect(1, 725, 1024, 45, 4);
		if (showSamples && toggleBlocker)
		{
			g.fillRoundRect(5, 120, 1015, 600, 4);	//This is for the samples
		}
		
		//Render text
		g.setColor(new Color(127, 255, 0));
		g.drawString("Number of entities: " + stage.getSize(), 5, 30);
		g.drawString("Average ns for lua processing: " + duration, 5, 50);
		g.drawString("In microseconds: " + duration/1000, 5, 70);
		g.drawString("In milliseconds: " + duration/1000000, 5, 90);
		g.drawString("Controls: z spawns continuously, " 
					+"x spawns one at a time, "
					+"c toggles replication, and "
					+"v toggles graphics.", 5, 730);
		g.drawString("Space kills them all. Enter "
					+"displays the individual time samples "
					+"since the thing is otherwise "
					+"hard to read.", 5, 750);
		
		//Render the individual duration samples for viewing pleasure.
		if (showSamples)
		{
			g.drawString("Numbers shown are in milliseconds and "
						+"are updated every tick.", 450, 20);
			g.drawString("By the way, try seeing how rendering text "
						+"affects the FPS.", 450, 40);
			g.drawString("If you want to render, but it's blocking samples, "
						+"hit \"b\".", 450, 60);
			
			int colGap = 100;
			int rowGap = 20;
			for (int r = 0; r < numRows; r++) 
			{
				for (int c = 0; c < numColumns; c++)
				{
					g.drawString(Double.toString(samples[r][c]), 
						         10 + c*colGap,
						         120 + r*rowGap);
				}
			}
			
		}
		
	}

	/**
     * Keypress Listener
     * @param key the key mapping
     * @param c character pressed
     */
    @Override
    public void keyPressed(int key, char c) {
    	if (key == Input.KEY_X)
    	{
    		Entity e = new Entity(500, 200);
            stage.addEntity(e);
    	}
    	if (key == Input.KEY_C)
    	{
    		stage.toggleReplication();
    	}
    	if (key == Input.KEY_SPACE)
    	{
    		stage.clearEntities();
    	}
    	if (key == Input.KEY_ENTER)
    	{
    		showSamples = !showSamples;
    	}
    	if (key == Input.KEY_V)
    	{
    		renderStage = !renderStage;
    	}
    	if (key == Input.KEY_B)
    	{
    		if (showSamples) toggleBlocker = !toggleBlocker;
    	}
    	
    }
    
    
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new LuaJDemo("LuaJ Learning Demo"));
			appgc.setDisplayMode(1024, 768, false);
	        appgc.setTargetFrameRate(60);
			appgc.start();
		}
		catch (SlickException ex) {}
		
	}

}
