package quit.ui

import android.app.ListFragment
import android.os.Bundle
import android.view.View

trait QListFragment extends ListFragment {
  def activity = getActivity.asInstanceOf[QActivity]
  def bus = activity.bus
  def env = activity.env
  def state = activity.state
  var viewCreated = false

  override def onStart {
    super.onStart
    bus.register(this)
  }

  override def onStop {
    super.onStop
    bus.unregister(this)
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    viewCreated = true
  }
}
