package quit.app.util

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
    return s"${h} hours and ${m} minutes ago"
  }

  def humanize(interval: Interval): String = {
    val millis = interval.millis
    val s = round(floor(millis / 1000))
    val m = round(floor(s / 60))
    val h = round(floor(m / 60))
    val mh = m - h * 60

    if(h == 1 && mh == 1) return s"An hour and a minute to your goal"
    if(h == 1 && mh > 1) return s"An hour and ${mh} minutes to your goal"
    if(h > 1 && mh == 1) return s"${h} hours and a minute to your goal"
    if(h > 1 && mh > 0) return s"${h} hours and ${mh} minutes to your goal"
    if(h > 1) return s"${h} hours to your goal"
    if(h == 1) return "An hour to your goal"
    if(m > 1) return s"${m} minutes to your goal"
    if(m == 1) return "A minute to your goal"
    if(s < 60) return "A few moments to your goal"
    return s"${h} hours and ${m} minutes to your goal"
  }
}
