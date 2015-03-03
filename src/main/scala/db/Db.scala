package quit.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
