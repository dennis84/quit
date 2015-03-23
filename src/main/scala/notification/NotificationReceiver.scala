package quit.app.notification

import android.app.NotificationManager
import android.content.{Context, Intent, BroadcastReceiver}
import android.preference.PreferenceManager
import com.github.nscala_time.time.Imports._
import quit.app._

class NotificationReceiver extends BroadcastReceiver {

  override def onReceive(context: Context, intent: Intent) {
    val notificationManager = context.systemService[NotificationManager]
    val mainIntent = new Intent(context, classOf[MainActivity])
    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    intent.getAction match {
      case "quit.app.notification.OK" =>
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        val goal = DateTime.now + 30.minutes
        settings.edit.putLong("goal_date", goal.getMillis).commit
        AlarmScheduler.schedule(context, goal)
        notificationManager.cancel(0)
      case "quit.app.notification.NO" =>
        notificationManager.cancel(0)
    }

    context.startActivity(mainIntent)
  }
}