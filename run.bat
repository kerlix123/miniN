:: Deletes everything from translated file
type nul > trnsltd.kt
type nul > err.log

:: Calls translator
CALL kotlinc trnsltr.kt -include-runtime -d trnsltr.jar
java -jar .\trnsltr.jar
echo.

:: Prints info
echo Translation ended...
echo Running code...
echo.

:: Calls translated file
CALL kotlinc trnsltd.kt > err.log 2>&1 -include-runtime -d trnsltd.jar
java -jar .\trnsltd.jar
