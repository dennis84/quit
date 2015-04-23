package quit.app.settings

import android.os.Bundle
import quit.app._

class SettingsActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.settings)
  }
}
