package entities;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

import main.LuaJDemo;

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
				LuaJDemo.scr.stepEntity(currentEntity, time);
			}
			else
			{
				LuaJDemo.scr.stepWithoutReplication(currentEntity, time);
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
