package quit.ui

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.View

trait QFragment extends Fragment {
  def activity = getActivity.asInstanceOf[QActivity]
  def bus = activity.bus
  def env = activity.env
  def state = activity.state
  var viewCreated = false

  override def onResume {
    super.onResume
    bus.register(this)
  }

  override def onPause {
    super.onPause
    bus.unregister(this)
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    viewCreated = true
  }
}
