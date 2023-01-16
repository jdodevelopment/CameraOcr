package ar.com.jdodevelopment.cameraocr.common.extension

import com.google.mlkit.vision.text.Text
import kotlin.math.atan2
import kotlin.math.hypot


fun Text.TextBlock.inclination(): Double {
    val topLeft = this.cornerPoints!![0]
    val topRight = this.cornerPoints!![1]
    val dx = topRight.x.toDouble() - topLeft.x.toDouble()
    val dy = topRight.y.toDouble() - topLeft.y.toDouble()
    return Math.toDegrees(atan2(dy, dx))
}

fun Text.TextBlock.height(): Double {
    val topLeft = this.cornerPoints!![0]
    val topRight = this.cornerPoints!![3]
    return hypot(topLeft.x.toDouble() - topRight.x.toDouble(), topLeft.y.toDouble() - topRight.y.toDouble())
}

fun Text.Line.inclination(): Double {
    val topLeft = this.cornerPoints!![0]
    val topRight = this.cornerPoints!![1]
    val dx = topRight.x.toDouble() - topLeft.x.toDouble()
    val dy = topRight.y.toDouble() - topLeft.y.toDouble()
    return Math.toDegrees(atan2(dy, dx))
}

fun Text.Line.height(): Double {
    val topLeft = this.cornerPoints!![0]
    val bottomLeft = this.cornerPoints!![3]
    return hypot(topLeft.x.toDouble() - bottomLeft.x.toDouble(), topLeft.y.toDouble() - bottomLeft.y.toDouble())
}
