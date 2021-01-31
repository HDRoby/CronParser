import CronExpressionParser.{Parser, ParserPrinter}

object CronExpressionParserApp {

  def main(args: Array[String]): Unit = {
    val output = Parser.parse(args)
    ParserPrinter.prettyPrint(output)
  }

}