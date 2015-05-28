package com.github.dennis84.quit.ui.stats

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.graphics.DashPathEffect
import lecho.lib.hellocharts.view.LineChartView
import lecho.lib.hellocharts.model.{Line, LineChartData, PointValue, Viewport, Axis, AxisValue}
import lecho.lib.hellocharts.gesture.ZoomType
import java.util.Locale
import scala.collection.JavaConversions._
import com.squareup.otto._
import com.github.dennis84.quit.ui.QFragment
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.core._
import com.github.dennis84.quit.R

class ChartFragment extends QFragment {

  var chart: LineChartView = _

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = inflater.inflate(R.layout.chart, container, false)

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    chart = view.find[LineChartView](R.id.chart_chart)
    chart.setZoomType(ZoomType.HORIZONTAL)
    chart.setValueSelectionEnabled(true)
  }

  override def onResume {
    super.onResume
    env.ctrl.stats(state)
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    val days = event.state.days.reverse
    val values = DataGenerator.week(event.state.days)
    val line = new Line(values)
      .setColor(getResources.getColor(R.color.magenta))
      .setHasLabelsOnlyForSelected(true)
      .setCubic(false)
      .setStrokeWidth(4)
      .setPointRadius(4)

    val limit = new Line((for {
      h <- days.headOption
      l <- days.lastOption
      from = h.date.dayIndex
      to   = l.date.dayIndex
    } yield {
      val configs = event.state.configs.groupBy {
        x => x.createdAt.withTimeAtStartOfDay
      }.map(_._2.head).toList.sortBy {
        x => x.createdAt.withTimeAtStartOfDay.getMillis
      }

      val points = ((None +: configs.map(Some(_))) sliding 2).map {
        case List(None, Some(x)) => List(
          new PointValue(x.createdAt.dayIndex, x.limit).setLabel(""))
        case List(Some(y), Some(x)) => List(
          new PointValue(x.createdAt.dayIndex, y.limit).setLabel(""),
          new PointValue(x.createdAt.dayIndex, x.limit).setLabel(""))
        case _ => Nil
      }.flatten

      val fl = configs.headOption.map(_.limit).getOrElse(event.state.limit)
      List(new PointValue(from, fl).setLabel("")) ++ points ++ List(
        new PointValue(to, event.state.limit).setLabel(s"Limit: ${event.state.limit}"))
    }).getOrElse(List(
      new PointValue(0, event.state.limit)
    ))).setColor(getResources.getColor(R.color.cyan))
      .setCubic(false)
      .setPointRadius(2)
      .setStrokeWidth(4)
      .setHasLabels(true)
    limit.setPathEffect(new DashPathEffect(Array(1, 20, 1, 20), 0))

    val index = DateTime.now.dayIndex + 1
    val maxY = days.maxBy(_.dates.length)
    val fixLine = new Line(List(
      new PointValue(index, maxY.dates.length + 1),
      new PointValue(index, 0)
    )).setHasLines(false)
      .setHasPoints(false)

    val labelsY = (0 to 20) map (x => new AxisValue(x).setLabel(x.toString))
    val labelsX = days map { day =>
      new AxisValue(day.date.dayIndex).setLabel(day.date.toString("EEE", Locale.US))
    }

    val axisY = new Axis(labelsY)
      .setHasLines(true)
      .setHasSeparationLine(false)
    val axisX = new Axis(labelsX.toList)
      .setHasSeparationLine(false)

    val data = new LineChartData
    data.setLines(List(limit, line, fixLine))
    data.setAxisYLeft(axisY)
    data.setAxisXBottom(axisX)
    chart.setLineChartData(data)

    days.lastOption foreach { day =>
      val l = day.date.dayIndex
      val v = new Viewport(chart.getMaximumViewport)
      v.left = l - 9
      v.right = l + 1
      chart.setViewportCalculationEnabled(false)
      chart.setCurrentViewport(v)
    }
  }
}
