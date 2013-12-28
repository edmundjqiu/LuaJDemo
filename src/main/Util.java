package main;

public class Util {
	//All times in nanoseconds, including accumulated time.
	public static double lastStartTime;
	public static double accumulatedTime = 0.0d;

	
	public static double getNanoTime()
	{
		return System.nanoTime();
	}
	
	public static double nanosecondToSeconds(double nanoseconds)
	{
		return nanoseconds / 1000000000;
	}
	
	public static void startStopwatch()
	{
		lastStartTime = getNanoTime();
	}
	
	public static double stopStopwatch()
	{
		double currentTime = getNanoTime();
		double elapsedTime = currentTime - lastStartTime;
		accumulatedTime += elapsedTime;
		return accumulatedTime;
	}
	
	public static void resetStopwatch()
	{
		accumulatedTime = 0.0d;
	}
}
