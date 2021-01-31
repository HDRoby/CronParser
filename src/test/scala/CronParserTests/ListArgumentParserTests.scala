package CronParserTests

import CronExpressionParser.ArgumentParsers.{ArgumentType, _}
import org.scalatest.FunSuite

class ListArgumentParserTests extends FunSuite {

  def verifyListIsSubsetOfList(list1:List[Int], list2:List[Int]): Boolean = list1.toSet.subsetOf(list2.toSet)

  test("CronParserTests.PatternListTests.When input is within-range, return correct range") {
    for(param <- ArgumentType.values) {
      val input = param.range.inclusive.mkString(",")
      val actual = ListArgumentParser.parse(input, param)
      assert(actual.isDefined)
      assert(actual.get === param.range.inclusive)
    }
  }

  test("CronParserTests.PatternListTests.When input is within-range but not in sequence, return correct range") {
    for(param <- ArgumentType.values) {
      val input = param.range.inclusive.reverse.mkString(",")
      val actual = ListArgumentParser.parse(input, param)
      assert(actual.isDefined)
      assert(verifyListIsSubsetOfList(actual.get, param.range.inclusive.toList))
    }
  }

  test("CronParserTests.PatternListTests.When input is out-of-range, return None") {
    for(param <- ArgumentType.values) {
      // not changing start to start-1 because many ranges are positive and a -(dash) would be considered in the regex
      val input = s"${param.range.start},${param.range.end + 1}"
      val actual = ListArgumentParser.parse(input, param)
      assert(actual.isEmpty)
    }
  }

  test("CronParserTests.PatternListTests.When input does not match pattern, return None") {
    val inputs = Seq("1,,10", "1, 10", "1 ,10", "1,", ",10", "1*10", "1 10", "", " ")
    for (input <- inputs) {
      val actual = ListArgumentParser.parse(input, ArgumentType.Hour)
      assert(actual.isEmpty)
    }
  }

  test("CronParserTests.PatternListTests.When input is Empty, return None") {
    val actual = ListArgumentParser.parse("", ArgumentType.Hour)
    assert(actual.isEmpty)
  }

  test("CronParserTests.PatternListTests.When input is null, return None") {
    val actual = ListArgumentParser.parse(null, ArgumentType.Hour)
    assert(actual.isEmpty)
  }

  test("CronParserTests.PatternListTests.When parameter is null, return None") {
    val actual = ListArgumentParser.parse("1,2,3", null)
    assert(actual.isEmpty)
  }

}
