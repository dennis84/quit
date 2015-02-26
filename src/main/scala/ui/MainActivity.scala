package quit.ui

import android.os.Bundle
import android.content.Context
import com.squareup.otto._
import quit.util.Rand

class MainActivity extends QActivity {

  var env: Env = null
  var state: State = null

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)

    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    val id = settings.getString("id", Rand nextString 8)
    val goal = settings.getInt("goal", 7200000)

    if(!settings.contains("id")) {
      settings.edit.putString("id", id).commit
    }

    if(!settings.contains("goal")) {
      settings.edit.putInt("goal", goal).commit
    }

    state = State(goal)
    env = new Env(id, getResources.getString(R.string.url))
    setContentView(R.layout.main)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    settings.edit.putInt("goal", event.state.goal).commit
    state = event.state
  }
}
