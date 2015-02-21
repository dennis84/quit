package quit.android

import org.scaloid.common._

class MainActivity extends SActivity {

  onCreate {
    contentView = new SVerticalLayout {
      STextView("You are %s h smokefree.") textSize 15.dip
      SButton("Press me", toast("Bang"))
    } padding 20.dip
  }
}
