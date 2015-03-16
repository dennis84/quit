package quit.ui

import android.content.Context
import android.preference.PreferenceManager
import com.squareup.otto.Bus
import com.github.nscala_time.time.Imports._
import quit.db.Repo
import quit.ui.notification._

class Ctrl(bus: Bus, repo: Repo) {

  def list(state: State) {
    val newState = state.copy(connected = true, dates = repo.list)
    bus.post(new ChangeState(newState))
  }

  def add(state: State, context: Context) {
    val date = DateTime.now
    repo.insert(date)

    val settings = PreferenceManager.getDefaultSharedPreferences(context)
    val goalDate = date + state.goal.millis
    settings.edit.putLong("goal_date", goalDate.getMillis).commit
    AlarmScheduler.schedule(context, goalDate)

    bus.post(new ChangeState(state.copy(
      dates = date :: state.dates,
      goalDate = Some(goalDate)
    )))
  }
}
