package quit.ui

import android.support.v4.app.FragmentActivity
import com.squareup.otto.Bus

trait QActivity extends FragmentActivity {
  val bus = new Bus
  var env: Env = _
  var state: State = _

  override def onStart {
    super.onStart
    bus.register(this)
  }

  override def onStop {
    super.onStop
    bus.unregister(this)
  }
}
