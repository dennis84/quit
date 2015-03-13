package quit.ui.tweaks

import android.app.NotificationManager
import android.content.Context

trait ContextTweaks {

  trait SystemService[+T] {
    def get(context: Context): T
  }

  private def systemServiceOf[T](const: String) = new SystemService[T] {
    def get(context: Context) = context.getSystemService(const).asInstanceOf[T]
  }

  implicit val nm = systemServiceOf[NotificationManager](Context.NOTIFICATION_SERVICE)

  implicit class SystemServices(context: Context) {
    def systemService[T : SystemService](implicit service: SystemService[T]) = service get context
  }
}
