package quit.ui.notification

import android.app.{PendingIntent, Notification, NotificationManager}
import android.preference.PreferenceManager
import android.content.{Context, Intent, BroadcastReceiver}
import android.graphics.Color
import quit.ui._

class AlarmReceiver extends BroadcastReceiver {

  override def onReceive(context: Context, intent: Intent) {
    val settings = PreferenceManager.getDefaultSharedPreferences(context)
    val notificationsEnabled = settings.getBoolean("notifications_enabled", true)
    if(!notificationsEnabled) {
      return
    }

    val contentIntent = new Intent(context, classOf[MainActivity])
    val contentPending = PendingIntent.getActivity(context, 0, contentIntent, 0)

    val okIntent = new Intent("quit.ui.notification.OK")
    val okPending = PendingIntent.getBroadcast(context, 1, okIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    val noIntent = new Intent("quit.ui.notification.NO")
    val noPending = PendingIntent.getBroadcast(context, 2, noIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    val notification = new Notification.Builder(context)
      .setContentTitle("Congrats, you've reached your goal!")
      .setContentText("Want to extend your goal by half an hour?")
      .setSmallIcon(R.drawable.icon)
      .setContentIntent(contentPending)
      .setVibrate(Array(0, 300l, 300l))
      .setLights(Color.WHITE, 3000, 3000)
      .addAction(R.drawable.icon, "Ok", okPending)
      .addAction(R.drawable.icon, "No", noPending)
      .build()

    val notificationManager = context.systemService[NotificationManager]
    notificationManager.notify(0, notification)
  }
}
