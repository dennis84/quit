package quit.android

import android.os.Bundle
import android.content.Context
import android.support.v4.app.FragmentActivity
import org.scaloid.common._
import com.squareup.otto._
import android.util.AttributeSet

class MainActivity extends FragmentActivity {

  var id: String = ""
  var client: Client = null
  val bus = new Bus

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)

    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    id = settings.getString("id", Rand nextString 8)

    if(!settings.contains("id")) {
      settings.edit.putString("id", id).commit
    }

    client = new Client(id)
    setContentView(R.layout.main)
  }

  override def onCreateView(name: String, context: Context, attrs: AttributeSet) = {
    val view = super.onCreateView(name, context, attrs)
    bus.post(Env(id, client))
    view
  }
}
