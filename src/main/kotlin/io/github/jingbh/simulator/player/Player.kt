package io.github.jingbh.simulator.player

import io.github.jingbh.simulator.World
import org.jetbrains.skia.*
import org.jetbrains.skiko.GenericSkikoView
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkikoView
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants
import kotlin.math.floor
import kotlin.math.round

class Player(
    private val frames: List<World>,
    private val timeScale: Double = 1.0,
    private val recordRate: Double = 0.1
) {
    private val window = JFrame("Simulator").apply {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        preferredSize = Dimension(800, 600)
        isResizable = false
    }

    private val skiaLayer = SkiaLayer()

    private val typeface = Typeface.makeDefault()
    private val font = Font(typeface)
    private val paint = Paint().apply {
        color = Color.BLACK
    }

    private var startTime = -1L

    init {
        skiaLayer.skikoView = GenericSkikoView(skiaLayer, object : SkikoView {
            override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
                if (startTime == -1L) startTime = nanoTime
                val nanoTimePassed = nanoTime - startTime
                val time = (nanoTimePassed / 1_000_000).toDouble() / 1000.0 / timeScale
                val cycleTime = frames.size * recordRate
                val timeInCycle = time % cycleTime
                val frameN = floor(timeInCycle / recordRate).toInt()

                val timeTextLine = TextLine.make("Time: ${round(timeInCycle * 100) / 100.0}s", font)
                val frameTextLine = TextLine.make("Frame: $frameN", font)
                canvas.drawTextLine(timeTextLine, 10F, 10F, paint)
                canvas.drawTextLine(frameTextLine, 10F, 28F, paint)

                frames[frameN].entities.forEach { entity ->
                    entity.draw(canvas)
                }
            }
        })
        skiaLayer.attachTo(window.contentPane)

        window.pack()
        window.isVisible = true
    }
}
