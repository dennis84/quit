package com.github.dennis84.quit.ui.notification

import android.app.NotificationManager
import android.content.{Context, Intent, BroadcastReceiver}
import android.preference.PreferenceManager
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.ui.MainActivity

class NotificationReceiver extends BroadcastReceiver {

  override def onReceive(context: Context, intent: Intent) {
    val notificationManager = context.systemService[NotificationManager]
    val mainIntent = new Intent(context, classOf[MainActivity])
    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    intent.getAction match {
      case "com.github.dennis84.quit.notification.OK" =>
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        val goal = DateTime.now + 30.minutes
        settings.edit.putLong("goal_date", goal.getMillis).commit
        AlarmScheduler.schedule(context, goal)
        notificationManager.cancel(0)
    }

    context.startActivity(mainIntent)
  }
}
