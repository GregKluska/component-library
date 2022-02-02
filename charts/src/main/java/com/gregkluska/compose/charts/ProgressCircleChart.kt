package com.gregkluska.compose.charts

import android.R.attr.radius
import android.graphics.DashPathEffect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


//
//@Composable
//fun ProgressCircleChart(
//    color: Color = Color.Black,
//    strokeSize: Float = 50F
//) {
//    Canvas(
//        modifier = Modifier.fillMaxSize().graphicsLayer { alpha = 0.99F }
//    ) {
//        val radius = PI * 2/100
//
//
//        drawArc(
//            color = color,
//            startAngle = 0F,
//            sweepAngle = 340F,
//            size = this.size.copy(this.size.width - strokeSize, this.size.height - strokeSize),
//            topLeft = Offset(strokeSize/2, strokeSize/2),
//            useCenter = false,
//            style = Stroke(
//                width = strokeSize,
//                cap = StrokeCap.Round,
//                join = StrokeJoin.Round,
//                pathEffect = PathEffect.cornerPathEffect(50F)
//            )
//        )
//
//
//    }
//}

/**
 * @param size of the canvas, based on which is calculated radius
 * @param offset a value which decreases the radius
 * @param angle of the point on the circle
 */
fun getPointOnCircle(
    size: Size,
    offset: Float,
    angle: Float,
): Offset {
    val invAngle: Float = (((125F + angle) * 2 * Math.PI) / -360F).toFloat()

    val mid = Offset(size.width / 2, size.height / 2)
    val radius = min((size.width / 2) - offset, (size.height / 2) - offset)
    val relPos = Offset(x = radius * cos(invAngle), y = radius * sin(invAngle))

    return Offset(
        x = mid.x + relPos.x,
        y = mid.y - relPos.y
    )
}

@Composable
fun ProgressCircleChart(
    brush: Brush = Brush.linearGradient(listOf(Color(0xFF7AD3FF), Color(0xFF4FBAF0))),
    strokeSize: Float = 70F,
    progress: Float = 1F
) {
    val sweepAngle = (progress * 290F)

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { alpha = 0.99F }
    ) {
        this.drawIntoCanvas {

            val paint = Paint()

            paint.style = PaintingStyle.Stroke
            paint.strokeCap = StrokeCap.Round
            paint.strokeJoin = StrokeJoin.Round
            paint.pathEffect = PathEffect.cornerPathEffect(50F)
            paint.strokeWidth = strokeSize
            paint.isAntiAlias = true
            paint.shader = LinearGradientShader(
                from = getPointOnCircle(this.size,strokeSize/2, 0F),
                to = getPointOnCircle(this.size,strokeSize/2, sweepAngle),
                colors = listOf(Color(0x1A4FBAF0), Color(0x1A7AD3FF)),
                tileMode = TileMode.Mirror
            )

            val arcSize = Size(
                this.size.width - strokeSize,
                this.size.height - strokeSize
            )

            it.drawArc(
                rect = Rect(
                    offset = Offset(strokeSize / 2, strokeSize / 2),
                    size = arcSize
                ),
                useCenter = false,
                startAngle = 125F,
                sweepAngle = 290F,
                paint = paint,
            )

            paint.shader = LinearGradientShader(
                from = getPointOnCircle(this.size,strokeSize/2, 0F),
                to = getPointOnCircle(this.size,strokeSize/2, sweepAngle),
                colors = listOf(Color(0xFF4FBAF0), Color(0xFF7AD3FF)),
                tileMode = TileMode.Mirror
            )

            it.drawArc(
                rect = Rect(
                    offset = Offset(strokeSize / 2, strokeSize / 2),
                    size = arcSize
                ),
                useCenter = false,
                startAngle = 125F,
                sweepAngle = sweepAngle,
                paint = paint,
            )

            val indicators = Size(
                this.size.width - (strokeSize * 3F),
                this.size.height - (strokeSize * 3F),
            )

            paint.strokeWidth = 8F

            paint.pathEffect = PathEffect.dashPathEffect(floatArrayOf(160F, 20F))
//            paint.pathEffect = PathEffect.dashPathEffect(floatArrayOf(2F, 36F), 3F)

            it.drawArc(
                rect = Rect(
                    offset = Offset(strokeSize * 1.5F, strokeSize * 1.5F),
                    size = indicators
                ),
                useCenter = false,
                startAngle = 125F,
                sweepAngle = 290F,
                paint = paint,
            )
        }

//        drawArc(
//            brush = brush,
//            startAngle = 125F,
//            sweepAngle = 290F,
//            size = this.size.copy(this.size.width - strokeSize, this.size.height - strokeSize),
//            topLeft = Offset(strokeSize/2, strokeSize/2),
//            useCenter = false,
//            style = Stroke(
//                width = strokeSize,
//                cap = StrokeCap.Round,
//                join = StrokeJoin.Round,
//                pathEffect = PathEffect.cornerPathEffect(50F)
//            )
//        )


    }
}

@Preview
@Composable
private fun ProgressCircleChartPreview() {
    Surface(
        modifier = Modifier.size(300.dp),
        color = Color.DarkGray
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.size(250.dp)) {
                ProgressCircleChart(
                    progress = 0.9F
                )
            }
        }
    }
}