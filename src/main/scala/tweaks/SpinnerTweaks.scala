package quit.app.tweaks

import android.widget.{AdapterView, Spinner}
import android.widget.AdapterView.OnItemSelectedListener
import android.view.View

trait SpinnerTweaks {

  implicit class SpinnerSelected(spinner: Spinner) {
    def onSelected(f: (AdapterView[_], View, Int, Long) => Unit) =
      spinner.setOnItemSelectedListener(new OnItemSelectedListener {
        def onItemSelected(parent: AdapterView[_], view: View, pos: Int, id: Long) = {
          f(parent, view, pos, id)
        }

        def onNothingSelected(parent: AdapterView[_]) {}
      })
  }
}
