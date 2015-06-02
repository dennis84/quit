package com.github.dennis84.quit.core

import android.content.Context
import android.preference.PreferenceManager
import com.squareup.otto.Bus
import org.joda.time.DateTime

class Env(context: Context) {

  lazy val bus = new Bus
  lazy val db = new Db(context)
  lazy val dateRepo = new DateRepo(db)
  lazy val configRepo = new ConfigRepo(db)
  lazy val ctrl = new Ctrl(bus, dateRepo, configRepo)
  var state = State()

  def updateState {
    val settings = PreferenceManager.getDefaultSharedPreferences(context)
    val id = settings.getString("id", Rand nextString 8)
    val goal = settings.getInt("goal", 7200000)
    val limit = settings.getInt("limit", 10)
    val notificationsEnabled = settings.getBoolean("notifications_enabled", true)
    val goalDate = settings.getLong("goal_date", 0) match {
      case 0 => None
      case x => Some(new DateTime(x))
    }

    if(!settings.contains("id")) {
      settings.edit.putString("id", id).commit
    }

    state = state.copy(
      goal = goal,
      limit = limit,
      notificationsEnabled = notificationsEnabled,
      goalDate = goalDate)
  }
}
