package quit.ui.progress

import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager

class ProgressPagerAdapter(manager: FragmentManager)
  extends FragmentPagerAdapter(manager) {

  override def getCount = 2

  override def getItem(position: Int) = position match {
    case 0 => new GoalPageFragment
    case 1 => new PiecesPageFragment
  }
}
