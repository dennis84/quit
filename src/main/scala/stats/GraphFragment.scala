package quit.app.stats

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import com.squareup.otto._
import quit.app._

import android.graphics.Color
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data._

import scala.collection.JavaConversions._

class GraphFragment extends QFragment {

  var chart: LineChart = _

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.graph, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    chart = view.find[LineChart](R.id.graph_chart)
    chart.setDrawGridBackground(false)
    chart.getXAxis.setDrawAxisLine(true)
    chart.getXAxis.setDrawGridLines(false)
    env.ctrl.list(state)
  }

  override def onResume {
    super.onResume
    update(new UpdateUI)
  }

  @Subscribe
  def update(event: UpdateUI) {
    val data = DataGenerator.weekly(state.days)
    debug(data)
    val set = new LineDataSet(data.ys, "DataSet 1")
    set.setLineWidth(2)
    set.setCircleSize(10)
    set.setColor(getResources.getColor(R.color.base00)) // Linecolor
    set.setCircleColor(getResources.getColor(R.color.base00))
    set.setCircleColorHole(getResources.getColor(R.color.base00))
    //set.setHighLightColor(Color.WHITE)
    set.setDrawValues(false)

    chart.setData(new LineData(data.xs, List(set)))

//     val xs = List("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
//     val ys = List(
//       new Entry(11f, 0),
//       new Entry(9f, 1),
//       new Entry(6f, 2),
//       new Entry(8f, 3),
//       new Entry(9f, 4),
//       new Entry(6f, 5))

//     val set = new LineDataSet(ys, "DataSet 1")
//     set.setLineWidth(1.75f)
//     set.setCircleSize(3f)
//     set.setColor(Color.WHITE)
//     set.setCircleColor(Color.WHITE)
//     set.setHighLightColor(Color.WHITE)
//     set.setDrawValues(false)

//     val sets = List(set)
//     val data = new LineData(xs, sets)
  }
}
