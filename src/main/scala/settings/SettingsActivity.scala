package quit.app.settings

import android.os.Bundle
import com.squareup.otto._
import quit.app._

class SettingsActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    env = new Env(this, bus)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.settings)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    env.state = event.state
    bus.post(new UpdateUI)
  }
}
