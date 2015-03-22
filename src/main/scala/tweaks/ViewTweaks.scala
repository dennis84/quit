package quit.app.tweaks

import android.view.View

trait ViewTweaks {

  implicit class Methods(view: View) {
    def find[T](id: Int) = view.findViewById(id).asInstanceOf[T]

    def onClick(f: => Unit) = view.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) = f
    })
  }
}
