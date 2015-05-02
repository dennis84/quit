package quit.app

import android.app.NotificationManager
import android.content.Context
import android.preference.PreferenceManager
import com.squareup.otto.Bus
import com.github.nscala_time.time.Imports._
import quit.app.db.Repo
import quit.app.notification._

class Ctrl(bus: Bus, repo: Repo) {

  def list(state: State, page: Int = 1) {
    val newState = state.copy(connected = true, dates = state.dates ::: repo.list(page))
    bus.post(new ChangeState(newState.withDays))
  }

  def listAll(state: State) {
    val newState = state.copy(connected = true, dates = repo.listAll)
    bus.post(new ChangeState(newState.withDays))
  }

  def add(state: State, context: Context) {
    val notificationManager = context.systemService[NotificationManager]
    notificationManager.cancel(0)

    val date = DateTime.now
    repo.insert(date)

    val settings = PreferenceManager.getDefaultSharedPreferences(context)
    val goalDate = date + state.goal.millis
    settings.edit.putLong("goal_date", goalDate.getMillis).commit

    if(state.notificationsEnabled) {
      AlarmScheduler.schedule(context, goalDate)
    }

    bus.post(new ChangeState(state.copy(
      dates = date :: state.dates,
      goalDate = Some(goalDate)
    ).withDays))
  }
}
