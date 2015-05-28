package com.github.dennis84.quit.ui.stats

import android.os.Bundle
import android.view.View
import java.util.ArrayList
import com.squareup.otto._
import org.joda.time.Weeks
import com.github.dennis84.quit.ui.QListFragment
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.core._

class WeeksFragment extends QListFragment[WeekAdapter] {

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    setListAdapter(new WeekAdapter(activity, new ArrayList[Week]))
    env.ctrl.stats(state)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val now = DateTime.now
    val from = event.state.dates.lastOption getOrElse now
    val nbWeeks = Weeks.weeksBetween(from.withTimeAtStartOfDay, now.withTimeAtStartOfDay).getWeeks
    val grouped = event.state.dates.groupBy(_.weekOfWeekyear)

    (0 to nbWeeks) foreach { n =>
      val x = (now - n.weeks).withTimeAtStartOfDay
      val w = Week(x, grouped.get(x.weekOfWeekyear).getOrElse(Nil))
      if(adapter.getPosition(w) < 0) adapter.add(w)
    }
  }
}
