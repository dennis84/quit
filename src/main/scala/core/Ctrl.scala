package com.github.dennis84.quit.core

import android.app.NotificationManager
import android.content.Context
import android.preference.PreferenceManager
import com.squareup.otto.Bus
import com.github.dennis84.quit.ui.notification.AlarmScheduler
import com.github.dennis84.quit.tweaks.FullDsl._

class Ctrl(bus: Bus, dateRepo: DateRepo, configRepo: ConfigRepo) {

  def list(state: State, page: Int = 1) {
    val newState = state.copy(dates = state.dates ::: dateRepo.list(page))
    bus.post(new ChangeState(newState.withDays))
  }

  def add(state: State, context: Context) {
    val notificationManager = context.systemService[NotificationManager]
    notificationManager.cancel(0)

    val date = DateTime.now
    dateRepo.insert(date)

    val settings = PreferenceManager.getDefaultSharedPreferences(context)
    val goalDate = date + state.goal.millis
    settings.edit.putLong("goal_date", goalDate.getMillis).commit

    if(state.notificationsEnabled) {
      AlarmScheduler.schedule(context, goalDate)
    }

    bus.post(new ChangeState(state.copy(
      dates = date :: state.dates,
      goalDate = Some(goalDate)
    ).withDays))
  }

  def stats(state: State) {
    bus.post(new ChangeState(state.copy(
      dates = dateRepo.listAll,
      configs = configRepo.list
    ).withDays))
  }
}
