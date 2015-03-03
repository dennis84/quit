package quit.app

import android.os.Handler

trait HandlerTweaks {

  def schedule(duration: Int, f: => Unit) {
    val handler = new Handler
    handler.postDelayed(new Runnable {
      override def run() {
        f
        handler.postDelayed(this, duration)
      }
    }, duration)
  }
}
