package quit.ui.history

import android.widget.ArrayAdapter
import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.TextView
import java.util.ArrayList
import org.scaloid.common._
import org.joda.time.DateTime

class DayAdapter(
  context: Context,
  days: ArrayList[List[DateTime]]
) extends ArrayAdapter[List[DateTime]](context, 0, days) {

  override def getView(position: Int, convertView: View, parent: ViewGroup) = {
    val view = LayoutInflater.from(context).inflate(quit.ui.R.layout.day, parent, false)
    val pieces = view.find[TextView](quit.ui.R.id.history_pieces)
    val day = view.find[TextView](quit.ui.R.id.history_day)
    val dates = days.get(position)

    pieces.setText(s"${dates.length} pieces")
    dates.headOption foreach { date =>
      day.setText(date.toString("EEE, MMM d"))
    }

    view
  }
}

object DayAdapter {
  def apply(context: Context): DayAdapter =
    new DayAdapter(context, new ArrayList[List[DateTime]])
}
