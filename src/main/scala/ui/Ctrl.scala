package quit.ui

import android.content.Context
import com.squareup.otto.Bus
import com.github.nscala_time.time.Imports._
import quit.db.Repo
import quit.ui.notification._

class Ctrl(bus: Bus, repo: Repo) {

  def list(state: State, context: Context) {
    val newState = state.copy(connected = true, dates = repo.list)
    val goalDate = newState.dates.lastOption.getOrElse(DateTime.now) +
                   newState.currentGoal.getOrElse(newState.goal).millis
    AlarmScheduler.schedule(context, goalDate)
    bus.post(new ChangeState(newState))
  }

  def add(state: State) {
    val date = DateTime.now
    repo.insert(date)
    bus.post(new ChangeState(state.copy(
      dates = state.dates ::: List(date)
    )))
  }
}
