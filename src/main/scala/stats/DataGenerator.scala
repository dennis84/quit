package quit.app.stats

import java.util.Locale
import lecho.lib.hellocharts.model.PointValue
import quit.app.history.Day

object DataGenerator {

  def week(days: List[Day]) =
    days.reverse map { d =>
      val index = d.date.getMillis / 1000 / 60 / 60 / 24
      new PointValue(index, d.dates.length)
    }
}
