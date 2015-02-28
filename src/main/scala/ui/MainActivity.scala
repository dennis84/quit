package quit.ui

import android.os.Bundle
import android.content.{Context, Intent}
import android.widget.Toolbar
import android.view.MenuItem
import com.squareup.otto._
import quit.util.Rand
import android.preference.PreferenceManager

class MainActivity extends QActivity {

  var env: Env = null
  var state: State = null

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)

    val settings = PreferenceManager.getDefaultSharedPreferences(this)
    val id = settings.getString("id", Rand nextString 8)
    val goal = settings.getInt("goal", 7200000)

    if(!settings.contains("id")) {
      settings.edit.putString("id", id).commit
    }

    if(!settings.contains("goal")) {
      settings.edit.putInt("goal", goal).commit
    }

    state = State(goal)
    env = new Env(id, getResources.getString(R.string.url))
    setContentView(R.layout.main)

    val toolbar = findViewById(R.id.toolbar).asInstanceOf[Toolbar]
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener {
      override def onMenuItemClick(item: MenuItem) = item.getItemId match {
        case id if (id == R.id.toolbar_settings) => {
          startActivity(new Intent(getApplicationContext, classOf[PrefsActivity]))
          true
        }

        case _ => true
      }
    })

    toolbar.inflateMenu(R.menu.main)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    settings.edit.putInt("goal", event.state.goal).commit
    state = event.state
  }

  def toast(text: String) {
    android.widget.Toast.makeText(getApplicationContext, text, android.widget.Toast.LENGTH_SHORT).show
  }
}
