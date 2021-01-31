package CronExpressionParser.ArgumentParsers

import ArgumentType.ArgumentType

trait ArgumentParser {
  private var pattern: String = ""

  protected def innerParse(input: String, argType: ArgumentType): Option[List[Int]]

  def parse(input: String, argType: ArgumentType): Option[List[Int]] = {
    if (!validateInputPattern(input, argType, pattern))
      return None

    val outputList = innerParse(input, argType)
    if(outputList.isEmpty)
      return None

    if (!validateInputLimits(outputList.get, argType))
      return None

    Option(outputList.get)
  }

  protected def validateInputLimits(inputList: List[Int], parameterType: ArgumentType): Boolean =
    inputList.toSet.subsetOf(parameterType.range.inclusive.toSet)

  protected def validateInputPattern(input: String, argumentType: ArgumentType, regExPattern: String): Boolean =
    !regExPattern.isEmpty && argumentType != null && input != null && regExPattern.r.matches(input)

  protected def stepRegExPattern(regEx: String): Unit = pattern = regEx
}