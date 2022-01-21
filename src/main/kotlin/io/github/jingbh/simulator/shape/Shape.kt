package io.github.jingbh.simulator.shape

import io.github.jingbh.simulator.Entity
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray

abstract class Shape {
    abstract fun collapse(
        place: NDArray<Double, D2>,
        entity: Entity,
        r: Double
    ): NDArray<Double, D2>

    abstract fun getShape(): String
}
