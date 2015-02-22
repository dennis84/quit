package quit.android

import org.scaloid.common._
import android.content.Context

class MainActivity extends SActivity {

  onCreate {
    val settings = getSharedPreferences("quit.android", Context.MODE_PRIVATE)
    val id = settings.getString("id", Rand nextString 8)

    if(!settings.contains("id")) {
      settings.edit().putString("id", id).commit()
    }

    val client = new Client(id)

    contentView = new SVerticalLayout {
      STextView("You are %s h smokefree.") textSize 15.dip
      SButton("Press me", client.put(res => {
        val out = res.map(_.toString)
        toast(out.mkString(", "))
      }))
    } padding 20.dip
  }
}
