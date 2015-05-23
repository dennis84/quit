package com.github.dennis84.quit.tweaks

import android.app.{NotificationManager, AlarmManager}
import android.view.LayoutInflater
import android.content.Context

trait SystemService[T] {
  def get(context: Context): T
}

trait ContextTweaks {

  private def systemServiceOf[T](const: String) = new SystemService[T] {
    def get(context: Context) = context.getSystemService(const).asInstanceOf[T]
  }

  implicit val nm = systemServiceOf[NotificationManager](Context.NOTIFICATION_SERVICE)
  implicit val am = systemServiceOf[AlarmManager](Context.ALARM_SERVICE)

  implicit val li = systemServiceOf[LayoutInflater](Context.LAYOUT_INFLATER_SERVICE)

  implicit class SystemServices(context: Context) {
    def systemService[T : SystemService](implicit service: SystemService[T]) = service get context
  }
}
