package quit.ui.history

import android.widget.{ArrayAdapter, TextView}
import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.text.Html
import java.util.ArrayList
import com.github.nscala_time.time.Imports._
import org.joda.time.Period
import quit.ui._

class DayAdapter(
  context: Context,
  days: ArrayList[Day]
) extends ArrayAdapter[Day](context, 0, days) {

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    var view = convertView
    if(null == view) {
      view = LayoutInflater.from(context).inflate(R.layout.day, parent, false)
    }

    val pieces = view.find[TextView](R.id.history_pieces)
    val name = view.find[TextView](R.id.history_day)
    val break = view.find[TextView](R.id.history_break)
    val day = days.get(position)

    pieces.setText(Html.fromHtml(s"<b>${day.dates.length}</b> pcs"))
    if(day.date == DateTime.now.withTimeAtStartOfDay - 1.days) {
      name.setText("Yesterday")
    } else {
      name.setText(day.date.toString("EEE, MMM d"))
    }

    day.dates.sliding(2).map {
      x => x(1).getMillis - x(0).getMillis
    } reduceOption (_ max _) foreach { x =>
      val period = new Period(x)
      break.setText(Html.fromHtml(s"<i>Longest break: ${period.getHours}h ${period.getMinutes}m</i>"))
    }

    view
  }
}
