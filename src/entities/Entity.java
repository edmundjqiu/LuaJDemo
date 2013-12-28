package entities;

public class Entity {
	
	private double xCoordinate;
	private double yCoordinate;
	private double xVelocity;
	private double yVelocity;
	private double food;
	
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

}
