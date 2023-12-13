#!/bin/bash

# Deletes everything from translated file
true > trnsltd.kt
true > err.log

# Calls translator
kotlinc trnsltr.kt -include-runtime -d trnsltr.jar
java -jar trnsltr.jar location_of_project
echo

# Prints info
echo "Translation ended..."
echo "Running code..."
echo

# Calls translated file
kotlinc trnsltd.kt > err.log 2>&1 -include-runtime -d trnsltd.jar
java -jar trnsltd.jar
