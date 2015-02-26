package quit.ui

import android.support.v4.app.FragmentActivity
import com.squareup.otto.Bus

trait QActivity extends FragmentActivity {
  val bus = new Bus
  var env: Env
  var state: State
}
