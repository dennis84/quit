package com.github.dennis84.quit.ui.button

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import com.melnykov.fab.FloatingActionButton
import com.github.dennis84.quit.tweaks.QFragment
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.R

class ButtonFragment extends QFragment {

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.button, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    val btn = view.find[FloatingActionButton](R.id.button_add)
    btn onClick env.ctrl.add(state, activity)
  }
}
