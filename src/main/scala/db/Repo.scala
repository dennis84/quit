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
      ORDER BY created_at
    """, null)

    if (cursor.moveToFirst) {
      do {
        dates += new DateTime(cursor.getLong(1))
      } while(cursor.moveToNext)
    }

    dates.toList
  }
}
