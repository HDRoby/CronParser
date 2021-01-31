package CronParserTests

import CronExpressionParser.ArgumentParsers.{ArgumentType, _}
import org.scalatest.FunSuite

class RangeArgumentParserTests extends FunSuite {

  test("CronParserTests.PatternRangeTests.When input is within-range, return correct range") {
    for(param <- ArgumentType.values) {
      val input = s"${param.range.start}-${param.range.end}"
      val actual = RangeArgumentParser.parse(input, param)
      assert(actual.isDefined)
      assert(actual.get === param.range.inclusive)
    }
  }

  test("CronParserTests.PatternRangeTests.When input is out-of-range, return None") {
    for(param <- ArgumentType.values) {
      // not changing start to start-1 because many ranges are positive and a -(dash) would be considered in the regex
      val input = s"${param.range.start}-${param.range.end + 1}"
      val actual = RangeArgumentParser.parse(input, param)
      assert(actual.isEmpty)
    }
  }

  test("CronParserTests.PatternRangeTests.When input does not match pattern, return None") {
    val inputs = Seq("1--10", "1- 10", "1 -10", "1-", "-10", "1*10", "1 10", "", " ")
    for (input <- inputs) {
      val actual = RangeArgumentParser.parse(input, ArgumentType.Hour)
      assert(actual.isEmpty)
    }
  }

  test("CronParserTests.PatternRangeTests.When input is Empty, return None") {
    val actual = RangeArgumentParser.parse("", ArgumentType.Hour)
    assert(actual.isEmpty)
  }

  test("CronParserTests.PatternRangeTests.When input is null, return None") {
    val actual = RangeArgumentParser.parse(null, ArgumentType.Hour)
    assert(actual.isEmpty)
  }

  test("CronParserTests.PatternRangeTests.When parameter is null, return None") {
    val actual = RangeArgumentParser.parse("1-10", null)
    assert(actual.isEmpty)
  }

}
