package quit.app

import android.content.Context
import android.preference.PreferenceManager
import com.squareup.otto.Bus
import quit.app.util.Rand

class Env(context: Context, bus: Bus) {
  
  lazy val db = new quit.app.db.Db(context)
  lazy val dateRepo = new quit.app.db.DateRepo(db)
  lazy val configRepo = new quit.app.db.ConfigRepo(db)
  lazy val ctrl = new Ctrl(bus, dateRepo, configRepo)
  var state = State()

  def updateState {
    val settings = PreferenceManager.getDefaultSharedPreferences(context)
    val id = settings.getString("id", Rand nextString 8)
    val goal = settings.getInt("goal", 7200000)
    val limit = settings.getInt("limit", 10)
    val notificationsEnabled = settings.getBoolean("notifications_enabled", true)
    val goalDate = settings.getLong("goal_date", 0) match {
      case 0 => None
      case x => Some(new DateTime(x))
    }

    if(!settings.contains("id")) {
      settings.edit.putString("id", id).commit
    }

    state = state.copy(
      goal = goal,
      limit = limit,
      notificationsEnabled = notificationsEnabled,
      goalDate = goalDate)
  }
}
