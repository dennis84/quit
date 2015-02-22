package quit.android

import com.github.nscala_time.time.Imports._
import org.ocpsoft.prettytime.PrettyTime

object Date {

  def humanize(date: DateTime): String = {
    val p = new PrettyTime
    val durations = p.calculatePreciseDuration(date.toDate)
    p.format(durations)
  }
}
