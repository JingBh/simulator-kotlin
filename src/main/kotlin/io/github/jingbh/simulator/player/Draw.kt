package io.github.jingbh.simulator.player

import io.github.jingbh.simulator.Entity
import io.github.jingbh.simulator.shape.Circle
import io.github.jingbh.simulator.shape.InnerCircle
import io.github.jingbh.simulator.shape.Shape
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.minus
import org.jetbrains.kotlinx.multik.ndarray.operations.times
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint

private const val RATIO = 10.0

private val center = mk.ndarray(mk[mk[400.0], mk[300.0]])

fun Entity.draw(canvas: Canvas) {
    shape.draw(canvas, place)
}

fun Shape.draw(canvas: Canvas, place: NDArray<Double, D2>) {
    when (getShape()) {
        "Circle" -> {
            val circle = this as Circle
            val position = center - place * RATIO
            val paint = Paint().apply {
                color = Color.BLUE
            }
            canvas.drawCircle(
                position[0][0].toFloat(),
                position[1][0].toFloat(),
                (circle.radius * RATIO).toFloat(),
                paint
            )
        }

        "InnerCircle" -> {
            val circle = this as InnerCircle
            val position = center - place * RATIO
            val paint = Paint().apply {
                color = Color.BLACK
            }
            canvas.drawCircle(
                position[0][0].toFloat(),
                position[1][0].toFloat(),
                (circle.radius * RATIO).toFloat(),
                paint
            )
        }
    }
}
