package quit.ui

import org.joda.time.DateTime

case class State(
  val goal: Int = 7200000,
  val limit: Int = 10,
  val dates: List[DateTime] = Nil,
  val connected: Boolean = false)
