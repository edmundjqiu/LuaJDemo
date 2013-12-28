package scripting;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.*;
import main.LuaJDemo;
import entities.*;

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
