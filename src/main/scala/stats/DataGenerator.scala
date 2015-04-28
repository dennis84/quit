package quit.app.stats

import java.util.Locale
import com.github.mikephil.charting.data.Entry
import quit.app.history.Day

case class Data(xs: List[String], ys: List[Entry])

object DataGenerator {

  def week(days: List[Day]) = {
    val ds = days.take(7).reverse
    val xs = ds.map(_.date.toString("EEE", Locale.US).toUpperCase)
    Data(xs, ds.zipWithIndex map { case (d, i) =>
      new Entry(d.dates.length, i)
    })
  }

  def month(days: List[Day]) = {
    Data(List("JAN", "FEB", "MAR", "APR"), List(
      new Entry(32, 0), new Entry(23, 1), new Entry(43, 2), new Entry(12, 3)))
  }
}
