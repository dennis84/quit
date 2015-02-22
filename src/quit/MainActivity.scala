package quit.android

import org.scaloid.common._
import android.content.Context
import android.widget.TextView
import android.widget.Button
import scala.concurrent.ExecutionContext.Implicits.global

class MainActivity extends SActivity {

  onCreate {
    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    val id = settings.getString("id", Rand nextString 8)

    if(!settings.contains("id")) {
      settings.edit().putString("id", id).commit()
    }

    val client = new Client(id)
    setContentView(R.layout.main)

    val text = find[TextView](R.id.text)
    val btn = find[Button](R.id.button)

    btn.onClick(client.put.onSuccess {
      case xs => runOnUiThread(text.setText(Date.humanize(xs.last)))
    })

    client.list.onSuccess {
      case Nil => toast("...")
      case xs => runOnUiThread(text.setText(Date.humanize(xs.last)))
    }
  }
}
