package quit.ui.progress

import android.app.Fragment
import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}
import android.widget.{Button, ProgressBar, RadioGroup}
import android.support.v4.view.ViewPager
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import org.scaloid.common._
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import scala.concurrent.ExecutionContext.Implicits.global
import quit.ui._

class ProgressFragment extends QFragment {

  var progr: ProgressBar = null

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = {
    val view = inflater.inflate(R.layout.progress, container, false)
    val btn = view.findViewById(R.id.btn).asInstanceOf[Button]
    val radio = view.findViewById(R.id.progress_indicator).asInstanceOf[RadioGroup]
    progr = view.findViewById(R.id.progress_bar).asInstanceOf[ProgressBar]
    val pager = view.findViewById(R.id.pager).asInstanceOf[ViewPager]
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

    btn onClick (env.client.put onSuccess {
      case xs => runOnUiThread(bus.post(new ChangeState(state.copy(dates = xs))))
    })

    env.client.list onSuccess {
      case Nil => ()
      case xs => runOnUiThread(bus.post(new ChangeState(state.copy(dates = xs))))
    }

    view
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val date = event.state.dates.last
    val p = if(date < DateTime.now) (date to DateTime.now).millis.toDouble / event.state.goal * 1000 else 0
    val anim = ObjectAnimator.ofInt(progr, "progress", 1, p.toInt)
    anim.setDuration(500)
    anim.setInterpolator(new DecelerateInterpolator)
    anim.start
  }
}
