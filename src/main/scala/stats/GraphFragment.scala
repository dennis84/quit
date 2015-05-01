package quit.app.stats

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{ArrayAdapter, AdapterView}
import android.widget.AdapterView.OnItemSelectedListener
import com.squareup.otto._
import quit.app._

import android.graphics.Color
import lecho.lib.hellocharts.view.LineChartView
import lecho.lib.hellocharts.gesture.{ContainerScrollType, ZoomType}
import lecho.lib.hellocharts.model.{Line, LineChartData, PointValue, Viewport, Axis, AxisValue}

import scala.collection.JavaConversions._
import java.util.Locale

class GraphFragment extends QFragment {

  var chart: LineChartView = _

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.graph, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    chart = view.find[LineChartView](R.id.graph_chart)
    env.ctrl.list(state)
  }

  override def onResume {
    super.onResume
    update(new UpdateUI)
  }

  @Subscribe
  def update(event: UpdateUI) {
    implicit val ctx = activity
    val values = DataGenerator.week(state.days)

    val line = new Line(values).setColor(getResources.getColor(R.color.blue)).setCubic(true)
    val lines = List(line)

    val data = new LineChartData
    data.setLines(lines)

    val labelsX = state.days.reverse.zipWithIndex map { case (day, i) =>
      new AxisValue(i, day.date.toString("EEE", Locale.US).toCharArray)
    }

    val axisX = new Axis(labelsX.toList)
    val axisY = new Axis().setHasLines(true)
    data.setAxisXBottom(axisX)
    data.setAxisYLeft(axisY)

    chart.setLineChartData(data)

    val v = new Viewport(chart.getMaximumViewport)
    v.left = 20;
    v.right = 30;
    chart.setViewportCalculationEnabled(false)
    chart.setCurrentViewport(v)
  }
}
