package com.github.dennis84.quit.ui.history

import android.os.Bundle
import android.view.{LayoutInflater, ViewGroup, View}
import android.widget.{ListView, TextView}
import com.squareup.otto._
import java.util.ArrayList
import com.github.dennis84.quit.core._
import com.github.dennis84.quit.tweaks.FullDsl._
import com.github.dennis84.quit.ui.QListFragment
import com.github.dennis84.quit.ui.timeline._
import com.github.dennis84.quit.R

class HistoryFragment extends QListFragment[DayAdapter] {

  var header: View  = _
  var timeline: TimelineView = _
  var toggleTimeline: TextView = _

  override def onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup,
    savedInstanceState: Bundle
  ) = {
    header = inflater.inflate(R.layout.today, container, false)
    timeline = header.find[TimelineView](R.id.timeline)
    toggleTimeline = header.find[TextView](R.id.toggle_timeline)
    super.onCreateView(inflater, container, savedInstanceState)
  }

  override def onViewCreated(view: View, savedInstanceState: Bundle) {
    super.onViewCreated(view, savedInstanceState)
    getListView addHeaderView header
    setListAdapter(new DayAdapter(activity, new ArrayList[Day]))
    env.ctrl.list(env.state)

    toggleTimeline onClick {
      if(View.VISIBLE == timeline.getVisibility) {
        timeline.setVisibility(View.GONE)
        toggleTimeline.setText("Show timeline")
      } else {
        timeline.setVisibility(View.VISIBLE)
        toggleTimeline.setText("Hide timeline")
      }
    }

    getListView.setOnScrollListener(new EndlessScrollListener {
      def onLoadMore(page: Int, totalItemsCount: Int) {
        env.ctrl.list(env.state, page)
      }
    })
  }

  override def onListItemClick(l: ListView, v: View, position: Int, id: Long) {
    super.onListItemClick(l, v, position, id)
    val day = getListAdapter.getItem(position - 1).asInstanceOf[Day]
    day.selected = !day.selected
    adapter.notifyDataSetChanged
  }

  @Subscribe
  def onChangeState(event: ChangeState) {
    event.state.days foreach { day =>
      if(day.isToday) {
        timeline.setAdapter(DateAdapter(activity, day.dates))
        timeline.getAdapter.notifyDataSetChanged
      } else {
        if(adapter.getPosition(day) < 0) adapter.add(day)
      }
    }
  }
}
