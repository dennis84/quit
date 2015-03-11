package quit.ui.progress

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.TextView
import com.squareup.otto._
import com.github.nscala_time.time.Imports._
import android.text.Html
import quit.ui._

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
    update(state)
  }

  @Subscribe
  def update(newState: State) {
    if(!viewCreated) return
    val dates = newState.dates.filter(_ > DateTime.now.withTimeAtStartOfDay)
    val html = s"${dates.length} pieces today" replaceAll ("""(\d+)""", "<b>$1</b>")
    pieces.setText(Html.fromHtml(html))
    limit.setText(s"Only ${newState.limit - dates.length} pieces left")
  }
}