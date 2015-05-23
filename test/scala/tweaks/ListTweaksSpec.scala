package com.github.dennis84.quit.ui.tweaks

import org.scalatest._

class ListTweaksSpec extends FlatSpec with Matchers with ListTweaks {

  "groupWhile" should "works" in {
    List(1,2,4,5).groupWhile {
      (left, right) => right - left <= 1
    } should be (List(List(1,2), List(4,5)))

    List.empty[Int].groupWhile {
      (left, right) => right - left <= 1
    } should be (Nil)
  }
}
