package quit.app.util

import scala.math._
import quit.app._

object Humanize {

  def humanize(date: DateTime): String = {
    if(date > DateTime.now) return "a few moments"
    humanize(date to DateTime.now)
  }

  def humanize(interval: Interval): String = {
    val millis = interval.millis
    val s = round(floor(millis / 1000))
    val m = round(floor(s / 60))
    val h = round(floor(m / 60))
    val mh = m - h * 60

    if(h == 1 && mh == 1) return s"an hour and a minute"
    if(h == 1 && mh > 1) return s"an hour and ${mh} minutes"
    if(h > 1 && mh == 1) return s"${h} hours and a minute"
    if(h > 1 && mh > 0) return s"${h} hours and ${mh} minutes"
    if(h > 1) return s"${h} hours"
    if(h == 1) return "an hour"
    if(m > 1) return s"${m} minutes"
    if(m == 1) return "a minute"
    if(s < 60) return "a few moments"
    return s"${h} hours and ${m} minutes"
  }
}
