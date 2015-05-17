package quit.core

import quit.tweaks.FullDsl._

case class Day(
  val date: DateTime,
  val dates: List[DateTime] = Nil,
  var selected: Boolean = false) {

  def isToday = date == DateTime.now.withTimeAtStartOfDay
  def isYesterday = date == DateTime.now.withTimeAtStartOfDay - 1.days
}
