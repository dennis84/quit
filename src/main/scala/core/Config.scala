package quit.core

import org.joda.time.DateTime

case class Config(
  val limit: Int,
  val createdAt: DateTime)
