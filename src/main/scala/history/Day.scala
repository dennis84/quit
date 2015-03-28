package quit.app.history

import com.github.nscala_time.time.Imports._

case class Day(
  val date: DateTime,
  val dates: List[DateTime] = Nil) {

  def isToday = date == DateTime.now.withTimeAtStartOfDay
  def isYesterday = date == DateTime.now.withTimeAtStartOfDay - 1.days
}
