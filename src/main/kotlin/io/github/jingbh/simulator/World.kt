package io.github.jingbh.simulator

import org.jetbrains.kotlinx.multik.ndarray.operations.plusAssign
import org.jetbrains.kotlinx.multik.ndarray.operations.times
import org.jetbrains.kotlinx.multik.ndarray.operations.unaryMinus

class World(
    val entities: List<Entity>,
    val display: Boolean = true,
    val totalTime: Double = Double.MAX_VALUE,
    val tick: Double = 0.01
) {
    fun step() {
        // 运动学循环
        entities.forEach { entity ->
            entity.velocity += entity.acceleration * tick
            entity.place += entity.velocity * tick
        }

        // 碰撞循环
        for (i in entities.indices) {
            val entity1 = entities[i]

            for (j in i + 1 until entities.size) {
                val entity2 = entities[j]

                val force = entity1.collapse(entity2)
                entity2.enforce(force)
                entity1.enforce(-force)
            }

            entity1.enforceGravity()
        }

        // 力学循环
        entities.forEach { it.calculateAcceleration() }
    }

    fun loop(recordRate: Double = 0.1): List<World> {
        var time = 0.0
        val isForever = totalTime == Double.MAX_VALUE

        val record = arrayListOf<World>()
        var recordN = 0

        while (isForever || time < totalTime) {
            step()

            if (display) {
                if (time >= recordRate * recordN) {
                    record.add(snapshot())
                    recordN++
                }
            }

            time += tick
        }

        return record
    }

    fun snapshot(): World {
        return World(
            entities.map { it.snapshot() },
            display,
            tick,
            totalTime
        )
    }
}
