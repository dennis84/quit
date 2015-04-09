package quit.app.timeline

import android.widget.{ArrayAdapter, TextView, RelativeLayout}
import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.text.Html
import java.util.{ArrayList, Locale}
import scala.collection.JavaConversions._
import scala.util.Try
import org.joda.time.{DateTime, Period}
import quit.app._

class DateAdapter(
  context: Context,
  items: ArrayList[TimelineItem]
) extends ArrayAdapter[TimelineItem](context, 0, items) {

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    val view = LayoutInflater.from(context).inflate(R.layout.timeline_item, parent, false)
    val time = view.find[TextView](R.id.timeline_time)
    val break = view.find[TextView](R.id.timeline_break)
    val pcs = view.find[TextView](R.id.timeline_pcs)
    val item = items.get(position)

    time.setText(item.date.toString("H:mm"))

    if(item.pcs > 1) {
      pcs.setText(item.pcs.toString)
    }

    Try(items.get(position + 1)) foreach { next =>
      val diff = item.date.getMillis - next.date.getMillis
      val period = new Period(diff)
      view.getLayoutParams.height = (diff / 1000 / 60 * 2).toInt
      break.setText(Html.fromHtml(s"<i>Break: ${period.getHours}h ${period.getMinutes}m</i>"))
    }

    view
  }
}

object DateAdapter {
  def apply(context: Context, dates: List[DateTime]) =
    new DateAdapter(context, new ArrayList(dates.groupWhile { (l,r) =>
      (l.getMillis - r.getMillis) <= (5 * 60 * 1000)
    } map (ys => TimelineItem(ys.head, ys.length))))
}
