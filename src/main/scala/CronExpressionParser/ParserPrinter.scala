package CronExpressionParser

import CronExpressionParser.Parser.Output

object ParserPrinter {
  val HeaderLen = 14

  private def usagePrint(): Unit = {
    println("""
              |                      ┌───────── minute           (0 - 59)
              |                      │ ┌─────── hour             (0 - 23)
              |                      │ │ ┌───── day of the month (1 - 31)
              |                      │ │ │ ┌─── month            (1 - 12)
              |                      │ │ │ │ ┌─ day of the week  (0 -  6)
              |                      │ │ │ │ │
              |CronExpressionParser  * * * * * <command>
              |""".stripMargin)
  }

  def prettyPrint(output:Option[Output]): Unit = {
    if(output.isEmpty) {
      usagePrint()
      return
    }

    for (value <- output.get)
      println(String.format("%1$-" + HeaderLen + "s", value._1) + value._2)
  }
}
