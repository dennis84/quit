package com.github.dennis84.quit.core

import android.content.ContentValues
import org.joda.time.DateTime

class ConfigRepo(db: Db) {

  def insert(conf: Config) {
    val values = new ContentValues
    val timestamp: java.lang.Long = conf.createdAt.getMillis
    val limit: java.lang.Integer = conf.limit
    values.put("`limit`", limit)
    values.put("created_at", timestamp)
    db.getWritableDatabase.insert("configs", null, values)
  }

  def list = {
    val configs = scala.collection.mutable.ListBuffer.empty[Config]
    val cursor = db.getReadableDatabase.rawQuery(s"""
      SELECT * FROM `configs`
      ORDER BY `created_at` DESC
    """, null)

    if(cursor.moveToFirst) {
      do {
        configs += Config(
          cursor.getInt(1),
          new DateTime(cursor.getLong(2)))
      } while(cursor.moveToNext)
    }

    configs.toList
  }
}
