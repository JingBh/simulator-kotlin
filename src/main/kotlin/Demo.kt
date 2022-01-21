import io.github.jingbh.simulator.Entity
import io.github.jingbh.simulator.World
import io.github.jingbh.simulator.shape.Circle
import io.github.jingbh.simulator.shape.InnerCircle
import io.github.jingbh.simulator.util.linalg
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.api.zeros

object Demo {
    init {
        linalg // init engine
    }

    private val box = Entity(
        InnerCircle(20.0),
        1.0,
        0.0,
        mk.zeros(2, 1),
        mk.zeros(2, 1),
        mk.zeros(2, 1),
        true
    )

    private val circle1 = Entity(
        Circle(1.0),
        2.0,
        0.0001,
        mk.ndarray(mk[mk[0.0], mk[0.0]]),
        mk.ndarray(mk[mk[3.0], mk[4.0]]),
        mk.zeros(2, 1)
    )

    private val circle2 = Entity(
        Circle(1.0),
        2.0,
        -0.0001,
        mk.ndarray(mk[mk[3.0], mk[5.0]]),
        mk.ndarray(mk[mk[5.0], mk[-4.0]]),
        mk.zeros(2, 1)
    )

    private val circle3 = Entity(
        Circle(1.0),
        2.0,
        -0.0001,
        mk.ndarray(mk[mk[-3.0], mk[-4.0]]),
        mk.ndarray(mk[mk[6.0], mk[0.0]]),
        mk.zeros(2, 1)
    )

    private val circle4 = Entity(
        Circle(1.0),
        2.0,
        0.0,
        mk.ndarray(mk[mk[-10.0], mk[10.0]]),
        mk.ndarray(mk[mk[0.0], mk[0.0]]),
        mk.zeros(2, 1)
    )

    private val world = World(
        listOf(box, circle1, circle2, circle3, circle4),
        true,
        10.0
    )

    fun doTiming(): Double {
        val startTime = System.currentTimeMillis() / 1000.0
        val result = world.loop()
        val endTime = System.currentTimeMillis() / 1000.0

        return endTime - startTime
    }
}

fun main(args: Array<String>) {
    val timeUsage = Demo.doTiming()
    println("Time: ${timeUsage}s")
}
