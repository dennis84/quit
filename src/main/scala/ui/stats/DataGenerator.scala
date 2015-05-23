package com.github.dennis84.quit.ui.stats

import lecho.lib.hellocharts.model.PointValue
import com.github.dennis84.quit.core.Day
import com.github.dennis84.quit.tweaks.FullDsl._

object DataGenerator {

  def week(days: List[Day]) =
    days.reverse map { d =>
      new PointValue(d.date.dayIndex, d.dates.length)
    }
}
