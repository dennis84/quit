package quit.ui.notification

import android.app.NotificationManager
import android.content.{Context, Intent, BroadcastReceiver}
import android.preference.PreferenceManager
import com.github.nscala_time.time.Imports._
import quit.ui._

class NotificationReceiver extends BroadcastReceiver {

  override def onReceive(context: Context, intent: Intent) {
    val notificationManager = context.systemService[NotificationManager]

    intent.getAction match {
      case "quit.ui.notification.OK" =>
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        val goal = DateTime.now + 30.minutes
        settings.edit.putLong("goal_date", goal.getMillis).commit
        notificationManager.cancel(0)
      case "quit.ui.notification.NO" =>
        notificationManager.cancel(0)
    }
  }
}
