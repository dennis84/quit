package com.github.dennis84.quit.tweaks

trait LoggingTweaks {

  def debug(xs: Any*) {
    android.util.Log.d("QUIT", s"${xs}")
  }
}
