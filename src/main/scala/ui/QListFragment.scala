package com.github.dennis84.quit.ui

import android.app.ListFragment
import android.os.Bundle
import android.view.View

trait QListFragment[A] extends ListFragment {
  def activity = getActivity.asInstanceOf[QActivity]
  def adapter = getListAdapter.asInstanceOf[A]
  def env = activity.env

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    env.bus.register(this)
  }

  override def onDestroy {
    super.onDestroy
    env.bus.unregister(this)
  }
}
