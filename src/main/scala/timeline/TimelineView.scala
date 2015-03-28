package quit.app.timeline

import android.content.Context
import android.view.LayoutInflater
import android.widget.{LinearLayout, TextView}
import android.util.AttributeSet
import android.database.DataSetObserver
import org.joda.time.DateTime
import quit.app._

class TimelineView(context: Context, attrs: AttributeSet)
  extends LinearLayout(context, attrs) {
  setOrientation(LinearLayout.VERTICAL)

  var adapter: DateAdapter = _
  var observer = new DateObserver(this)

  def setAdapter(a: DateAdapter) {
    if(null != adapter) {
      adapter.unregisterDataSetObserver(observer)
    }

    adapter = a
    adapter.registerDataSetObserver(observer)
  }

  def getAdapter = adapter
}
