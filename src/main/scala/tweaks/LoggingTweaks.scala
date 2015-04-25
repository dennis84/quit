package quit.app.tweaks

trait LoggingTweaks {

  def debug(xs: Any*) {
    android.util.Log.d("QUIT", s"${xs}")
  }
}
