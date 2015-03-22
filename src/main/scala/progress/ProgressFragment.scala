package quit.app.progress

import android.app.Fragment
import android.os.{Bundle, Handler}
import android.view.{LayoutInflater, ViewGroup, View}
import android.view.animation.DecelerateInterpolator
import android.widget.{Button, ProgressBar, RadioGroup}
import android.support.v4.view.ViewPager
import android.animation.ObjectAnimator
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import com.melnykov.fab.FloatingActionButton
import quit.app._

class ProgressFragment extends QFragment {

  var progr: ProgressBar = _
  var progrStart = 1
  var handler: Handler = new Handler

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.progress, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    val btn = view.find[FloatingActionButton](R.id.btn)
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

    btn onClick env.ctrl.add(state, activity)
  }

  override def onResume {
    super.onResume
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
    anim.setDuration(500)
    anim.setInterpolator(new DecelerateInterpolator)
    anim.start
  }
}
