package quit.android

import org.joda.time.DateTime

case class Updated(val dates: List[DateTime])

class Update

case class Env(
  val id: String,
  val client: Client)
