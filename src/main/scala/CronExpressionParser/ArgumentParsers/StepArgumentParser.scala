package CronExpressionParser.ArgumentParsers

import ArgumentType.ArgumentType

object StepArgumentParser extends ArgumentParser {

  stepRegExPattern("""^\*(\/\d+)?$""")

  override def innerParse(input: String, argType: ArgumentType): Option[List[Int]] = {
    val step = getStepFromInput(input, argType)

    if (step.isEmpty)
      return None

    Option(Range(argType.range.start, argType.range.end, step.get).inclusive.toList)
  }

  private def getStepFromInput(input: String, argType: ArgumentType): Option[Int] = {
    if (input == "*")
      return Some(1)

    val step = input.replace("*/", "").trim.toInt
    if ((step > 0) && (step <= argType.range.end))
      return Some(step)

    None
  }

}
