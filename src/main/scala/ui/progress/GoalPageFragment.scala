package com.github.dennis84.quit.ui.progress

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import android.text.Html
import com.squareup.otto._
import com.github.dennis84.quit.ui.QFragment
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.core._
import com.github.dennis84.quit.R
import Humanize._

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
  def update(event: UpdateUI) {
    if(!viewCreated) return
    (for {
      date <- state.dates.headOption
      goalDate <- state.goalDate
    } yield {
      val html = (humanize(date).capitalize + " ago") replaceAll ("""(\d+)""", "<b>$1</b>")
      text.setText(Html fromHtml html)
      if(DateTime.now < goalDate) {
        goal.setText(humanize(DateTime.now to goalDate).capitalize + " to your goal")
      } else if(DateTime.now > goalDate + 5.minutes) {
        goal.setText("You've exceeded your goal by " + humanize(goalDate to DateTime.now))
      } else {
        goal.setText("Congrats, you've reached your goal!")
      }
    }) getOrElse {
      text.setText("Welcome to Quit")
      goal.setText("Press the button to get started.")
    }
  }
}
