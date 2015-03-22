package quit.app

import android.content.Context
import com.squareup.otto.Bus

class Env(context: Context, bus: Bus) {

  lazy val db = new quit.app.db.Db(context)
  lazy val repo = new quit.app.db.Repo(db)
  lazy val ctrl = new Ctrl(bus, repo)
}
