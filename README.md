# miniN
miniN is one line programing language which is transpiled to Kotlin by transpiler written in Kotlin.
***
## Syntax

    line_name function function_parameters
Function names can be uppercase or lowercase.

## Line naming
In miniN every line of code can have it's own name if it will be later used in some of the functions like "EXE", "WHILE", "FOR"...

Every line needs to have it's specific name!!!

If you want code from line to be used in main section of the program line name should be "~". 

We will explain line naming better for each function in the following chapters.

## LBF and LBAF
First line of code in miniN specifies style of writing code.

Every program in miniN needs to start with defining the style of code writing

    # LBF
LBF is normal style of writing code in miniN. LBF (Lines Before Functions) tells transpiler that you will define
lines that are going to be executed in function before functions like EXE, WHILE, FOR...

    # LBAF
If you use LBAF (Lines Before and After Functions) you can define the line that will be executed in function before and after the function.

### Which one should you use?
If you need your program to run faster you should use LBF.

If you need simpler writing of code you should use LBAF.

**Note that if you have a lot of lines LBAF will be much slower!**

## Comments
Comment is every line which name is "?".
    
    ? this is comment

## EXE
"EXE" is the most important function in miniN programing language. This function is used for executing lines that are named.

    A PRINT "Hello, World!"
    ~ EXE A

You can execute more lines by putting "&" between name of the lines.

*And you can execute a "EXE" function =).
    
    A PRINT "Hello, World!"
    B EXE A
    ~ EXE B
    
**But then the A line will be executed as many times as there are EXE functions calling eachother!** (In this example A line will be executed twice)
## Output
As you saw in the previous chapter we use "PRINT" to output text.

    ~ PRINT "miniN"

## Variables
Variables in miniN are written like this:

    ~ VAR a = 10

If you want your variable to be unchagable instead of "VAR" you use "VAL":
    
    ~ VAL a = 10

To output variable you use "$" before the name of the variable in "PRINT" function:

    ~ VAR a = 10
    ~ PRINT "a = $a"

Or if you want to output only one variable without text before or after it you can do it like this:

    ~ VAR a = 10
    ~ PRINT a

## Input
To input value of variables in miniN we use function "IN":

    ~ VAR a
    ~ IN type a


#### Inputing integer
    ~ VAR a
    ~ IN INT a

#### Inputing decimal numbers
    ~ VAR a
    ~ IN DOUBLE a

Variables in which you input something need to be of type "VAR"!!!

## IF, ELIF & ELSE
So, now we are going to see the funniest part of miniN programing language.

In most other programing languages "IF, ELIF & ELSE" statements, "FOR" and "WHILE" loops are multi-line, but in miniN everything is written in one line and that is why we have possibility of line naming.
Let's take a look on how to write "IF, ELIF & ELSE" statements".

    ~ VAR a = 0
    ~ IN a
    A PRINT "a is smaller than 3."
    B PRINT "a is 3."
    C PRINT "a is bigger than 3."
    D PRINT "a: $a
    ~ IF [a<3] A&D
    ~ ELIF [a==3] B
    ~ ELSE C&D

In the example above if the "a" is smaller than 3 the lines "A" and "D" will be executed.

If we have more lines we want to execute we use "&" between them as you can see in "IF" and "ELSE" statements.

## While loop
Here is how to write a while loop in miniN:
    
    ~ VAR a = 0
    A PRINT "$a"
    B a += 1
    ~ WHILE [a<5] A&B

The example above outputs numbers from 0 to 4.

## For loop
For loop in miniN is written like this:

    A PRINT "$i"
    ~ FOR [i > 0..5] A

In this example the program outputs numbers from 0 to 5.

## Loop functions 
Inside a loop you can use "BREAK" or "CONTINUE" functions to break or continue a loop.

## ...line_name
You can use ...line_name operator to say to the function to take and execute all the lines while the name of line is not same as the line_name or till the end of program.

The line at line_name will not be executed.

    # LBAF
    ~ FOR [i > 0..<4] ...B1
    A1 PRINT "lele"
    B1 PRINT "leel"
In the example above line B1 is not taken in the FOR function.

    # LBAF
    ~ FOR [i > 0..<4] ...
    A1 PRINT "lele"
    B1 PRINT "leel"
In the example above FOR function will take every line until the empty line.

You can use it in EXE, IF, ELIF, ELSE, WHILE, FOR, FUN.

## Lists
Lists in miniN are written like this:

    ~ VAR list_name = [element_1, element_2, element_N]
### Changing value of list elements
    
    ~ list_name[index] = new_value
### Outputing list elements

    ~ PRINT "${list_name[index]}"
### Getting list size
    
    ~ VAR size = list_name.size
Outputing list size:

    ~ PRINT "${list_name.size}"

## Functions
Functions in miniN are the most confusing part of miniN.

Functions in miniN are written like this:

    ~ FUN fun_name[parameter_1: parameter_type, parameter_N: parameter_type] line_1&line_2

Functions can`t have line names.
Every function needs to have a return value!!!

Here is the example for how to make a function that returns a sum of two integers:
    
    ~ FUN sum[a: Int, b: Int] A
    A RETURN a+b
    ~ PRINT "${sum(1, 2)}

The code above outputs "3".

Please note that the PRINT is not part of the function.

If we want to store value of sum in variable we will do it like this:
    
    ~ VAR a = sum(1, 2)

Another example:

    ~ FUN value[a: Int] A&B
    A PRINT "value of a is: $a"
    B RETURN 0

In this example the function is used to output value of variable "a".
This function can't be called from "PRINT" or from variable because it will just show the return value of the function, but you can do it if you want to know return value of function.

To actually run the function we use "EXEF" function:

    ~ EXEF value[3]
The example above will output:
    
    value of a is: 3

### EXEF function

    ~ EXEF fun_name[value_of_parameter_1, value_of_parameter_N]
## Examples 

### Hello, World!
    
    # LBF
    ~ PRINT "Hello, World!"

Or using "EXE":
    
    # LBF
    A PRINT "Hello, World!"
    ~ EXE A

### Check if the number is even or odd

    # LBF
    ~ VAR num = 0
    ~ IN INT num
    
    A PRINT "Input is even."
    B PRINT "Input is odd."
    
    ~ IF [num%2==0] A
    ~ ELSE B

### Inputting and outputting strings

    # LBF
    ~ VAR a
    ~ IN STRING a
    ~ PRINT "a is $a."

### Comparing inputted strings

    # LBF
    ~ VAR a
    ~ VAR b
    ~ IN STRING a
    ~ IN STRING b
    A PRINT "a is same as b"
    ~ IF [a==b] A

### Selection sort in LBAF mode
    
    # LBAF
    ~ VAR a = [4, 2, 3, 1]
    ~ VAR pom = 0
    ~ FOR [i > 0..<4] A1
    A1 FOR [j > i+1..<4] B1
    B1 IF [a[i] > a[j]] C1&C2&C3
    C1 pom = a[i]
    C2 a[i] = a[j]
    C3 a[j] = pom
    
    ~ PRINT "$a"