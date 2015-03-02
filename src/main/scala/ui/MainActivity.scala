package quit.ui

import android.os.Bundle
import android.content.Context
import com.squareup.otto._
import quit.util.Rand
import android.preference.PreferenceManager

class MainActivity extends QActivity {

  var env: Env = null
  var state: State = null

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)

    val settings = PreferenceManager.getDefaultSharedPreferences(this)
    val id = settings.getString("id", Rand nextString 8)
    val goal = settings.getInt("goal", 7200000)
    val limit = settings.getInt("limit", 10)

    if(!settings.contains("id")) {
      settings.edit.putString("id", id).commit
    }

    state = State(goal, limit)
    env = new Env(this)
    setContentView(R.layout.main)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    settings.edit.putInt("goal", event.state.goal).commit
    settings.edit.putInt("limit", event.state.limit).commit
    state = event.state
  }
}
