package com.github.dennis84.quit.tweaks

import android.view.View

trait ViewTweaks {

  implicit class Methods(view: View) {
    def find[A](id: Int) = view.findViewById(id).asInstanceOf[A]

    def onClick(f: => Unit) = view.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) = f
    })
  }
}
