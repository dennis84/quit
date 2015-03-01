package quit.ui.toolbar

import android.os.Bundle
import android.content.Intent
import android.view.{LayoutInflater, ViewGroup}
import android.widget.Toolbar
import android.view.MenuItem
import quit.ui._

class ToolbarFragment extends QFragment {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = {
    val view = inflater.inflate(R.layout.toolbar, container, false)
    val toolbar = view.findViewById(R.id.toolbar_toolbar).asInstanceOf[Toolbar]
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener {
      override def onMenuItemClick(item: MenuItem) = item.getItemId match {
        case id if (id == R.id.toolbar_settings) => {
          startActivity(new Intent(activity.getApplicationContext, classOf[PrefsActivity]))
          true
        }

        case _ => true
      }
    })

    toolbar.inflateMenu(R.menu.main)
    view
  }
}
