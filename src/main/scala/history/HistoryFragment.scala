package quit.app.history

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{ListView, TextView}
import android.support.v4.view.ViewPager
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import org.joda.time.Days
import java.util.ArrayList
import quit.app._
import quit.app.timeline._

class HistoryFragment extends QListFragment {

  var adapter: DayAdapter = _
  var header: View  = _
  var timeline: TimelineView = _
  var toggleTimeline: TextView = _

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = {
    header = inflater.inflate(R.layout.today, container, false)
    timeline = header.find[TimelineView](R.id.timeline)
    toggleTimeline = header.find[TextView](R.id.toggle_timeline)
    var datesAdapter = new DateAdapter(activity, new ArrayList[DateTime])
    timeline.setAdapter(datesAdapter)
    super.onCreateView(inflater, container, savedInstanceState)
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    getListView addHeaderView header
    adapter = new DayAdapter(activity, new ArrayList[Day])
    setListAdapter(adapter)

    toggleTimeline onClick {
      if(View.VISIBLE == timeline.getVisibility) {
        timeline.setVisibility(View.GONE)
        toggleTimeline.setText("Show timeline")
      } else {
        timeline.setVisibility(View.VISIBLE)
        toggleTimeline.setText("Hide timeline")
      }
    }
  }

  override def onListItemClick(l: ListView, v: View, position: Int, id: Long) {
    super.onListItemClick(l, v, position, id)
    val day = getListAdapter.getItem(position - 1).asInstanceOf[Day]
    val timeline = v.find[TimelineView](R.id.timeline)
    if(View.VISIBLE == timeline.getVisibility) {
      day.selected = false
    } else {
      day.selected = true
    }

    adapter.notifyDataSetChanged
  }

  override def onResume {
    super.onResume
    env.ctrl.list(state)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    if(!viewCreated) return
    val from = event.state.dates.lastOption.getOrElse(DateTime.now)
    val days = Days.daysBetween(from, DateTime.now + 1.days).getDays
    val grouped = event.state.dates.groupBy(_.withTimeAtStartOfDay)
    adapter.clear
    for(i <- 0 until days) {
      val date = (DateTime.now - i.days).withTimeAtStartOfDay
      val dates = grouped.get(date).getOrElse(Nil)
      val day = Day(date, dates)
      if(day.isToday) {
        timeline.getAdapter.clear
        timeline.getAdapter.addDates(day.dates)
      } else {
        adapter.add(Day(date, dates))
      }
    }
  }
}
