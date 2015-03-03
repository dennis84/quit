package quit.ui

import android.content.Context

class Env(context: Context) {

  lazy val db = new quit.db.Db(context)
  lazy val repo = new quit.db.Repo(db)
}
