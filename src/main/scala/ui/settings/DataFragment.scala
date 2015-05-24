package com.github.dennis84.quit.ui.settings

import android.os.Bundle
import android.app.AlertDialog
import android.content.DialogInterface
import android.preference.{PreferenceFragment, Preference}
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.tweaks.QActivity
import com.github.dennis84.quit.R

class DataFragment extends PreferenceFragment {

  def activity = getActivity.asInstanceOf[QActivity]
  def env = activity.env

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.settings_data)
    val btn = findPreference("data_delete").asInstanceOf[Preference]

    btn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener {
      override def onPreferenceClick(preference: Preference) = {
        val builder = new AlertDialog.Builder(activity)
        builder.setMessage("Delete all data")
          .setPositiveButton("Ok", new DialogInterface.OnClickListener {
            def onClick(dialog: DialogInterface, id: Int) {
              val db = env.db.getWritableDatabase
              db.execSQL("DELETE FROM `dates`")
              db.execSQL("DELETE FROM `configs`")
            }
          })
          .setNegativeButton("Cancel", new DialogInterface.OnClickListener {
            def onClick(dialog: DialogInterface, id: Int) {}
          })

        val dialog = builder.create
        dialog.show
        true
      }
    })
  }
}
