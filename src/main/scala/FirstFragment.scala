package quit.android

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}
import android.widget.TextView
import android.support.v4.app.Fragment
import com.github.nscala_time.time.Imports._
import android.text.Html
import com.squareup.otto._
import org.scaloid.common._

class FirstFragment extends Fragment {

  var text: TextView = null

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    getActivity.asInstanceOf[MainActivity].bus.register(this)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = {
    val view = inflater.inflate(R.layout.first, container, false)
    val btn = view.findViewById(R.id.btn).asInstanceOf[TextView]
    text = view.findViewById(R.id.text).asInstanceOf[TextView]

    btn onClick {
      getActivity.asInstanceOf[MainActivity].bus.post(new Update)
    }

    view
  }

  @Subscribe
  def onUpdated(event: Updated) {
    val html = Humanize.humanize(event.dates.last) replaceAll ("""(\d+)""", "<b>$1</b>")
    text.setText(Html fromHtml html)
  }
}
