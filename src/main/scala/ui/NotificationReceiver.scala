package quit.ui

import android.app.NotificationManager
import android.content.{Context, Intent, BroadcastReceiver}

class NotificationReceiver extends BroadcastReceiver {

  override def onReceive(context: Context, intent: Intent) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
      .asInstanceOf[NotificationManager]

    intent.getAction match {
      case "quit.ui.OK" =>
        val settings = context.getSharedPreferences("quit.android", Context.MODE_PRIVATE)
        settings.edit.putInt("current_goal", 7200000 + 30 * 60 * 1000).commit
        notificationManager.cancel(0)
      case "quit.ui.NO" =>
        notificationManager.cancel(0)
    }
  }
}
