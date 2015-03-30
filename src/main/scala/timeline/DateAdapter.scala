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
  dates: ArrayList[DateTime]
) extends ArrayAdapter[DateTime](context, 0, dates) {

  def addDates(xs: List[DateTime]) {
    xs foreach add
  }

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    val view = LayoutInflater.from(context).inflate(R.layout.timeline_item, parent, false)
    val time = view.find[TextView](R.id.timeline_time)
    val break = view.find[TextView](R.id.timeline_break)
    val date = dates.get(position)
    var next: Option[DateTime] = None

    try {
      next = Some(dates.get(position + 1))
    } catch {
      case e: Exception =>
    }

    time.setText(date.toString("H:mm"))

    next foreach { x =>
      val diff = date.getMillis - x.getMillis
      val period = new Period(diff)

      if(dates.size > 1) {
        val min = dates.toList.sliding(2).map(
          x => x(0).getMillis - x(1).getMillis).min.toDouble
        val fac = 120 / (min / 1000 / 60)
        val f = if(fac > 4) 4 else fac
        view.getLayoutParams.height = ((diff / 1000 / 60) * f).toInt
      }

      break.setText(Html.fromHtml(s"<i>Break: ${period.getHours}h ${period.getMinutes}m</i>"))
    }

    view
  }
}
