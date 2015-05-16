package quit.app.settings

import android.os.Bundle
import android.preference.PreferenceFragment
import quit.tweaks.FullDsl._
import quit.app._

class NotificationsFragment extends PreferenceFragment {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.settings_notifications)
  }
}
