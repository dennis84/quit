package quit.ui.history

import android.widget.ArrayAdapter
import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.TextView
import android.text.Html
import java.util.ArrayList
import org.scaloid.common._
import com.github.nscala_time.time.Imports._

class DayAdapter(
  context: Context,
  days: ArrayList[Day]
) extends ArrayAdapter[Day](context, 0, days) {

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    var view = convertView
    if(null == view) {
      view = LayoutInflater.from(context).inflate(quit.ui.R.layout.day, parent, false)
    }

    val pieces = view.find[TextView](quit.ui.R.id.history_pieces)
    val name = view.find[TextView](quit.ui.R.id.history_day)
    val day = days.get(position)

    pieces.setText(Html.fromHtml(s"<b>${day.dates.length}</b> pcs"))
    if(day.date == DateTime.now.withTimeAtStartOfDay - 1.days) {
      name.setText("Yesterday")
    } else {
      name.setText(day.date.toString("EEE, MMM d"))
    }

    view
  }
}
