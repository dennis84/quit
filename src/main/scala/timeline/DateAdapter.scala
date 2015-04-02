package quit.app.timeline

import android.widget.{ArrayAdapter, TextView, RelativeLayout}
import android.content.Context
import android.view.{LayoutInflater, View, ViewGroup}
import android.text.Html
import java.util.{ArrayList, Locale}
import scala.collection.JavaConversions._
import com.github.nscala_time.time.Imports._
import org.joda.time.Period
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
    var next: Option[TimelineItem] = None

    try {
      next = Some(items.get(position + 1))
    } catch {
      case e: Exception =>
    }

    time.setText(item.date.toString("H:mm"))

    if(item.pcs > 1) {
      pcs.setText(item.pcs.toString)
    }

    next foreach { x =>
      val diff = item.date.getMillis - x.date.getMillis
      val period = new Period(diff)
      if(items.size > 1) {
        view.getLayoutParams.height = (diff / 1000 / 60 * 2).toInt
      }

      break.setText(Html.fromHtml(s"<i>Break: ${period.getHours}h ${period.getMinutes}m</i>"))
    }

    view
  }
}

object DateAdapter {

  def apply(context: Context, dates: List[DateTime]) =
    new DateAdapter(context, new ArrayList(dates.groupWhile { (a,b) =>
      (b.getMillis - a.getMillis) > (5 * 60 * 1000)
    } map (ys => TimelineItem(ys.head, ys.length))))
}
