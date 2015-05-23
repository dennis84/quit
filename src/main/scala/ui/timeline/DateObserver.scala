package com.github.dennis84.quit.ui.timeline

import android.database.DataSetObserver

class DateObserver(context: TimelineView) extends DataSetObserver {

  override def onChanged {
    val views = (0 until context.getChildCount) map context.getChildAt
    context.removeAllViews

    (0 until context.adapter.getCount) foreach { i =>
      val convertView = views.lift(i) getOrElse null
      context.addView(context.adapter.getView(i, convertView, context))
    }

    super.onChanged
  }

  override def onInvalidated {
    context.removeAllViews
    super.onInvalidated
  }
}
