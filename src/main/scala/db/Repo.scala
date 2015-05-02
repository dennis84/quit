package quit.app.db

import android.content.ContentValues
import com.github.nscala_time.time.Imports._

class Repo(db: Db) {

  def insert(date: DateTime) {
    val values = new ContentValues
    val timestamp: java.lang.Long = date.getMillis
    values.put("created_at", timestamp)
    db.getWritableDatabase.insert("dates", null, values)
  }

  def list(page: Int): List[DateTime] = {
    val from = DateTime.now.withTimeAtStartOfDay - (30 * page).days
    val to = from + (if(1 == page) 31.days else 30.days)

    val dates = scala.collection.mutable.ListBuffer.empty[DateTime]
    val cursor = db.getReadableDatabase.rawQuery(s"""
      SELECT * FROM dates
      WHERE created_at BETWEEN ${from.getMillis} AND ${to.getMillis}
      ORDER BY created_at DESC
    """, null)

    if(cursor.moveToFirst) {
      do {
        dates += new DateTime(cursor.getLong(1))
      } while(cursor.moveToNext)
    }

    dates.toList
  }

  def listAll: List[DateTime] = {
    val dates = scala.collection.mutable.ListBuffer.empty[DateTime]
    val cursor = db.getReadableDatabase.rawQuery(s"""
      SELECT * FROM dates
      ORDER BY created_at DESC
    """, null)

    if(cursor.moveToFirst) {
      do {
        dates += new DateTime(cursor.getLong(1))
      } while(cursor.moveToNext)
    }

    dates.toList
  }

  def last: Option[DateTime] = {
    val cursor = db.getReadableDatabase.rawQuery("""
      SELECT * FROM dates
      ORDER BY created_at DESC
      LIMIT 1
    """, null)

    cursor match {
      case null => None
      case c => {
        c.moveToFirst
        Some(new DateTime(c.getLong(1)))
      }
    }
  }
}
