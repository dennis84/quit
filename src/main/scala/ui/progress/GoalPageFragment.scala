package quit.ui.progress

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import android.text.Html
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import quit.util.Humanize._
import quit.ui._

class GoalPageFragment extends QFragment {

  var text: TextView = _
  var goal: TextView = _

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.first, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    text = view.find[TextView](R.id.text)
    goal = view.find[TextView](R.id.progress_goal)
  }

  override def onResume {
    super.onResume
    update(state)
  }

  @Subscribe
  def update(newState: State) {
    if(!viewCreated) return
    newState.dates.lastOption foreach { date =>
      val html = humanize(date) replaceAll ("""(\d+)""", "<b>$1</b>")
      text.setText(Html fromHtml html)

      val goalDate = date + newState.currentGoal.getOrElse(newState.goal).millis
      if(DateTime.now > goalDate) {
        goal.setText("Congrats, you've reached your goal")
      } else {
        goal.setText(humanize(DateTime.now to goalDate))
      }
    }
  }
}
