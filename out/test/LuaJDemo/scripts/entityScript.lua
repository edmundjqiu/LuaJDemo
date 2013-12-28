-- Load constants and libraries
local library = require 'scripting/ExposedJavaAPI'
local SPAWN_CONSTANT = 0.6 				-- Ergo 60% chance of spawning

-- Just testing something...
function print(asdf)
	value = getValue(3);
	library.print(value);
end

-- This is called for every 20 milliseconds for the 'entities' in the test
function step(entity, milliseconds)

	-- Move
	moveEntity(entity, milliseconds)
	
	-- If we get to 20000 food, then something might spawn!!
	entity:increaseFood();
	foodCount = entity:getFood();		--library.print("Food..."..foodCount);
	if (foodCount > 600) then
		if ( math.random() < SPAWN_CONSTANT ) then
			spawnEntity( entity:getX(), entity:getY() );
		end
		
		entity:setFood(0);
	end
	
end

function moveEntity(entity, milliseconds)

	-- Gather information
	scaledTick = milliseconds/1000		--library.print(milliseconds);
	x = entity:getX();					--library.print("Location..."..x.." "..y);
	y = entity:getY();
	
	-- Move.
	newX = x + entity:getXVelocity()*scaledTick;
	newY = y + entity:getYVelocity()*scaledTick;
	
	if ( ( newX > 0 and newX < 1024 ) and ( newY > 0 and newY < 768 ) ) then
		entity:setX(newX);
		entity:setY(newY);
	else
		entity:setXVelocity(-1.0 * entity:getXVelocity());
		entity:setYVelocity(-1.0 * entity:getYVelocity());
	end
end

function spawnEntity(x, y)
	library.newEntity(x, y);
end






