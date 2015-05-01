package quit.app.stats

import java.util.Locale
import lecho.lib.hellocharts.model.PointValue
import quit.app.history.Day

object DataGenerator {

  def week(days: List[Day]) =
    days.reverse.zipWithIndex map { case (d, i) =>
      new PointValue(i, d.dates.length)
    }
}
