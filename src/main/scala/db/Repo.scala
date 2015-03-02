package quit.db

import android.content.ContentValues
import org.joda.time.DateTime

class Repo(db: Db) {

  def insert(date: DateTime) {
    val values = new ContentValues;
    values.put("created_at", date.toString)
    db.getWritableDatabase.insert("dates", null, values)
  }

  def list: List[DateTime] = {
    val dates = scala.collection.mutable.ListBuffer.empty[DateTime]
    val cursor = db.getReadableDatabase.rawQuery("""
      SELECT * FROM dates
      ORDER BY date(created_at) DESC
    """, null)

    if (cursor.moveToFirst) {
      do {
        dates += DateTime.parse(cursor.getString(1))
      } while(cursor.moveToNext)
    }

    dates.toList
  }
}
