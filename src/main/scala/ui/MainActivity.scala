package com.github.dennis84.quit.ui

import android.os.Bundle
import com.github.dennis84.quit.R
import com.github.nscala_time.time.Imports._

class MainActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
  }
}
