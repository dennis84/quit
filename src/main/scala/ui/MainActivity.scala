package quit.ui

import android.app.{AlarmManager, PendingIntent}
import android.os.Bundle
import android.content.{Context, Intent}
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import quit.util.Rand
import quit.ui.notification._

class MainActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    val id = settings.getString("id", Rand nextString 8)
    val goal = settings.getInt("goal", 7200000)
    val limit = settings.getInt("limit", 10)
    val currentGoal = settings.getInt("current_goal", 0) match {
      case 0 => None
      case x => Some(x)
    }

    if(!settings.contains("id")) {
      settings.edit.putString("id", id).commit
    }

    state = State(goal, limit, currentGoal)
    env = new Env(this)
    setContentView(R.layout.main)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val goalDate = event.state.dates.lastOption.getOrElse(DateTime.now) +
                   event.state.currentGoal.getOrElse(event.state.goal).millis

    android.util.Log.d("QUIT", "onChangeState")

    val alarmManager = getSystemService(Context.ALARM_SERVICE).asInstanceOf[AlarmManager]
    val intent = new Intent(this, classOf[AlarmReceiver])
    val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
    alarmManager.set(AlarmManager.RTC_WAKEUP, goalDate.getMillis, alarmIntent)

    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    settings.edit.putInt("goal", event.state.goal).commit
    settings.edit.putInt("limit", event.state.limit).commit
    state = event.state
  }
}
