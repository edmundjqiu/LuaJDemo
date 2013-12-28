package scripting;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

import entities.Entity;

public class ScriptingEngine
{
	public Globals _G;
	
	public ScriptingEngine()
	{
		_G = JsePlatform.standardGlobals();
	}
	
	public void loadScript(String filename)
	{
		_G.loadFile(filename).call();
	}

	public void stepEntity(Entity e, double milliseconds)
	{
		//_G.get("print").invoke(new LuaValue[] {});
		_G.get("step").invoke(new LuaValue[] {
				CoerceJavaToLua.coerce(e),
				CoerceJavaToLua.coerce(milliseconds)
		});
	}
	
	public void stepWithoutReplication(Entity e, double milliseconds)
	{
		//_G.get("print").invoke(new LuaValue[] {});
		_G.get("moveEntity").invoke(new LuaValue[] {
				CoerceJavaToLua.coerce(e),
				CoerceJavaToLua.coerce(milliseconds)
		});
	}
	
	public void spawnRandomEntity()
	{
		_G.get("spawnEntity").invoke(new LuaValue[] {
				CoerceJavaToLua.coerce(Math.random() * 1024),
				CoerceJavaToLua.coerce(Math.random() * 768)
		});
	}
	
	
}
