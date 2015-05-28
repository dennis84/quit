package com.github.dennis84.quit.ui

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.squareup.otto._
import com.github.dennis84.quit.core._

trait QActivity extends FragmentActivity {
  val bus = new Bus
  val env = new Env(this, bus)
  def state = env.state

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)
  }

  override def onDestroy {
    super.onDestroy
    bus.unregister(this)
  }

  override def onResume {
    super.onResume
    env.updateState
    bus.post(new UpdateUI)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    env.state = event.state
    bus.post(new UpdateUI)
  }
}
