package com.github.dennis84.quit.ui.toolbar

import android.os.Bundle
import android.content.Intent
import android.view.{LayoutInflater, ViewGroup, View}
import android.net.Uri
import android.support.v7.widget.Toolbar
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.ui.QFragment
import com.github.dennis84.quit.ui.settings.SettingsActivity
import com.github.dennis84.quit.ui.stats.{StatsActivity, GraphActivity}
import com.github.dennis84.quit.R

class ToolbarFragment extends QFragment {

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.toolbar, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    val toolbar = view.find[Toolbar](R.id.toolbar_toolbar)

    toolbar onClick {
      case item if(item.getItemId == R.id.toolbar_stats) =>
        startActivity(new Intent(activity.getApplicationContext, classOf[StatsActivity]))

      case item if(item.getItemId == R.id.toolbar_graph) =>
        startActivity(new Intent(activity.getApplicationContext, classOf[GraphActivity]))

      case item if(item.getItemId == R.id.toolbar_settings) =>
        startActivity(new Intent(activity.getApplicationContext, classOf[SettingsActivity]))

      case item if(item.getItemId == R.id.toolbar_feedback) => {
        implicit val ctx = activity
        startActivity(Intent.createChooser(new Intent(
          Intent.ACTION_SENDTO,
          Uri.parse("mailto:d.dietrich84@gmail.com")
        ), "Send email"))
      }
    }

    toolbar.inflateMenu(R.menu.main)
  }
}
