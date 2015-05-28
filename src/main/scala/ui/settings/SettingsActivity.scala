package com.github.dennis84.quit.ui.settings

import android.os.Bundle
import com.github.dennis84.quit.ui.QActivity
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.R

class SettingsActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.settings)
  }
}
