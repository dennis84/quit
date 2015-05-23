package com.github.dennis84.quit.ui.progress

import android.app.Fragment
import android.os.{Bundle, Handler}
import android.view.{LayoutInflater, ViewGroup, View}
import android.view.animation.DecelerateInterpolator
import android.widget.{ProgressBar, RadioGroup}
import android.support.v4.view.ViewPager
import android.animation.ObjectAnimator
import com.squareup.otto._
import com.github.dennis84.quit.tweaks.QFragment
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.core._
import com.github.dennis84.quit.R

class ProgressFragment extends QFragment {

  var progr: ProgressBar = _
  var progrStart = 0
  var handler: Handler = new Handler

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.progress, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    val radio = view.find[RadioGroup](R.id.progress_indicator)
    progr = view.find[ProgressBar](R.id.progress_bar)
    val pager = view.find[ViewPager](R.id.pager)
    val adapter = new ProgressPagerAdapter(activity.getSupportFragmentManager)

    pager.setAdapter(adapter)

    pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener {
      override def onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
      override def onPageScrollStateChanged(state: Int) {}

      override def onPageSelected(position: Int) {
        if(position == 0) radio.check(R.id.progress_indicator_1)
        if(position == 1) radio.check(R.id.progress_indicator_2)
      }
    })

    radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener {
      override def onCheckedChanged(group: RadioGroup, checkedId: Int) {
        if(checkedId == R.id.progress_indicator_1) pager.setCurrentItem(0)
        if(checkedId == R.id.progress_indicator_2) pager.setCurrentItem(1)
      }
    })
  }

  override def onResume {
    super.onResume
    progrStart = 0
    progr.setProgress(progrStart)
    update(new UpdateUI)
    handler.postDelayed(new Runnable {
      override def run() {
        bus.post(new UpdateUI)
        handler.postDelayed(this, 60000)
      }
    }, 60000)
  }

  override def onPause {
    super.onPause
    handler.removeCallbacksAndMessages(null)
  }

  @Subscribe
  def update(event: UpdateUI) = for {
    date <- state.dates.headOption
    goalDate <- state.goalDate
    if(viewCreated)
    x = (DateTime.now.getMillis - date.getMillis)
    y = (goalDate.getMillis - date.getMillis)
    p = (x.toDouble / y.toDouble * 1000).toInt
  } yield {
    val anim = ObjectAnimator.ofInt(progr, "progress", progrStart, p)
    progrStart = p
    anim.setInterpolator(new DecelerateInterpolator)
    anim.setDuration(1000)
    anim.setStartDelay(800)
    anim.start
  }
}
