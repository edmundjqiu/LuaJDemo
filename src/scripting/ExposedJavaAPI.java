package scripting;

import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.*;
import main.LuaJDemo;
import entities.*;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class ExposedJavaAPI extends TwoArgFunction {

    public ExposedJavaAPI()
    {
    	
    }

    public LuaValue call(LuaValue modname, LuaValue env) {
    	System.out.println("Setting environment");
    	
        LuaValue library = tableOf();
        	library.set( "newEntity", new newEntity());
	        library.set( "print", new print() );
        env.set( "ExposedJavaAPI", library );
  
        return library;
    }
    
    static class newEntity extends TwoArgFunction 
    {
    	public LuaValue call(LuaValue newX, LuaValue newY) {
    		Entity e = new Entity(newX.todouble(), newY.todouble());
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


            LuaJDemo.stage.addEntity(e);
            
            return LuaValue.valueOf(0);
        }
    }

    static class print extends OneArgFunction {
        public LuaValue call(LuaValue x) {
            System.out.println(x.strvalue());
            return LuaValue.valueOf(0);
        }
    }

}
