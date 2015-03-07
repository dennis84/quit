package quit.ui.history

import android.widget.ArrayAdapter
import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.TextView
import android.text.Html
import java.util.ArrayList
import org.scaloid.common._
import com.github.nscala_time.time.Imports._
import quit.ui._
import quit.ui.progress._
import quit.db._

class DayAdapter(
  context: Context,
  days: ArrayList[List[DateTime]]
) extends ArrayAdapter[List[DateTime]](context, 0, days) {

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    var view = convertView
    if(null == view) {
      view = LayoutInflater.from(context).inflate(quit.ui.R.layout.day, parent, false)
    }

    val pieces = view.find[TextView](quit.ui.R.id.history_pieces)
    val day = view.find[TextView](quit.ui.R.id.history_day)
    val dates = days.get(position)

    pieces.setText(Html.fromHtml(s"<b>${dates.length}</b> pcs"))
    dates.headOption foreach { date =>
      if(date.withTimeAtStartOfDay == DateTime.now.withTimeAtStartOfDay - 1.days) {
        day.setText("Yesterday")
      } else {
        day.setText(date.toString("EEE, MMM d"))
      }
    }

    view
  }
}
