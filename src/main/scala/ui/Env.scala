package quit.ui

import android.content.Context
import com.squareup.otto.Bus

class Env(context: Context, bus: Bus) {

  lazy val db = new quit.db.Db(context)
  lazy val repo = new quit.db.Repo(db)
  lazy val ctrl = new Ctrl(bus, repo)
}
