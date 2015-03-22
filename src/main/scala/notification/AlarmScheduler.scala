package quit.app.notification

import android.app.{AlarmManager, PendingIntent}
import android.content.{Context, Intent}
import com.github.nscala_time.time.Imports._
import quit.app._

object AlarmScheduler {

  def schedule(context: Context, at: DateTime) {
    if(at < DateTime.now) return
    val alarmManager = context.systemService[AlarmManager]
    val intent = new Intent(context, classOf[AlarmReceiver])
    val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    alarmManager.set(AlarmManager.RTC_WAKEUP, at.getMillis, alarmIntent)
  }

  def cancel(context: Context) {
    val alarmManager = context.systemService[AlarmManager]
    val intent = new Intent(context, classOf[AlarmReceiver])
    val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    alarmManager.cancel(alarmIntent)
  }
}
