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
    var view = convertView
    val holder = if(null == view) {
      view = LayoutInflater.from(context).inflate(R.layout.day, parent, false)
      val h = ViewHolder(
        view.find[TextView](R.id.history_pieces),
        view.find[TextView](R.id.history_day),
        view.find[TextView](R.id.history_break),
        view.find[TimelineView](R.id.timeline))
      view.setTag(h)
      h
    } else convertView.getTag.asInstanceOf[ViewHolder]

    val day = days.get(position)

    if(day.selected) {
      if(null == holder.timeline.getAdapter) {
        holder.timeline.setAdapter(DateAdapter(context, day.dates))
        holder.timeline.getAdapter.notifyDataSetChanged
      }

      holder.timeline.setVisibility(View.VISIBLE)
    } else {
      holder.timeline.setVisibility(View.GONE)
    }

    holder.pieces.setText(Html.fromHtml(s"<b>${day.dates.length}</b> pcs"))
    if(day.isYesterday) {
      holder.name.setText("Yesterday")
    } else {
      holder.name.setText(day.date.toString("EEE, MMM d", Locale.US))
    }

    if(day.dates.length > 1) {
      day.dates.reverse.sliding(2).map {
        x => x(1).getMillis - x(0).getMillis
      } reduceOption (_ max _) foreach { x =>
        val period = new Period(x)
        holder.break.setText(Html.fromHtml(s"<i>Longest break: ${period.getHours}h ${period.getMinutes}m</i>"))
      }
    }

    view
  }
}
