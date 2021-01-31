package CronExpressionParser.ArgumentParsers

import ArgumentType.ArgumentType

object RangeArgumentParser extends ArgumentParser {

  stepRegExPattern("""^\d+-\d+$""")

  override def innerParse(input: String, argType: ArgumentType): Option[List[Int]] = {
    val rangeValues = input.split("-")
    Option(Range(rangeValues(0).toInt, rangeValues(1).toInt).inclusive.toList)
  }

}

