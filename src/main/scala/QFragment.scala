package quit.android

import android.support.v4.app.Fragment

trait QFragment extends Fragment {
  def bus = getActivity.asInstanceOf[QActivity].bus
}
