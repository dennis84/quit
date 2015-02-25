package quit.util

import com.github.nscala_time.time.Imports._
import scala.math._

object Humanize {

  def humanize(date: DateTime): String = {
    if(date > DateTime.now) return "Just now"
    val millis = (date to DateTime.now).millis
    val s = round(floor(millis / 1000))
    val m = round(floor(s / 60))
    val h = round(floor(m / 60))
    val mh = m - h * 60

    if(h == 1 && mh == 1) return s"An hour and a minute ago"
    if(h == 1 && mh > 1) return s"An hour and ${mh} minutes ago"
    if(h > 1 && mh == 1) return s"${h} hours and a minute ago"
    if(h > 1 && mh > 0) return s"${h} hours and ${mh} minutes ago"
    if(h > 1) return s"${h} hours ago"
    if(h == 1) return "An hour ago"
    if(m > 1) return s"${m} minutes ago"
    if(m == 1) return "A minute ago"
    if(s < 60) return "Just now"
    return s"${h} hours and ${m} ago"
  }
}
