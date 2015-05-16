package quit.app.button

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import com.melnykov.fab.FloatingActionButton
import quit.tweaks.QFragment
import quit.tweaks.FullDsl._
import quit.app._

class ButtonFragment extends QFragment {

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.button, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    val btn = view.find[FloatingActionButton](R.id.button_add)
    btn onClick env.ctrl.add(state, activity)
  }
}
