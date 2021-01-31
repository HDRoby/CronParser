package CronExpressionParser.ArgumentParsers

object ArgumentType extends Enumeration {
  type ArgumentType = Value

  val Minute: RangeVal = RangeVal(0, Range(0, 59), "minute")
  val Hour: RangeVal = RangeVal(1, Range(0, 23), "hour")
  val DayOfMonth: RangeVal = RangeVal(2, Range(1, 31), "day of month")
  val Month: RangeVal = RangeVal(3, Range(1, 12), "month")
  val DayOfWeek: RangeVal = RangeVal(4, Range(0, 6), "day of week")

  //TODO: not required but easy to add.
  //val Year = RangeVal(5,Range(1970,2099), "year")

  protected case class RangeVal(num: Int, range: Range, header: String) extends Val

  implicit def convert(value: Value): RangeVal = value.asInstanceOf[RangeVal]
}
