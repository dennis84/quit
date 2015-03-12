package quit.ui.notification

import android.app.{PendingIntent, Notification, NotificationManager}
import android.content.{Context, Intent, BroadcastReceiver}
import quit.ui._

class AlarmReceiver extends BroadcastReceiver {

  override def onReceive(context: Context, intent: Intent) {
    val settings = context.getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    settings.edit.putInt("current_goal", 0).commit

    val contentIntent = new Intent(context, classOf[MainActivity])
    val contentPending = PendingIntent.getActivity(context, 0, contentIntent, 0)

    val okIntent = new Intent("quit.ui.notification.OK")
    val okPending = PendingIntent.getBroadcast(context, 1, okIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    val noIntent = new Intent("quit.ui.notification.NO")
    val noPending = PendingIntent.getBroadcast(context, 2, noIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    val notification = new Notification.Builder(context)
        .setContentTitle("Congrats, you've reached your goal!")
        .setContentText("Subject")
        .setSmallIcon(R.drawable.icon)
        .setContentIntent(contentPending)
        .setAutoCancel(true)
        .addAction(R.drawable.icon, "Ok", okPending)
        .addAction(R.drawable.icon, "No", noPending)
        .build()

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
      .asInstanceOf[NotificationManager]
    notificationManager.notify(0, notification)
  }
}
