package quit.app.toolbar

import android.os.Bundle
import android.content.Intent
import android.view.{LayoutInflater, ViewGroup, View}
import android.support.v7.widget.Toolbar
import quit.app.settings.SettingsActivity
import quit.app.stats.StatsActivity
import quit.app._

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
      case item if(item.getItemId == R.id.toolbar_settings) =>
        startActivity(new Intent(activity.getApplicationContext, classOf[SettingsActivity]))

      case item if(item.getItemId == R.id.toolbar_stats) =>
        startActivity(new Intent(activity.getApplicationContext, classOf[StatsActivity]))
    }

    toolbar.inflateMenu(R.menu.main)
  }
}
