package quit.app.stats

import org.joda.time.DateTime

case class Week(
  val date: DateTime,
  val dates: List[DateTime] = Nil) {

  def number = date.weekOfWeekyear.getAsString.toInt

  def average: Double = {
    val days = dates.groupBy(_.withTimeAtStartOfDay)
    days.map { case (_, xs) =>
      xs.length
    }.sum / days.size.toDouble
  }
}
