package quit.app.history

import android.widget.{ArrayAdapter, TextView}
import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.text.Html
import java.util.{ArrayList, Locale}
import com.github.nscala_time.time.Imports._
import org.joda.time.Period
import quit.app._
import quit.app.timeline._

class DayAdapter(
  context: Context,
  days: ArrayList[Day]
) extends ArrayAdapter[Day](context, 0, days) {

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    val view = LayoutInflater.from(context).inflate(R.layout.day, parent, false)
    val pieces = view.find[TextView](R.id.history_pieces)
    val name = view.find[TextView](R.id.history_day)
    val break = view.find[TextView](R.id.history_break)
    val day = days.get(position)

    pieces.setText(Html.fromHtml(s"<b>${day.dates.length}</b> pcs"))
    if(day.isYesterday) {
      name.setText("Yesterday")
    } else {
      name.setText(day.date.toString("EEE, MMM d", Locale.US))
    }

    if(day.dates.length > 1) {
      day.dates.reverse.sliding(2).map {
        x => x(1).getMillis - x(0).getMillis
      } reduceOption (_ max _) foreach { x =>
        val period = new Period(x)
        break.setText(Html.fromHtml(s"<i>Longest break: ${period.getHours}h ${period.getMinutes}m</i>"))
      }
    }

    view
  }
}
