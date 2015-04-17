package quit.app.progress

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import android.text.Html
import quit.app._

class PiecesPageFragment extends QFragment {

  var pieces: TextView = _
  var limit: TextView = _

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.second, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    pieces = view.find[TextView](R.id.progress_pieces)
    limit = view.find[TextView](R.id.progress_limit)
  }

  override def onResume {
    super.onResume
    update(new UpdateUI)
  }

  @Subscribe
  def update(event: UpdateUI) {
    if(!viewCreated) return
    val today = state.days.head
    val html = s"${today.dates.length} pieces today" replaceAll ("""(\d+)""", "<b>$1</b>")
    pieces.setText(Html.fromHtml(html))
    limit.setText(s"Only ${state.limit - today.dates.length} pieces left")
  }
}
