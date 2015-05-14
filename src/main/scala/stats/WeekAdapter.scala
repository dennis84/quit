package quit.app.stats

import android.widget.{ArrayAdapter, TextView}
import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.text.Html
import java.util.{ArrayList, Locale}
import scala.math.BigDecimal
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
    val dateText = s"Week ${week.number}, ${week.date.toString("MMM Y", Locale.US)}"
    val avg = BigDecimal(week.average).setScale(1, BigDecimal.RoundingMode.HALF_UP).toDouble
    val avgText = s"Average per day: <b>${avg}</b>"
    val pcsText = s"Total of: <b>${week.dates.length}</b>"
    holder.number.setText(dateText)
    holder.average.setText(Html.fromHtml(avgText))
    holder.pieces.setText(Html.fromHtml(pcsText))

    view
  }
}
