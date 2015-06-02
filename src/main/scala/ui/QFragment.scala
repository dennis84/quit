package com.github.dennis84.quit.ui

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.View

trait QFragment extends Fragment {
  def activity = getActivity.asInstanceOf[QActivity]
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
