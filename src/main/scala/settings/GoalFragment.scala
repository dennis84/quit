package quit.app.settings

import android.os.Bundle
import android.preference.{PreferenceFragment, EditTextPreference}
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.github.nscala_time.time.Imports._
import java.util.concurrent.TimeUnit
import quit.app.notification._
import quit.app.db.Config
import quit.app._

class GoalFragment
  extends PreferenceFragment
  with OnSharedPreferenceChangeListener {

  def activity = getActivity.asInstanceOf[QActivity]
  def env = activity.env

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.settings_goal)

    val prefs = getPreferenceScreen.getSharedPreferences
    val hours = findPreference("goal_hours").asInstanceOf[EditTextPreference]
    val minutes = findPreference("goal_minutes").asInstanceOf[EditTextPreference]
    val limit = findPreference("goal_limit").asInstanceOf[EditTextPreference]

    val h = TimeUnit.MILLISECONDS.toHours(env.state.goal)
    val m = TimeUnit.MILLISECONDS.toMinutes(env.state.goal) - TimeUnit.HOURS.toMinutes(h)

    hours.setSummary(prefs.getString("goal_hours", h.toString))
    minutes.setSummary(prefs.getString("goal_minutes", m.toString))
    limit.setSummary(prefs.getString("goal_limit", env.state.limit.toString))
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
      env.configRepo.insert(Config(limit, DateTime.now))

      env.dateRepo.last foreach { date =>
        val goalDate = date + goal.millis
        sharedPreferences.edit.putLong("goal_date", goalDate.getMillis).commit
        AlarmScheduler.schedule(activity, goalDate)
      }
    }
  }
}
