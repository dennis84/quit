package quit.app

import android.support.v4.app.FragmentActivity
import com.squareup.otto._

trait QActivity extends FragmentActivity {
  val bus = new Bus
  val env = new Env(this, bus)
  def state = env.state

  override def onStart {
    super.onStart
    bus.register(this)
  }

  override def onStop {
    super.onStop
    bus.unregister(this)
  }

  override def onResume {
    super.onResume
    env.updateState
    bus.post(new UpdateUI)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    env.state = event.state
    bus.post(new UpdateUI)
  }
}
