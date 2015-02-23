package quit.android

import org.scalatest._
import com.github.nscala_time.time.Imports._

class HumanizeSpec extends FlatSpec with Matchers {

  "The Humanizer" should "work" in {
    Humanize.humanize(DateTime.now - 10.seconds) should be ("Just now")
    Humanize.humanize(DateTime.now - 80.seconds) should be ("A minute ago")
    Humanize.humanize(DateTime.now - 120.seconds) should be ("2 minutes ago")
    Humanize.humanize(DateTime.now - 5.minutes) should be ("5 minutes ago")
    Humanize.humanize(DateTime.now - 1.hours) should be ("An hour ago")
    Humanize.humanize(DateTime.now - 3.hours) should be ("3 hours ago")
    Humanize.humanize(DateTime.now - 3.hours - 15.minutes) should be ("3 hours and 15 minutes ago")
    Humanize.humanize(DateTime.now - 3.hours - 1.minutes) should be ("3 hours and a minute ago")
    Humanize.humanize(DateTime.now - 1.hours - 30.minutes) should be ("An hour and 30 minutes ago")
    Humanize.humanize(DateTime.now - 1.hours - 1.minutes) should be ("An hour and a minute ago")
  }
}
