package quit.app.stats

import com.github.mikephil.charting.utils.ValueFormatter

class IntValueFormatter extends ValueFormatter {

    override def getFormattedValue(value: Float) = value.toInt.toString
}
