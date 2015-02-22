package quit.android

import org.scaloid.common._
import android.content.Context
import android.widget.TextView
import android.widget.ProgressBar
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import scala.concurrent.ExecutionContext.Implicits.global
import com.github.nscala_time.time.Imports._

class MainActivity extends SActivity {

  var text: TextView = null
  var progress: ProgressBar = null

  onCreate {
    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    val id = settings.getString("id", Rand nextString 8)

    if(!settings.contains("id")) {
      settings.edit().putString("id", id).commit()
    }

    val client = new Client(id)
    setContentView(R.layout.main)

    text = find[TextView](R.id.text)
    progress = find[ProgressBar](R.id.progress)

    text.onClick(client.put.onSuccess {
      case xs => update(xs.last)
    })

    client.list.onSuccess {
      case Nil => toast("...")
      case xs => update(xs.last)
    }
  }

  def update(date: DateTime) = runOnUiThread {
    text.setText(Date.humanize(date))
    val progr = if(date < DateTime.now) (date to DateTime.now).millis.toDouble / 7200000 * 1000 else 0
    val anim = ObjectAnimator.ofInt(progress, "progress", 1, progr.toInt)
    anim.setDuration(500)
    anim.setInterpolator(new DecelerateInterpolator())
    anim.start()
  }
}
