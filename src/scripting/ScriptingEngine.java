package scripting;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

import entities.Entity;

import java.io.FileInputStream;
import java.io.IOException;

public class ScriptingEngine
{
	//Global Lua State
    //In future, do NOT make this static.
    public static LuaValue _G;

    private static class a {
        int b = 5;
        a() {}
        int getb() { return b; }
    }
    //An "empty" stand-in Varargs value
    public static final LuaValue standIn = CoerceJavaToLua.coerce(new a());


	public ScriptingEngine()
	{
		_G = JsePlatform.standardGlobals();
	}
	
	public void loadScript(String filename)
	{
        //The following works for "Globals"
		//_G.loadFile(filename).call();

        try
        {
            LoadState.load( new FileInputStream(filename), filename, _G ).call();
        } catch (IOException e) {}
	}

	public void stepEntity(Entity e, double milliseconds)
	{
		//_G.get("print").invoke(new LuaValue[] {});
		//_G.get("step").invoke(new LuaValue[] {
		//		CoerceJavaToLua.coerce(e),
		//		CoerceJavaToLua.coerce(milliseconds)
		//});

        LuaFunction a = (LuaFunction)_G.get("step");
        a.invoke(new LuaValue[]{
                CoerceJavaToLua.coerce(e),
                CoerceJavaToLua.coerce(milliseconds)
        });

	}

    public void asdf()
    {

//        Varargs result = _G.get("test").invoke(new LuaValue[] {});
//        LuaThread t = result.checkthread(1);
//        if (t != null) System.out.println("Great.");
//        t.resume(standIn);

        LuaFunction f = (LuaFunction)_G.get("myFunction");
        LuaThread t = new LuaThread(_G.checkglobals(), f);
        System.out.println(t.getStatus());


        Varargs args = LuaValue.varargsOf(
            new LuaValue[] {
                CoerceJavaToLua.coerce(Math.random() * 1024),
                CoerceJavaToLua.coerce(t)
            }
        );

        t.resume(args);
        System.out.println(t.getStatus());
        t.resume(standIn);
        System.out.println(t.getStatus());
        t.resume(standIn);
        System.out.println(t.getStatus());


    }

    public LuaThread createCoroutine(String functionName)
    {
        LuaFunction f = (LuaFunction)_G.get(functionName);
        LuaThread t = new LuaThread(_G.checkglobals(), f);
        return t;
    }
	
}
