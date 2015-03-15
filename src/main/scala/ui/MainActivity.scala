package quit.ui

import android.os.Bundle
import android.content.{Context, Intent}
import android.preference.PreferenceManager
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import quit.util.Rand

class MainActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val settings = PreferenceManager.getDefaultSharedPreferences(this)
    val id = settings.getString("id", Rand nextString 8)
    val goal = settings.getInt("goal", 7200000)
    val limit = settings.getInt("limit", 10)
    val goalDate = settings.getLong("goal_date", 0) match {
      case 0 => None
      case x => Some(new DateTime(x))
    }

    if(!settings.contains("id")) {
      settings.edit.putString("id", id).commit
    }

    state = State(goal, limit, goalDate)
    env = new Env(this, bus)
    setContentView(R.layout.main)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    state = event.state
    bus.post(new UpdateUI)
  }
}
