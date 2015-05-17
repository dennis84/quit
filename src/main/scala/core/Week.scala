package quit.core

import org.joda.time.DateTime

case class Week(
  val date: DateTime,
  val dates: List[DateTime] = Nil) {

  def number = date.weekOfWeekyear.getAsString.toInt

  def average: Double = dates match {
    case Nil => 0
    case xs => {
      val days = dates.groupBy(_.withTimeAtStartOfDay)
      days.map(_._2.length).sum / days.size.toDouble
    }
  }
}
