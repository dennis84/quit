package quit.ui.progress

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}
import android.widget.TextView
import com.github.nscala_time.time.Imports._
import android.text.Html
import com.squareup.otto._
import org.scaloid.common._
import quit.util.Humanize._
import quit.ui._

class FirstFragment extends QFragment {

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
    val view = inflater.inflate(R.layout.first, container, false)
    text = view.findViewById(R.id.text).asInstanceOf[TextView]
    view
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val html = humanize(event.state.dates.last) replaceAll ("""(\d+)""", "<b>$1</b>")
    text.setText(Html fromHtml html)
  }
}
