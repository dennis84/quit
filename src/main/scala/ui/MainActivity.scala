package quit.ui

import android.os.Bundle
import android.content.Context
import android.preference.PreferenceManager
import com.squareup.otto._
import quit.util.Rand

class MainActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
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
  def update(newState: State) {
    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    settings.edit.putInt("goal", newState.goal).commit
    settings.edit.putInt("limit", newState.limit).commit
    state = newState
  }
}
