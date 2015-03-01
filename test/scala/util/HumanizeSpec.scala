package quit.util

import org.scalatest._
import com.github.nscala_time.time.Imports._
import Humanize._

class HumanizeSpec extends FlatSpec with Matchers {

  "The Humanizer" should "pretty print date time" in {
    humanize(DateTime.now + 10.seconds) should be ("Just now")
    humanize(DateTime.now - 10.seconds) should be ("Just now")
    humanize(DateTime.now - 80.seconds) should be ("A minute ago")
    humanize(DateTime.now - 120.seconds) should be ("2 minutes ago")
    humanize(DateTime.now - 5.minutes) should be ("5 minutes ago")
    humanize(DateTime.now - 1.hours) should be ("An hour ago")
    humanize(DateTime.now - 3.hours) should be ("3 hours ago")
    humanize(DateTime.now - 3.hours - 15.minutes) should be ("3 hours and 15 minutes ago")
    humanize(DateTime.now - 3.hours - 1.minutes) should be ("3 hours and a minute ago")
    humanize(DateTime.now - 1.hours - 30.minutes) should be ("An hour and 30 minutes ago")
    humanize(DateTime.now - 1.hours - 1.minutes) should be ("An hour and a minute ago")
  }

  "The Humanizer" should "pretty print interval" in {
    humanize(DateTime.now - 1.hours - 1.minutes to DateTime.now) should be ("An hour and a minute to your goal")
    humanize(DateTime.now - 1.hours - 15.minutes to DateTime.now) should be ("An hour and 15 minutes to your goal")
    humanize(DateTime.now - 3.hours - 1.minutes to DateTime.now) should be ("3 hours and a minute to your goal")
    humanize(DateTime.now - 3.hours - 30.minutes to DateTime.now) should be ("3 hours and 30 minutes to your goal")
    humanize(DateTime.now - 3.hours to DateTime.now) should be ("3 hours to your goal")
    humanize(DateTime.now - 1.hours to DateTime.now) should be ("An hour to your goal")
    humanize(DateTime.now - 30.minutes to DateTime.now) should be ("30 minutes to your goal")
    humanize(DateTime.now - 1.minutes to DateTime.now) should be ("A minute to your goal")
    humanize(DateTime.now - 30.seconds to DateTime.now) should be ("A few moments to your goal")
  }
}
