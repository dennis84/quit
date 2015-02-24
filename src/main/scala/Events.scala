package quit.android

import org.joda.time.DateTime

case class Updated(val dates: List[DateTime])

case class Update()

case class Env(val client: Client)
