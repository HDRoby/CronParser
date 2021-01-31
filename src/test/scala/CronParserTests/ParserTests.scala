package CronParserTests

import CronExpressionParser.Parser.OutputRow
import CronExpressionParser.ParserPrinter
import org.scalatest.{BeforeAndAfter, FunSuite}

class ParserTests extends FunSuite with BeforeAndAfter {
  /*
        CRON parameters positions

        ┌───────────── minute (0 - 59)
        │ ┌─────────── hour (0 - 23)
        │ │ ┌───────── day of the month (1 - 31)
        │ │ │ ┌─────── month (1 - 12)
        │ │ │ │ ┌───── day of the week (0 - 6)
        │ │ │ │ │
        * * * * * <command>

        Output required
        ---------------
        The output should be formatted as a table with the field name taking the first 14 columns and
        the times as a space-separated list following it.

        Example
        -------
*/

  //      Input: */15 0 1,15 * 1-5 /usr/bin/find

/*
          Output:
          ┌─────────────────── col 1
          |            ┌────── col 14
          |            |
          minute        0 15 30 45
          hour          0
          day of month  1 15
          month         1 2 3 4 5 6 7 8 9 10 11 12
          day of week   1 2 3 4 5
          command       /usr/bin/find
 */

  test("CronParserTests.ParserTest.When input the required args, return correct output") {
    val args = Seq("*/15", "0", "1,15", "*", "1-5", "/usr/bin/find").toArray
    val actual = CronExpressionParser.Parser.parse(args)
    val expected = Seq[OutputRow](
      ("minute", "0 15 30 45"),
      ("hour", "0"),
      ("day of month", "1 15"),
      ("month", "1 2 3 4 5 6 7 8 9 10 11 12"),
      ("day of week", "1 2 3 4 5"),
      ("command", "/usr/bin/find")
    )
    assert(actual.get === expected)

    ParserPrinter.prettyPrint(actual)
  }

  test("CronParserTests.ParserTest.Must run on Saturday @23.45.[eye-ball]") {
    val args = Seq("45", "23", "*", "*", "6", "/usr/bin/find").toArray
    val actual = CronExpressionParser.Parser.parse(args)

    assert(actual.isDefined)
    ParserPrinter.prettyPrint(actual)
  }

  test("CronParserTests.ParserTest.Must run every 5th minute @1am,2am,3am.[eye-ball]") {
    val args = Seq("*/5", "1,2,3", "*", "*", "*", "/usr/bin/find").toArray
    val actual = CronExpressionParser.Parser.parse(args)

    assert(actual.isDefined)
    ParserPrinter.prettyPrint(actual)
  }

  test("CronParserTests.ParserTest.[eye-ball]") {
    val args = Seq("*", "*", "*", "*", "*", "echo").toArray
    val actual = CronExpressionParser.Parser.parse(args)

    assert(actual.isDefined)
    ParserPrinter.prettyPrint(actual)
  }

  test("CronParserTests.ParserTest.When input wrong number of args, return None") {
    val args = Seq("*/15", "0", "1,15", "*", "1-5").toArray
    val actual = CronExpressionParser.Parser.parse(args)

    assert(actual.isEmpty)
    ParserPrinter.prettyPrint(actual)
  }

  test("CronParserTests.ParserTest.When input correct number of args but non correct, return None") {
    val args = Seq("-", "-", "1,15", "*", "1-5").toArray
    val actual = CronExpressionParser.Parser.parse(args)

    assert(actual.isEmpty)
  }

  test("CronParserTests.ParserTest.When input correct number of args but all non correct, return None") {
    val args = Seq("-", "-", "-", "-", "-", "/usr/bin/find").toArray
    val actual = CronExpressionParser.Parser.parse(args)

    assert(actual.isEmpty)
  }

  test("CronParserTests.ParserTest.When input correct number of args but out-of-range, return None") {
    val args = Seq("*", "30", "1,15", "*", "1-5").toArray
    val actual = CronExpressionParser.Parser.parse(args)

    assert(actual.isEmpty)
  }

}
