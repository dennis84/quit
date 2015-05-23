package com.github.dennis84.quit.ui.settings

import android.os.Bundle
import android.preference.PreferenceFragment
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.R

class NotificationsFragment extends PreferenceFragment {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.settings_notifications)
  }
}
