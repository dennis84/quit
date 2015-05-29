package com.github.dennis84.quit.tweaks

import android.app.{NotificationManager, AlarmManager}
import android.view.LayoutInflater
import android.content.Context

trait SystemService[A] {
  def get(context: Context): A
}

trait ContextTweaks {

  private def systemServiceOf[A](const: String) = new SystemService[A] {
    def get(context: Context) = context.getSystemService(const).asInstanceOf[A]
  }

  implicit val nm = systemServiceOf[NotificationManager](Context.NOTIFICATION_SERVICE)
  implicit val am = systemServiceOf[AlarmManager](Context.ALARM_SERVICE)

  implicit val li = systemServiceOf[LayoutInflater](Context.LAYOUT_INFLATER_SERVICE)

  implicit class SystemServices(context: Context) {
    def systemService[A : SystemService](implicit service: SystemService[A]) = service get context
  }
}
