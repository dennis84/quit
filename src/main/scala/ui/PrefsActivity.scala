package quit.ui

import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.EditTextPreference
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener

class PrefsActivity extends PreferenceActivity with OnSharedPreferenceChangeListener {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.prefs)
    val prefs = getPreferenceScreen.getSharedPreferences
    val hours = findPreference("goal_hours").asInstanceOf[EditTextPreference]
    val minutes = findPreference("goal_minutes").asInstanceOf[EditTextPreference]
    val limit = findPreference("goal_limit").asInstanceOf[EditTextPreference]
    hours.setSummary(prefs.getString("goal_hours", "-"))
    minutes.setSummary(prefs.getString("goal_minutes", "-"))
    limit.setSummary(prefs.getString("goal_limit", "-"))
  }

  override def onResume {
    super.onResume
    getPreferenceScreen.getSharedPreferences.registerOnSharedPreferenceChangeListener(this)
  }

  override def onPause {
    super.onPause
    getPreferenceScreen.getSharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
  }

  def onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
    val pref = findPreference(key)
    if(pref.isInstanceOf[EditTextPreference]) {
      val text = pref.asInstanceOf[EditTextPreference]
      pref.setSummary(text.getText)

      val hours = sharedPreferences.getString("goal_hours", "2").toInt
      val minutes = sharedPreferences.getString("goal_minutes", "0").toInt
      val limit = sharedPreferences.getString("goal_limit", "0").toInt
      val goal = (hours * 60 * 60 * 1000) + (minutes * 60 * 1000)
      sharedPreferences.edit.putInt("goal", goal).commit
      sharedPreferences.edit.putInt("limit", limit).commit
    }
  }
}
