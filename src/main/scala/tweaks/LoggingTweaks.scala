package quit.app.tweaks

trait LoggingTweaks {

  def debug(msg: Any) {
    android.util.Log.d("QUIT", s"${msg}")
  }
}
