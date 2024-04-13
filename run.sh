#!/bin/bash

# Deletes everything from translated file
true > trnsltd.kt
true > err.log

# Calls translator
kotlinc trnsltr.kt > err.log 2>&1 -include-runtime -d trnsltr.jar #Can be omitted if translator is already builded!
java -jar trnsltr.jar /Users/antoniomatijevic/Documents/Kotlin/miniN/src/main/kotlin/
echo

# Prints info
echo "Translation ended..."
echo "Running code..."
echo

# Calls translated file
kotlinc trnsltd.kt > err.log 2>&1 -include-runtime -d trnsltd.jar
java -jar trnsltd.jar
