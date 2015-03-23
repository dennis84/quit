package quit.app

import android.os.Bundle
import com.squareup.otto._

class MainActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
  }

  override def onResume {
    super.onResume
    env = new Env(this, bus)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    env.state = event.state
    bus.post(new UpdateUI)
  }
}
