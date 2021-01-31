# Cron Expression Parser
The application parses a cron string and expands each field to show the times at which it will run.
It considers the standard cron format ([here](https://en.wikipedia.org/wiki/Cron)) with five time fields plus a command:
```aidl
 ┌───────────── minute (0 - 59)
 │ ┌───────────── hour (0 - 23)
 │ │ ┌───────────── day of the month (1 - 31)
 │ │ │ ┌───────────── month (1 - 12)
 │ │ │ │ ┌───────────── day of the week (0 - 6) (Sunday to Saturday;
 │ │ │ │ │
 │ │ │ │ │
 * * * * * <command>
```

The output should be formatted as a table with the field name taking the first 14 columns, and the times as a space-separated list following it.

The application **does not** handle the special time strings such as ```@yearly``` nor handles the string format for week days and months: ```SUN-SAT``` and ```JAN-DEC```.

The Cron parameters are passed to the application on the command line as a single argument on a single line.

## Usage

```aidl
~$ CronExpressionParserApp <cron parameters> <command>
```
for example:
```aidl
~$ CronExpressionParserApp */15 0 1,15 * 1-5 /usr/bin/find
```

will produce:

```aidl
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

## Project Structure
This project has been developed in Scala with the use of IntelliJ and SBT as a building tool.

Use ```sbt clean```, ```sbt compile```, ```sbt test``` and ```sbt package``` to generate jar file (dependencies not included).
```
CronParser
    build.sbt
    README.md    
    /src
        /main
            /scala
                /CronExpressionParser
                    CronExpressionParserApp.scala
                    /ArgumentParsers
                    Parser.scala
                    ParserPrinter.scala
                    
        /test
            /scala
                /CronParserTests
```


## Code walk-through
The entry point is in ```CronExpressionParser``` where the parser itself is executed, and a pretty print utility produces the required output.
The parser object run through the arguments and for each, loops through all the patterns' parsers. The first non-empty result is used and appended to the output array, then the next argument is processed.
Each argument parser is an object that extend the ```ArgumentParser``` trait. The only public method is ```parse``` which does input validation and output range checking and calls ```innerParse``` which is the only abstract method that needs to be implemented for argument-specific parsers.
Each argument is positionally identified and associated with an enum that holds its description (to be used in the output) and its range (used for output validation and generation). 

**Note**: ```ArgumentType``` and ```ArgumentParse``` are decoupled. The former identifies the accepted ranges, the latter specifies the expected pattern and how to interpret it.  
