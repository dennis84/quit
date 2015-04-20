package quit.app.progress

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import android.text.Html
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import quit.app.util.Humanize._
import quit.app._

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
    update(new UpdateUI)
  }

  @Subscribe
  def update(event: UpdateUI) =
    (viewCreated, state.dates.headOption, state.goalDate) match {
      case (false, _, _) =>
      case (true, Some(date), Some(goalDate)) => {
        val html = (humanize(date).capitalize + " ago") replaceAll ("""(\d+)""", "<b>$1</b>")
        text.setText(Html fromHtml html)
        if(DateTime.now < goalDate) {
          goal.setText(humanize(DateTime.now to goalDate).capitalize + " to your goal")
        } else if(DateTime.now > goalDate + 5.minutes) {
          goal.setText("You've exceeded your goal by " + humanize(goalDate to DateTime.now))
        } else {
          goal.setText("Congrats, you've reached your goal!")
        }
      }
      case (true, _, _) => {
        text.setText("Welcome to Quit")
        goal.setText("Press the button to get started.")
      }
    }
}
