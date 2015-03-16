package quit.db

import android.content.ContentValues
import org.joda.time.DateTime

class Repo(db: Db) {

  def insert(date: DateTime) {
    val values = new ContentValues;
    val timestamp: java.lang.Long = date.getMillis
    values.put("created_at", timestamp)
    db.getWritableDatabase.insert("dates", null, values)
  }

  def list: List[DateTime] = {
    val dates = scala.collection.mutable.ListBuffer.empty[DateTime]
    val cursor = db.getReadableDatabase.rawQuery("""
      SELECT * FROM dates
      ORDER BY created_at DESC
    """, null)

    if (cursor.moveToFirst) {
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
