package quit.ui

import quit.api._

class Env(id: String, url: String) {

  lazy val client = new Client(id, url)
}
