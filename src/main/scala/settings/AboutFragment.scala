package quit.app.settings

import android.os.Bundle
import android.preference.{PreferenceFragment, EditTextPreference}
import quit.app._

class AboutFragment extends PreferenceFragment {

  def activity = getActivity.asInstanceOf[QActivity]

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.settings_about)

    val text = findPreference("version").asInstanceOf[EditTextPreference]
    val version = activity.getPackageManager.getPackageInfo(activity.getPackageName, 0).versionName
    text.setSummary(version)
  }
}
