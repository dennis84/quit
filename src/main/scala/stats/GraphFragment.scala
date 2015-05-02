package quit.app.stats

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import lecho.lib.hellocharts.view.LineChartView
import lecho.lib.hellocharts.model.{Line, LineChartData, PointValue, Viewport, Axis, AxisValue}
import java.util.Locale
import scala.collection.JavaConversions._
import com.squareup.otto._
import quit.app._

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
  }

  override def onResume {
    super.onResume
    env.ctrl.listAll(state)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val values = DataGenerator.week(event.state.days)
    val line = new Line(values)
      .setColor(getResources.getColor(R.color.magenta))
      .setCubic(false)
      .setStrokeWidth(4)
      .setPointRadius(4)
    val limit = new Line(List(
      new PointValue(0, event.state.limit),
      new PointValue(values.length, event.state.limit)
    )).setColor(getResources.getColor(R.color.cyan))
      .setCubic(false)
      .setHasPoints(false)
      .setStrokeWidth(4)

    val labelsY = (0 to 20) map (x => new AxisValue(x, x.toString.toCharArray))
    val labelsX = event.state.days.reverse.zipWithIndex map { case (day, i) =>
      new AxisValue(i, day.date.toString("EEE", Locale.US).toCharArray)
    }

    val axisY = new Axis(labelsY).setHasLines(true)
    val axisX = new Axis(labelsX.toList)

    val data = new LineChartData
    data.setLines(List(limit, line))
    data.setAxisYLeft(axisY)
    data.setAxisXBottom(axisX)
    chart.setLineChartData(data)

    val v = new Viewport(chart.getMaximumViewport)
    v.left = values.length - 10;
    v.right = values.length;
    chart.setViewportCalculationEnabled(false)
    chart.setCurrentViewport(v)
  }
}
