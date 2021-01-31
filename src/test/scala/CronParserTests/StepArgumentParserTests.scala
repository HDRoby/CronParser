package CronParserTests

import CronExpressionParser.ArgumentParsers.{ArgumentType, _}
import org.scalatest.FunSuite

class StepArgumentParserTests extends FunSuite {

  test("CronParserTests.PatternStepTests.When input is within-range, return correct range") {
    for(param <- ArgumentType.values) {
      for(i <- param.range){
        if (i > 0) {
          val expected = Range(param.range.start, param.range.end, i).inclusive
          val input = s"*/$i"
          val actual = StepArgumentParser.parse(input, param)
          assert(actual.isDefined)
          assert(actual.get === expected)
        }
      }
    }
  }

  test("CronParserTests.PatternStepTests.When input is *, return full range") {
    for(param <- ArgumentType.values) {
      val actual = StepArgumentParser.parse("*", param)
      assert(actual.isDefined)
      assert(actual.get === param.range.inclusive)
    }
  }

  test("CronParserTests.PatternStepTests.When input is out-of-range, return None") {
    for(param <- ArgumentType.values) {
      val input = s"*/${param.range.end + 1}"
      val actual = StepArgumentParser.parse(input, param)
      assert(actual.isEmpty)
    }
  }

  test("CronParserTests.PatternStepTests.When input is 0, return None") {
    val actual = StepArgumentParser.parse("*/0", ArgumentType.Hour)
    assert(actual.isEmpty)
  }

  test("CronParserTests.PatternStepTests.When input does not match pattern, return None") {
    val inputs = Seq("*//1", "*/a", "/*", "*/ 1", "", " ")
    for (input <- inputs) {
      val actual = StepArgumentParser.parse(input, ArgumentType.Hour)
      assert(actual.isEmpty)
    }
  }

  test("CronParserTests.PatternStepTests.When input is Empty, return None") {
    val actual = StepArgumentParser.parse("", ArgumentType.Hour)
    assert(actual.isEmpty)
  }

  test("CronParserTests.PatternStepTests.When input is null, return None") {
    val actual = StepArgumentParser.parse(null, ArgumentType.Hour)
    assert(actual.isEmpty)
  }

  test("CronParserTests.PatternStepTests.When parameter is null, return None") {
    val actual = StepArgumentParser.parse("*/2", null)
    assert(actual.isEmpty)
  }

}
