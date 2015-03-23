package quit.app

import android.content.Context
import android.preference.PreferenceManager
import com.squareup.otto.Bus
import com.github.nscala_time.time.Imports._
import quit.app.util.Rand

class Env(context: Context, bus: Bus) {

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

  var state = State(goal, limit, notificationsEnabled, goalDate)

  lazy val db = new quit.app.db.Db(context)
  lazy val repo = new quit.app.db.Repo(db)
  lazy val ctrl = new Ctrl(bus, repo)
}
