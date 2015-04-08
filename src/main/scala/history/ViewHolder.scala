package quit.app.history

import android.widget.TextView
import quit.app.timeline.TimelineView

case class ViewHolder(
  val pieces: TextView,
  val name: TextView,
  val break: TextView,
  val timeline: TimelineView)
