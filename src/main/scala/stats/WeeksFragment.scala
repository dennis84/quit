package quit.app.stats

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{ListView, TextView}
import android.support.v4.view.ViewPager
import com.squareup.otto._
import java.util.ArrayList
import org.joda.time.Weeks
import quit.app._
import quit.app.timeline._

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
