package com.github.dennis84.quit.core

import android.content.Context
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}
import com.github.dennis84.quit.tweaks.FullDsl._

class Db(context: Context)
  extends SQLiteOpenHelper(context, "quit", null, 5) {

  override def onCreate(db: SQLiteDatabase) {
    db.execSQL("""
      CREATE TABLE IF NOT EXISTS `dates`
      ( `_id` INTEGER PRIMARY KEY
      , `created_at` INTEGER );
    """)
    db.execSQL("""
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
  ) = onCreate(db)

  override def onDowngrade(
    db: SQLiteDatabase,
    oldVersion: Int,
    newVersion: Int
  ) = onCreate(db)
}
