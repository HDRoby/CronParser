package CronExpressionParser.ArgumentParsers

import ArgumentType.ArgumentType

object ListArgumentParser extends ArgumentParser {

  stepRegExPattern("""(\d+)(,\d+)*""")

  override def innerParse(input: String, argType: ArgumentType): Option[List[Int]] =
    Option(input.split(",").map(e => e.toInt).toList)
}
