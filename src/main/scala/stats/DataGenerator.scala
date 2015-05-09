package quit.app.stats

import java.util.Locale
import lecho.lib.hellocharts.model.PointValue
import quit.app.history.Day
import quit.app._

object DataGenerator {

  def week(days: List[Day]) =
    days.reverse map { d =>
      new PointValue(d.date.dayIndex, d.dates.length)
    }
}
