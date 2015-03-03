package quit.ui.progress

import android.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{Button, ProgressBar, RadioGroup}
import android.support.v4.view.ViewPager
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import org.scaloid.common._
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import scala.concurrent.ExecutionContext.Implicits.global
import com.melnykov.fab.FloatingActionButton
import quit.ui._
import quit.app._

class ProgressFragment extends QFragment {

  var progr: ProgressBar = null
  var progrStart = 1

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)
  }

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
    val adapter = new PagerAdapter(activity.getSupportFragmentManager)
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

    btn onClick runOnUiThread {
      env.repo.insert(DateTime.now)
      bus.post(state.copy(dates = env.repo.list))
    }

    runOnUiThread(bus.post(state.copy(
      connected = true,
      dates = env.repo.list
    )))

    schedule(60000, {
      runOnUiThread(bus.post(state))
    })
  }

  @Subscribe
  def update(newState: State) {
    if(!viewCreated) return
    newState.dates.lastOption foreach { date =>
      val p = if(date < DateTime.now) (date to DateTime.now).millis.toDouble / newState.goal * 1000 else 0
      val anim = ObjectAnimator.ofInt(progr, "progress", progrStart, p.toInt)
      progrStart = p.toInt
      anim.setDuration(500)
      anim.setInterpolator(new DecelerateInterpolator)
      anim.start
    }
  }
}
