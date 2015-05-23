package com.github.dennis84.quit.core

import org.scalatest._
import com.github.nscala_time.time.Imports._
import com.github.dennis84.quit.core.Humanize._

class HumanizeSpec extends FlatSpec with Matchers {

  "The Humanizer" should "pretty print" in {
    humanize(DateTime.now + 10.seconds) should be ("a few moments")
    humanize(DateTime.now - 10.seconds) should be ("a few moments")
    humanize(DateTime.now - 80.seconds) should be ("a minute")
    humanize(DateTime.now - 120.seconds) should be ("2 minutes")
    humanize(DateTime.now - 5.minutes) should be ("5 minutes")
    humanize(DateTime.now - 1.hours) should be ("an hour")
    humanize(DateTime.now - 3.hours) should be ("3 hours")
    humanize(DateTime.now - 3.hours - 15.minutes) should be ("3 hours and 15 minutes")
    humanize(DateTime.now - 3.hours - 1.minutes) should be ("3 hours and a minute")
    humanize(DateTime.now - 1.hours - 30.minutes) should be ("an hour and 30 minutes")
    humanize(DateTime.now - 1.hours - 1.minutes) should be ("an hour and a minute")
  }
}
