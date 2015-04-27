package quit.app.stats

import java.util.Locale
import com.github.mikephil.charting.data.Entry
import quit.app.history.Day

case class Data(xs: List[String], ys: List[Entry])

object DataGenerator {

  def weekly(days: List[Day]) = {
    val ds = days.take(7).reverse
    val xs = ds.map(_.date.toString("EEE", Locale.US))
    Data(xs, ds.zipWithIndex map { case (d, i) =>
      new Entry(d.dates.length, i)
    })
  }
}
