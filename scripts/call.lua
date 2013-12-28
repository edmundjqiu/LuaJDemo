local library = require 'scripting/ExposedJavaAPI'

function print(asdf)
	value = getValue(3)
	library.print(value)
end
	