package quit.ui.progress

import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager

class PagerAdapter(manager: FragmentManager)
  extends FragmentPagerAdapter(manager) {

  override def getCount = 2

  override def getItem(position: Int) = position match {
    case 0 => new FirstFragment
    case 1 => new SecondFragment
  }
}
