package quit.android

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}
import android.widget.TextView
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import android.text.Html

class SecondFragment extends QFragment {

  var text: TextView = null

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = {
    val view = inflater.inflate(R.layout.second, container, false)
    text = view.findViewById(R.id.foo).asInstanceOf[TextView]
    view
  }

  @Subscribe
  def onUpdated(event: Updated) {
    val dates = event.dates.filter(_ < DateTime.now.withTimeAtStartOfDay)
    val html = s"${dates.length} pieces today" replaceAll ("""(\d+)""", "<b>$1</b>")
    text.setText(Html.fromHtml(html))
  }
}
