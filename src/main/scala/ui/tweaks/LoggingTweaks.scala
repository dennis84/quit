package quit.ui.tweaks

trait LoggingTweaks {

  def debug(msg: Any) {
    android.util.Log.d("QUIT", s"${msg}")
  }
}
