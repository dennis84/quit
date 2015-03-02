package quit.ui.loading

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import com.squareup.otto._
import quit.ui._

class LoadingFragment extends QFragment {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    bus.register(this)
  }

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.loading, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    onChangeState(new ChangeState(state))
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    if(viewCreated && event.state.connected) {
      getView.setVisibility(View.GONE)
    }
  }
}
