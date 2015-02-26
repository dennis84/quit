package quit.ui

import org.joda.time.DateTime

case class State(
  val goal: Int = 7200000,
  val dates: List[DateTime] = Nil)

class ChangeState(val state: State)
