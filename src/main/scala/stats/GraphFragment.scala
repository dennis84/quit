package quit.app.stats

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{ArrayAdapter, AdapterView, Spinner}
import android.widget.AdapterView.OnItemSelectedListener
import com.squareup.otto._
import quit.app._

import android.graphics.Color
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data._

import scala.collection.JavaConversions._

class GraphFragment extends QFragment {

  var spinner: Spinner = _
  var chart: LineChart = _
  var graphType = "week"

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.graph, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    spinner = view.find[Spinner](R.id.graph_spinner)
    spinner onSelected {
      case (parent, view, 0, id) => {
        graphType = "week"
        update(new UpdateUI)
      }

      case (parent, view, 1, id) => {
        graphType = "month"
        update(new UpdateUI)
      }
    }

    val items = List("Week", "Month")
    spinner.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, items))
    chart = view.find[LineChart](R.id.graph_chart)
    chart.setDrawGridBackground(false)
    chart.setDescription("")
    chart.getLegend.setEnabled(false)
    chart.getXAxis.setDrawAxisLine(true)
    chart.getXAxis.setDrawGridLines(false)
    chart.getXAxis.setTextSize(10)
    chart.getXAxis.setTextColor(getResources.getColor(R.color.base1))
    chart.getXAxis.setPosition(XAxisPosition.BOTTOM)
    chart.getXAxis.setAvoidFirstLastClipping(true)
    chart.getXAxis.setSpaceBetweenLabels(0)
    chart.getAxisRight.setEnabled(false)
    chart.getAxisLeft.setTextSize(10)
    chart.getAxisLeft.setTextColor(getResources.getColor(R.color.base1))
    chart.getAxisLeft.setValueFormatter(new IntValueFormatter)
    chart.getAxisLeft.setDrawAxisLine(false)
    env.ctrl.list(state)
  }

  override def onResume {
    super.onResume
    update(new UpdateUI)
  }

  @Subscribe
  def update(event: UpdateUI) {
    implicit val ctx = activity
    val data = if("week" == graphType) DataGenerator.week(state.days)
               else DataGenerator.month(state.days)

    val set = new LineDataSet(data.ys, "DataSet 1")
    set.setLineWidth(4)
    set.setCircleSize(5)
    set.setColor(getResources.getColor(R.color.base00))
    set.setCircleColor(getResources.getColor(R.color.base00))
    set.setCircleColorHole(getResources.getColor(R.color.base00))
    set.setDrawValues(false)
    chart.setData(new LineData(data.xs, List(set)))
  }
}
