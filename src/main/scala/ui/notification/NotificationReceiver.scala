package quit.ui.notification

import android.app.NotificationManager
import android.content.{Context, Intent, BroadcastReceiver}
import quit.ui._

class NotificationReceiver extends BroadcastReceiver {

  override def onReceive(context: Context, intent: Intent) {
    val notificationManager = context.systemService[NotificationManager]

    intent.getAction match {
      case "quit.ui.notification.OK" =>
        val settings = context.getSharedPreferences("quit.android", Context.MODE_PRIVATE)
        val goal = settings.getInt("goal", 7200000)
        settings.edit.putInt("current_goal", goal + 30 * 60 * 1000).commit
        notificationManager.cancel(0)
      case "quit.ui.notification.NO" =>
        notificationManager.cancel(0)
    }
  }
}
