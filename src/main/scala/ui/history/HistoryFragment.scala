package quit.ui.history

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{LinearLayout, Button, ProgressBar, RadioGroup}
import android.support.v4.view.ViewPager
import org.scaloid.common._
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
    adapter = new DayAdapter(activity, new ArrayList[List[DateTime]])
    setListAdapter(adapter)
  }

  override def onResume {
    super.onResume
    runOnUiThread(bus.post(state.copy(
      connected = true,
      dates = env.repo.list
    )))
  }

  @Subscribe
  def update(newState: State) {
    if(!viewCreated) return
    val from = newState.dates.headOption.getOrElse(DateTime.now)
    val days = Days.daysBetween(from, DateTime.now + 1.days).getDays
    for(i <- 1 until days) {
      adapter.add(newState.dates.filter {
        _.withTimeAtStartOfDay == (DateTime.now - i.days).withTimeAtStartOfDay
      })
    }
  }
}
