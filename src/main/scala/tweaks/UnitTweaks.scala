package quit.app.tweaks

import android.content.Context

trait UnitTweaks {

  implicit class IntToDip(val x: Int)(implicit ctx: Context) {
    def dip = (x * m.density).toInt
    private def m = ctx.getResources.getDisplayMetrics
  }
}
