package quit.app.db

import android.content.{ContentValues, Context}
import android.database.Cursor
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}

class Db(context: Context)
  extends SQLiteOpenHelper(context, "quit", null, 1) {

  override def onCreate(db: SQLiteDatabase) {
    db.execSQL("""
      CREATE TABLE IF NOT EXISTS dates
      ( _id INTEGER PRIMARY KEY
      , created_at INTEGER );
    """)
  }

  override def onUpgrade(
    db: SQLiteDatabase,
    oldVersion: Int,
    newVersion: Int
  ) {}
}
