package entities;

import main.LuaJDemo;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import scripting.ScriptingEngine;

public class Entity {
	
	private double xCoordinate;
	private double yCoordinate;
	private double xVelocity;
	private double yVelocity;
	private double food;

    private boolean moving = false;
    private LuaThread coroutine;
    private int imsorry = 0;
	
	public Entity()
	{
		xCoordinate = Math.random()*1024;
		yCoordinate = Math.random()*768;
		
		xVelocity = Math.random()*300;
		yVelocity = Math.random()*300;
		food = 0;
	}
	
	public Entity(double xCoordinate, double yCoordinate)
	{
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		
		xVelocity = Math.random()*300;
		yVelocity = Math.random()*300;
		food = 0;
	}
	
	public void setX(double xCoordinate)
	{
		this.xCoordinate = xCoordinate;
	}
	
	public void setY(double yCoordinate)
	{
		this.yCoordinate = yCoordinate;
	}
	
	public double getX()
	{
		return xCoordinate;
	}
	
	public double getY()
	{
		return yCoordinate;
	}
	
	public double getXVelocity()
	{
		return xVelocity;
	}
	
	public double getYVelocity()
	{
		return yVelocity;
	}
	
	public void setXVelocity(double xVelocity)
	{
		this.xVelocity = xVelocity;
	}
	
	public void setYVelocity(double yVelocity)
	{
		this.yVelocity = yVelocity;
	}
	
	public void increaseFood()
	{
		food += 5.0d;
	}
	
	public double getFood()
	{
		return food;
	}
	
	public void setFood(int food)
	{
		this.food = food;
	}

    public void move(LuaThread coroutine)
    {
        this.coroutine = coroutine;
        moving = true;
        ScriptingEngine._G.checkglobals().yield(LuaValue.varargsOf(new LuaValue[] {coroutine}));
    }

    public void update(int milliseconds)
    {

        if (moving)
        {
            LuaJDemo.scr.stepEntity(this, milliseconds);
            imsorry++;
        }

        imsorry++;

        //Around 10 seconds
        if (imsorry > 50)
        {
            moving = false;
            imsorry = 0;
            coroutine.resume(
                LuaValue.varargsOf(
                    new LuaValue[] {
                        CoerceJavaToLua.coerce(this),
                        coroutine
                    }
                )
            );
        }
    }

}
