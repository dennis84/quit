package quit.ui.history

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{LinearLayout, Button, ProgressBar, RadioGroup}
import android.support.v4.view.ViewPager
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import org.joda.time.Days
import java.util.ArrayList
import quit.ui._

class HistoryFragment extends QListFragment {

  var adapter: DayAdapter = _
  var header: View  = _

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = {
    header = inflater.inflate(R.layout.today, container, false)
    super.onCreateView(inflater, container, savedInstanceState)
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    getListView addHeaderView header
    adapter = new DayAdapter(activity, new ArrayList[Day])
    setListAdapter(adapter)
  }

  override def onResume {
    super.onResume
    env.ctrl.list(state)
  }

  @Subscribe
  def update(event: UpdateUI) {
    if(!viewCreated) return
    val from = state.dates.lastOption.getOrElse(DateTime.now)
    val days = Days.daysBetween(from, DateTime.now + 1.days).getDays
    val grouped = state.dates.groupBy(_.withTimeAtStartOfDay)
    adapter.clear
    for(i <- 1 until days) {
      val date = (DateTime.now - i.days).withTimeAtStartOfDay
      val dates = grouped.get(date).getOrElse(Nil)
      adapter.add(Day(date, dates))
    }
  }
}
