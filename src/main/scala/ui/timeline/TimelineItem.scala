package com.github.dennis84.quit.ui.timeline

import org.joda.time.DateTime

case class TimelineItem(
  val date: DateTime,
  val pcs: Int = 1)
