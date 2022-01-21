package io.github.jingbh.simulator.shape

import io.github.jingbh.simulator.Color
import io.github.jingbh.simulator.Entity
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.operations.div
import org.jetbrains.kotlinx.multik.ndarray.operations.minus
import org.jetbrains.kotlinx.multik.ndarray.operations.times
import kotlin.math.pow

class Circle(
    val radius: Double,
    val k: Double = 10.0.pow(2),
    val color: Color = Color.BLUE
) : Shape() {
    override fun collapse(
        place: NDArray<Double, D2>,
        entity: Entity,
        r: Double
    ): NDArray<Double, D2> {
        if (entity.shape.getShape() == "Circle") {
            val otherShape = entity.shape as Circle

            if (r > radius + otherShape.radius)
                return mk.zeros(2, 1)

            val distance = entity.place - place
            val f = (radius + otherShape.radius - r) * (k + otherShape.k)

            return distance * f / r
        }

        return mk.zeros(2, 1)
    }

    override fun getShape(): String = "Circle"
}
