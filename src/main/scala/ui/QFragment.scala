package quit.ui

import android.support.v4.app.Fragment

trait QFragment extends Fragment {
  def activity = getActivity.asInstanceOf[QActivity]
  def bus = activity.bus
}
