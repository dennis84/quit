package com.github.dennis84.quit.tweaks

import com.github.nscala_time.time.Imports

trait TimeTweaks extends Imports {
  implicit class DayIndex(date: DateTime) {
    def dayIndex = date.withTimeAtStartOfDay.getMillis / 1000 / 60 / 60 / 24
  }
}
