package entities;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;

import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

import main.LuaJDemo;
import scripting.ScriptingEngine;

public class Stage
{
	private LinkedList<Entity> entities;
	private Stack<Entity> newEntities;
	
	private boolean replication = true;
	
	public Stage()
	{
		entities = new LinkedList<Entity>();
		newEntities = new Stack<Entity>();
	}
	
	public void update(int time)
	{
		//If there are new entities born, add them to entities list
		while (newEntities.size() > 0)
		{
			entities.add(newEntities.pop());
		}
		
		//Update all current entities
		ListIterator iter = entities.listIterator();
		while (iter.hasNext())
		{
			Entity currentEntity = (Entity)iter.next();
			
			if (replication)
			{
				//LuaJDemo.scr.stepEntity(currentEntity, time);
                currentEntity.update(time);
			}
		}
		
	}

	public void render(GameContainer gc, Graphics g)
	{
		//Green
		g.setColor(new Color(127, 255, 0));
		
		ListIterator iter = entities.listIterator();
		while (iter.hasNext())
		{
			Entity currentEntity = (Entity)iter.next();
			g.drawOval((float)currentEntity.getX(), 
					   (float)currentEntity.getY(), 7, 7);
		}
	}
	
	public LinkedList<Entity> getEntityList()
	{
		return entities;
	}
	
	public void addEntity(Entity e)
	{
		newEntities.add(e);

        //What differentiates this test is that we're having it controlled by a
        //LuaThread coroutine script.
        LuaThread coroutine = LuaJDemo.scr.createCoroutine("update");

        Varargs args = LuaValue.varargsOf(
                new LuaValue[]{
                        CoerceJavaToLua.coerce(e),
                        CoerceJavaToLua.coerce(coroutine)
                }
        );

        coroutine.resume(args);

    }
	
	public void clearEntities()
	{
		entities.clear();
	}
	
	public void toggleReplication()
	{
		replication = !replication;
	}
	
	public int getSize()
	{
		return entities.size();
	}

	
}
