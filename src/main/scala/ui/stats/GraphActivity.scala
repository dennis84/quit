package com.github.dennis84.quit.ui.stats

import android.os.Bundle
import com.github.dennis84.quit.tweaks.QActivity
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.R

class GraphActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.graph)
  }
}
