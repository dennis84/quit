package quit.app

import android.app.ListFragment
import android.os.Bundle
import android.view.View

trait QListFragment[A] extends ListFragment {
  def activity = getActivity.asInstanceOf[QActivity]
  def bus = activity.bus
  def env = activity.env
  def state = activity.state
  var viewCreated = false
  def adapter = getListAdapter.asInstanceOf[A]

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)
  }

  override def onDestroy {
    super.onDestroy
    bus.unregister(this)
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    viewCreated = true
  }
}
