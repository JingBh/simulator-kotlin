package io.github.jingbh.simulator

import io.github.jingbh.simulator.shape.Shape
import io.github.jingbh.simulator.util.linalg
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.operations.*
import kotlin.math.PI
import kotlin.math.pow

class Entity(
    val shape: Shape,
    val mass: Double,
    val charge: Double,
    val place: NDArray<Double, D2>,
    var velocity: NDArray<Double, D2>,
    var acceleration: NDArray<Double, D2>,
    val fixed: Boolean = false
) {
    private val forces = arrayListOf<NDArray<Double, D2>>()

    val gravity = mk.ndarray(mk[mk[0.0], mk[-mass * G]])

    fun enforce(force: NDArray<Double, D2>) {
        forces.add(force)
    }

    fun enforceGravity() {
        forces.add(gravity)
    }

    fun calculateAcceleration() {
        if (!fixed) {
            val resultantForce = mk.zeros<Double>(2, 1)
            forces.forEach { resultantForce += it }

            acceleration = resultantForce / mass

            forces.clear()
        }
    }

    fun collapse(entity: Entity): NDArray<Double, D2> {
        val rVector = entity.place - place
        val r = linalg.norm(rVector)
        val mechanical = shape.collapse(place, entity, r)

        val electrical = if (charge != 0.0) {
            charge * entity.charge / (EPSILON_0 * 4.0 * PI * r.pow(3)) * rVector
        } else mk.zeros<Double>(2, 1)

        return mechanical + electrical
    }

    fun draw(ratio: Double): NDArray<Double, D2> {
        // t.goto(place)
        shape.draw(ratio)
        return place
    }

    fun snapshot(): Entity {
        return Entity(
            shape,
            mass,
            charge,
            place,
            velocity,
            acceleration,
            fixed
        )
    }
}
