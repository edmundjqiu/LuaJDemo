require ("scripting/ExposedJavaAPI")
local SPAWN_CONSTANT = 0.2 				-- Ergo 20% chance of spawning

function myFunction(data, thread)
    --[[
    ExposedJavaAPI.print(data);
    coroutine.yield();
    ExposedJavaAPI.print("Hello again.");
    coroutine.yield();
    ExposedJavaAPI.print("Son.");

    ]]--
end

function update(entity, coroutine)

    while (true) do
        ExposedJavaAPI.print("I move now.");
        entity:move(coroutine)
        if ( math.random() < SPAWN_CONSTANT ) then
        	-- spawnEntity( entity:getX(), entity:getY() );
        end
    end

end