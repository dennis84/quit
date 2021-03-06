package com.github.dennis84.quit.core

import org.joda.time.Days
import com.github.nscala_time.time.Imports._

case class State(
  val goal: Int = 7200000,
  val limit: Int = 10,
  val notificationsEnabled: Boolean = true,
  val goalDate: Option[DateTime] = None,
  val dates: List[DateTime] = Nil,
  val days: List[Day] = Nil,
  val configs: List[Config] = Nil) {

  def withDays = {
    val now = DateTime.now
    val from = dates.lastOption getOrElse now
    val nbDays = Days.daysBetween(from.withTimeAtStartOfDay, now.withTimeAtStartOfDay).getDays
    val grouped = dates.groupBy(_.withTimeAtStartOfDay)
    this.copy(days = (0 to nbDays).toList map { n =>
      val y = (now - n.days).withTimeAtStartOfDay
      val ys = grouped.get(y).getOrElse(Nil)
      Day(y, ys)
    })
  }
}
