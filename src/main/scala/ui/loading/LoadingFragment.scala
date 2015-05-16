package quit.app.loading

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import com.squareup.otto._
import quit.tweaks.QFragment
import quit.tweaks.FullDsl._
import quit.core._
import quit.app._

class LoadingFragment extends QFragment {

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.loading, container, false)

  override def onResume {
    super.onResume
    update(new UpdateUI)
  }

  @Subscribe
  def update(event: UpdateUI) {
    if(viewCreated && state.connected) {
      getView.setVisibility(View.GONE)
    }
  }
}
