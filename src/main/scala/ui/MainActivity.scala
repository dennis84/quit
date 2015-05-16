package quit.app

import android.os.Bundle
import quit.tweaks.QActivity

class MainActivity extends QActivity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
  }
}
