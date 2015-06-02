package com.github.dennis84.quit.ui

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.squareup.otto._
import com.github.dennis84.quit.core._

trait QActivity extends FragmentActivity {
  val env = new Env(this)

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    env.bus.register(this)
  }

  override def onDestroy {
    super.onDestroy
    env.bus.unregister(this)
  }

  override def onResume {
    super.onResume
    env.updateState
    env.bus.post(new UpdateUI)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    env.state = event.state
    env.bus.post(new UpdateUI)
  }
}
