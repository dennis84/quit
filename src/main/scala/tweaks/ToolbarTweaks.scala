package com.github.dennis84.quit.tweaks

import android.support.v7.widget.Toolbar
import android.support.v7.widget.Toolbar.OnMenuItemClickListener
import android.view.MenuItem

trait ToolbarTweaks {

  implicit class ToolbarClick(toolbar: Toolbar) {
    def onClick(f: MenuItem => Unit) =
      toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener {
        def onMenuItemClick(item: MenuItem) = {
          f(item)
          true
        }
      })
  }
}
