import io.github.jingbh.simulator.Entity
import io.github.jingbh.simulator.World
import io.github.jingbh.simulator.player.Player
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
        60.0
    )

    fun render(): List<World> {
        val startTime = System.currentTimeMillis() / 1000.0
        val result = world.loop(1.0 / 60.0)
        val endTime = System.currentTimeMillis() / 1000.0

        val timeUsage = endTime - startTime
        println("Time: ${timeUsage}s")

        return result
    }
}

fun main(args: Array<String>) {
    Player(Demo.render(), 1.0, 1.0 / 60.0)
}
