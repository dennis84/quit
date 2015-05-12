package quit.app.db

import android.content.Context
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}

class Db(context: Context)
  extends SQLiteOpenHelper(context, "quit", null, 2) {

  override def onCreate(db: SQLiteDatabase) {
    db.execSQL("""
      CREATE TABLE IF NOT EXISTS `dates`
      ( `_id` INTEGER PRIMARY KEY
      , `created_at` INTEGER );

      CREATE TABLE IF NOT EXISTS `configs`
      ( `_id` INTEGER PRIMARY KEY
      , `limit` INTEGER
      , `created_at` INTEGER );
    """)
  }

  override def onUpgrade(
    db: SQLiteDatabase,
    oldVersion: Int,
    newVersion: Int
  ) {
    db.execSQL("""
      CREATE TABLE IF NOT EXISTS `configs`
      ( `_id` INTEGER PRIMARY KEY
      , `limit` INTEGER
      , `created_at` INTEGER );
    """)
  }

  override def onDowngrade(
    db: SQLiteDatabase,
    oldVersion: Int,
    newVersion: Int
  ) {
    db.execSQL("DROP TABLE IF EXISTS `configs`;")
  }
}
