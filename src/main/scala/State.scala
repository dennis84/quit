package quit.app

import org.joda.time.Days
import com.github.nscala_time.time.Imports._
import quit.app.history.Day

case class State(
  val goal: Int = 7200000,
  val limit: Int = 10,
  val notificationsEnabled: Boolean = true,
  val goalDate: Option[DateTime] = None,
  val dates: List[DateTime] = Nil,
  val days: List[Day] = Nil,
  val connected: Boolean = false) {

  def withDays = this.copy(days = (for {
    from <- dates.lastOption
    to <- dates.headOption
    xs = Days.daysBetween(from, DateTime.now + 1.days).getDays
    grouped = dates.groupBy(_.withTimeAtStartOfDay)
  } yield (0 until xs).toList map { x =>
    val y = (to - x.days).withTimeAtStartOfDay
    val ys = grouped.get(y).getOrElse(Nil)
    Day(y, ys)
  }) getOrElse Nil)
}

