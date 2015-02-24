package quit.android

import android.os.Bundle
import android.content.Context
import android.util.AttributeSet
import org.scaloid.common._

class MainActivity extends QActivity {

  var client: Option[Client] = None

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)

    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    val id = settings.getString("id", Rand nextString 8)

    if(!settings.contains("id")) {
      settings.edit.putString("id", id).commit
    }

    client = Some(new Client(id, getResources.getString(R.string.url)))
    setContentView(R.layout.main)
  }

  override def onCreateView(name: String, context: Context, attrs: AttributeSet) = {
    val view = super.onCreateView(name, context, attrs)
    client foreach (c => bus post Env(c))
    view
  }
}
