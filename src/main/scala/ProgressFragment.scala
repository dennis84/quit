package quit.android

import android.app.Fragment
import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup}
import android.widget.ProgressBar
import android.support.v4.view.ViewPager
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import org.scaloid.common._
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import scala.concurrent.ExecutionContext.Implicits.global

class ProgressFragment extends Fragment {

  var client: Client = null
  var bus: Bus = null
  var progr: ProgressBar = null

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus = getActivity.asInstanceOf[MainActivity].bus
    bus.register(this)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = {
    val view = inflater.inflate(R.layout.progress, container, false)

    progr = view.findViewById(R.id.progress_bar).asInstanceOf[ProgressBar]
    val pager = view.findViewById(R.id.pager).asInstanceOf[ViewPager]
    val adapter = new PagerAdapter(getActivity.asInstanceOf[MainActivity].getSupportFragmentManager())
    pager.setAdapter(adapter)

    client.list onSuccess {
      case xs => runOnUiThread(bus.post(Updated(xs)))
    }

    view
  }

  @Subscribe
  def onEnv(event: Env) {
    client = event.client
  }

  @Subscribe
  def onUpdate(event: Update) {
    client.put onSuccess {
      case xs => runOnUiThread(bus.post(Updated(xs)))
    }
  }

  @Subscribe
  def onUpdated(event: Updated) {
    val date = event.dates.last
    val p = if(date < DateTime.now) (date to DateTime.now).millis.toDouble / 7200000 * 1000 else 0
    val anim = ObjectAnimator.ofInt(progr, "progress", 1, p.toInt)
    anim.setDuration(500)
    anim.setInterpolator(new DecelerateInterpolator)
    anim.start
  }
}
