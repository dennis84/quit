package quit.app.history

import org.joda.time.DateTime

case class Day(
  val date: DateTime,
  val dates: List[DateTime] = Nil)
