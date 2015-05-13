package quit.app.stats

import android.widget.{ArrayAdapter, TextView}
import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.text.Html
import java.util.{ArrayList, Locale}
import org.joda.time.Period
import quit.app._

class WeekAdapter(
  context: Context,
  weeks: ArrayList[Week]
) extends ArrayAdapter[Week](context, 0, weeks) {

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    var view = convertView
    val holder = if(null == view) {
      view = LayoutInflater.from(context).inflate(R.layout.week, parent, false)
      val h = ViewHolder(
        view.find[TextView](R.id.week_number),
        view.find[TextView](R.id.week_pieces),
        view.find[TextView](R.id.week_average))
      view.setTag(h)
      h
    } else convertView.getTag.asInstanceOf[ViewHolder]

    val week = weeks.get(position)
    holder.number.setText(week.number.toString)
    holder.pieces.setText(week.dates.length.toString)
    holder.average.setText(week.average.toString)

    view
  }
}
