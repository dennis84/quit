package quit.app.stats

import android.os.Bundle
import quit.tweaks.QActivity
import quit.tweaks.FullDsl._
import quit.app._

class StatsActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.stats)
  }
}