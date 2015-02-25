package quit.ui

import android.support.v4.app.FragmentActivity
import com.squareup.otto.Bus

trait QActivity extends FragmentActivity {
  lazy val bus = new Bus
}
