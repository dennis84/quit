package quit.ui.history

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import org.scaloid.common._
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import quit.ui._

class HistoryFragment extends QListFragment {

  var adapter: DayAdapter = _

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    implicit val ctx = getActivity

    adapter = DayAdapter(activity)
    setListAdapter(adapter)
    update(state)
  }

  @Subscribe
  def update(newState: State) {
    if(!viewCreated) return
    val dates = newState.dates.filter(
      _.withTimeAtStartOfDay < DateTime.now.withTimeAtStartOfDay
    ) groupBy (_.withTimeAtStartOfDay) foreach {
      adapter add _._2
    }
    adapter.notifyDataSetChanged
  }
}
