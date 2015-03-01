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
  var goal: TextView = null

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
    goal = view.findViewById(R.id.progress_goal).asInstanceOf[TextView]
    view
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val date = event.state.dates.last
    val html = humanize(date) replaceAll ("""(\d+)""", "<b>$1</b>")
    text.setText(Html fromHtml html)

    val goalDate = date + event.state.goal.millis
    if(DateTime.now > goalDate) {
      goal.setText("Congrats, you reached your goal")
    } else {
      goal.setText(humanize(DateTime.now to goalDate))
    }
  }
}
