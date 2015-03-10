package quit.ui.tweaks

import android.widget.Toolbar
import android.widget.Toolbar.OnMenuItemClickListener
import android.view.MenuItem

trait ToolbarTweaks {

  implicit class ToolbarClick(toolbar: Toolbar) {
    def onClick(f: MenuItem => Unit) =
      toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener {
        override def onMenuItemClick(item: MenuItem) = {
          f(item)
          true
        }
      })
  }
}
