package quit.app.stats

import lecho.lib.hellocharts.model.PointValue
import quit.core.Day
import quit.tweaks.FullDsl._

object DataGenerator {

  def week(days: List[Day]) =
    days.reverse map { d =>
      new PointValue(d.date.dayIndex, d.dates.length)
    }
}
