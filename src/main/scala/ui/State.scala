package quit.ui

import org.joda.time.DateTime

case class State(
  val goal: Int = 7200000,
  val limit: Int = 10,
  val notificationsEnabled: Boolean = true,
  val goalDate: Option[DateTime] = None,
  val dates: List[DateTime] = Nil,
  val connected: Boolean = false)
