package com.github.dennis84.quit.ui.loading

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import com.squareup.otto._
import com.github.dennis84.quit.ui.QFragment
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.core._
import com.github.dennis84.quit.R

class LoadingFragment extends QFragment {

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.loading, container, false)

  override def onResume {
    super.onResume
    update(new UpdateUI)
  }

  @Subscribe
  def update(event: UpdateUI) {
    if(viewCreated && state.connected) {
      getView.setVisibility(View.GONE)
    }
  }
}
