package CronExpressionParser


import CronExpressionParser.ArgumentParsers.ArgumentType.ArgumentType
import CronExpressionParser.ArgumentParsers.{ArgumentParser, ArgumentType, ListArgumentParser, RangeArgumentParser, StepArgumentParser}

import scala.collection.mutable.ListBuffer

object Parser  {
  private val parsers = Seq[ArgumentParser](ListArgumentParser, RangeArgumentParser, StepArgumentParser)
  private val NumFieldsToParse = 6

  type OutputRow = (String,String)
  type Output = Array[OutputRow]

  private def parseArgument(input: String, argType: ArgumentType) : Option[List[Int]] = {
    for(parser <- parsers) {
      val result = parser.parse(input, argType)
      if(result.isDefined)
        return result
    }
    None
  }

  def parse(args: Array[String]) : Option[Output] = {
    if((args == null) || (args.length != NumFieldsToParse))
      return None

    var output = ListBuffer[OutputRow]()
    for(argument <- ArgumentType.values) {
      val input = args(argument.id)

      val elements = parseArgument(input, argument)
      if (elements.isEmpty)
        return None

      val outputRow = (argument.header, elements.get.mkString(" "))
      output += outputRow
    }

    val outputRow = ("command", args.last)
    output += outputRow
    Option(output.toArray)
  }

}