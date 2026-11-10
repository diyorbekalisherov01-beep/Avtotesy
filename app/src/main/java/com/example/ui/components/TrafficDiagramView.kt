package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DiagramType

@Composable
fun TrafficDiagramView(
    diagramType: String,
    modifier: Modifier = Modifier
) {
    val type = try {
        DiagramType.valueOf(diagramType)
    } catch (e: Exception) {
        DiagramType.NONE
    }

    if (type == DiagramType.NONE) return

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1E293B)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h / 2f

            when (type) {
                DiagramType.CROSSROAD_EQUAL, DiagramType.CROSSROAD_PRIORITY -> {
                    drawCrossroad(w, h, cx, cy, isPriority = (type == DiagramType.CROSSROAD_PRIORITY))
                }
                DiagramType.CROSSROAD_TRAFFIC_LIGHT -> {
                    drawTrafficLightCrossroad(w, h, cx, cy)
                }
                DiagramType.ROUNDABOUT -> {
                    drawRoundabout(w, h, cx, cy)
                }
                DiagramType.PEDESTRIAN_CROSSING -> {
                    drawPedestrianCrossing(w, h, cx, cy)
                }
                DiagramType.OVERTAKING_MANEUVER -> {
                    drawOvertaking(w, h, cx, cy)
                }
                DiagramType.PARKING_STOPPING -> {
                    drawParkingDiagram(w, h, cx, cy)
                }
                DiagramType.POLICE_REGULATOR -> {
                    drawRegulatorDiagram(w, h, cx, cy)
                }
                DiagramType.RAILWAY_CROSSING -> {
                    drawRailwayCrossing(w, h, cx, cy)
                }
                else -> {}
            }
        }

        // Overlay diagram title tag
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .background(Color(0x990F172A), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            val label = when (type) {
                DiagramType.CROSSROAD_EQUAL -> "Vaziyat: Teng huquqli chorraha"
                DiagramType.CROSSROAD_PRIORITY -> "Vaziyat: Asosiy yo'l (2.1 belgisi)"
                DiagramType.CROSSROAD_TRAFFIC_LIGHT -> "Vaziyat: Svetofor ishorasi"
                DiagramType.ROUNDABOUT -> "Vaziyat: Aylanma harakat (4.3)"
                DiagramType.PEDESTRIAN_CROSSING -> "Vaziyat: Piyodalar o'tish joyi"
                DiagramType.OVERTAKING_MANEUVER -> "Vaziyat: Quvib o'tish hududi"
                DiagramType.PARKING_STOPPING -> "Vaziyat: 5 metr masofa to'xtash"
                DiagramType.POLICE_REGULATOR -> "Vaziyat: Tartibga soluvchi ishorasi"
                DiagramType.RAILWAY_CROSSING -> "Vaziyat: Temir yo'l kesishmasi"
                else -> "Yo'l harakati vaziyati"
            }
            Text(
                text = label,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun DrawScope.drawCrossroad(w: Float, h: Float, cx: Float, cy: Float, isPriority: Boolean) {
    val roadWidth = 90f

    // Draw asphalt roads
    val roadColor = Color(0xFF334155)
    drawRect(roadColor, Offset(0f, cy - roadWidth / 2), Size(w, roadWidth))
    drawRect(roadColor, Offset(cx - roadWidth / 2, 0f), Size(roadWidth, h))

    // Draw dashed center lines
    val dashColor = Color(0xFFE2E8F0)
    drawLine(dashColor, Offset(0f, cy), Offset(cx - roadWidth / 2, cy), strokeWidth = 3f)
    drawLine(dashColor, Offset(cx + roadWidth / 2, cy), Offset(w, cy), strokeWidth = 3f)
    drawLine(dashColor, Offset(cx, 0f), Offset(cx, cy - roadWidth / 2), strokeWidth = 3f)
    drawLine(dashColor, Offset(cx, cy + roadWidth / 2), Offset(cx, h), strokeWidth = 3f)

    // Draw priority signs if applicable
    if (isPriority) {
        // Yellow Diamond 2.1 sign on vertical main road
        val diamondPath = Path().apply {
            moveTo(cx + 60f, cy + 40f)
            lineTo(cx + 72f, cy + 52f)
            lineTo(cx + 60f, cy + 64f)
            lineTo(cx + 48f, cy + 52f)
            close()
        }
        drawPath(diamondPath, Color(0xFFFACC15))
        drawPath(diamondPath, Color.White, style = Stroke(width = 2f))
    }

    // Vehicle 1 (Blue) moving north
    drawRoundRect(
        Color(0xFF38BDF8),
        Offset(cx + 10f, cy + 50f),
        Size(24f, 40f),
        CornerRadius(6f, 6f)
    )
    // Trajectory arrow for Vehicle 1
    drawLine(Color(0xFF38BDF8), Offset(cx + 22f, cy + 45f), Offset(cx + 22f, cy - 35f), strokeWidth = 4f, cap = StrokeCap.Round)

    // Vehicle 2 (Red) moving west
    drawRoundRect(
        Color(0xFFF43F5E),
        Offset(cx + 60f, cy - 35f),
        Size(40f, 24f),
        CornerRadius(6f, 6f)
    )
    // Trajectory arrow for Vehicle 2
    drawLine(Color(0xFFF43F5E), Offset(cx + 55f, cy - 23f), Offset(cx - 30f, cy - 23f), strokeWidth = 4f, cap = StrokeCap.Round)
}

private fun DrawScope.drawTrafficLightCrossroad(w: Float, h: Float, cx: Float, cy: Float) {
    val roadWidth = 90f
    val roadColor = Color(0xFF334155)
    drawRect(roadColor, Offset(0f, cy - roadWidth / 2), Size(w, roadWidth))
    drawRect(roadColor, Offset(cx - roadWidth / 2, 0f), Size(roadWidth, h))

    // Traffic light housing
    val tlX = cx + 55f
    val tlY = cy - 65f
    drawRoundRect(Color(0xFF0F172A), Offset(tlX, tlY), Size(18f, 48f), CornerRadius(4f, 4f))

    // Red, Yellow, Green lamps (Green illuminated)
    drawCircle(Color(0x55EF4444), radius = 5f, center = Offset(tlX + 9f, tlY + 10f))
    drawCircle(Color(0x55F59E0B), radius = 5f, center = Offset(tlX + 9f, tlY + 24f))
    drawCircle(Color(0xFF22C55E), radius = 6f, center = Offset(tlX + 9f, tlY + 38f))

    // Vehicle approaching on Green
    drawRoundRect(
        Color(0xFF10B981),
        Offset(cx - 35f, cy + 45f),
        Size(24f, 40f),
        CornerRadius(6f, 6f)
    )
    drawLine(Color(0xFF10B981), Offset(cx - 23f, cy + 40f), Offset(cx - 23f, cy - 30f), strokeWidth = 4f, cap = StrokeCap.Round)
}

private fun DrawScope.drawRoundabout(w: Float, h: Float, cx: Float, cy: Float) {
    val roadColor = Color(0xFF334155)
    drawCircle(roadColor, radius = 70f, center = Offset(cx, cy))
    // Island
    drawCircle(Color(0xFF0F766E), radius = 35f, center = Offset(cx, cy))

    // Circular lane dashes
    drawCircle(Color(0xFFE2E8F0), radius = 52f, center = Offset(cx, cy), style = Stroke(width = 2f))

    // Incoming car
    drawRoundRect(Color(0xFF38BDF8), Offset(cx - 12f, cy + 50f), Size(24f, 36f), CornerRadius(6f, 6f))

    // Circling car with right of way
    drawRoundRect(Color(0xFFF59E0B), Offset(cx + 36f, cy - 15f), Size(36f, 22f), CornerRadius(6f, 6f))
}

private fun DrawScope.drawPedestrianCrossing(w: Float, h: Float, cx: Float, cy: Float) {
    val roadColor = Color(0xFF334155)
    drawRect(roadColor, Offset(0f, cy - 45f), Size(w, 90f))

    // Zebra stripes (1.14.1)
    val zebraWidth = 12f
    val zebraGap = 10f
    val startX = cx - 50f
    for (i in 0..4) {
        val x = startX + i * (zebraWidth + zebraGap)
        drawRect(Color.White, Offset(x, cy - 40f), Size(zebraWidth, 80f))
    }

    // Pedestrian icon / circle + torso
    drawCircle(Color(0xFFFDE047), radius = 7f, center = Offset(cx - 15f, cy - 10f))
    drawRoundRect(Color(0xFFFDE047), Offset(cx - 20f, cy - 2f), Size(10f, 20f), CornerRadius(3f, 3f))

    // Waiting car stopped before stopline
    drawRoundRect(Color(0xFF38BDF8), Offset(cx - 120f, cy + 5f), Size(40f, 24f), CornerRadius(6f, 6f))
    drawLine(Color.White, Offset(cx - 70f, cy), Offset(cx - 70f, cy + 45f), strokeWidth = 5f)
}

private fun DrawScope.drawOvertaking(w: Float, h: Float, cx: Float, cy: Float) {
    val roadColor = Color(0xFF334155)
    drawRect(roadColor, Offset(0f, cy - 40f), Size(w, 80f))

    // Solid line on left, dashed on right
    drawLine(Color(0xFFEF4444), Offset(0f, cy), Offset(w, cy), strokeWidth = 3f)

    // Slow truck ahead
    drawRoundRect(Color(0xFF64748B), Offset(cx + 30f, cy + 5f), Size(50f, 26f), CornerRadius(4f, 4f))

    // Overtaking car attempting illegal pass over solid line
    drawRoundRect(Color(0xFFF43F5E), Offset(cx - 40f, cy - 32f), Size(36f, 22f), CornerRadius(6f, 6f))

    // Forbidden cross mark
    drawLine(Color(0xFFEF4444), Offset(cx - 10f, cy - 10f), Offset(cx + 10f, cy + 10f), strokeWidth = 4f)
    drawLine(Color(0xFFEF4444), Offset(cx + 10f, cy - 10f), Offset(cx - 10f, cy + 10f), strokeWidth = 4f)
}

private fun DrawScope.drawParkingDiagram(w: Float, h: Float, cx: Float, cy: Float) {
    val roadColor = Color(0xFF334155)
    drawRect(roadColor, Offset(0f, cy - 30f), Size(w, 70f))

    // Sidewalk curb
    drawRect(Color(0xFF475569), Offset(0f, cy + 40f), Size(w, 20f))

    // Zebra crosswalk
    for (i in 0..3) {
        drawRect(Color.White, Offset(cx - 100f + i * 18f, cy - 25f), Size(10f, 60f))
    }

    // Car parked near crosswalk
    drawRoundRect(Color(0xFF38BDF8), Offset(cx + 30f, cy + 10f), Size(42f, 22f), CornerRadius(5f, 5f))

    // 5-meter dimension line
    drawLine(Color(0xFFFACC15), Offset(cx - 30f, cy + 30f), Offset(cx + 25f, cy + 30f), strokeWidth = 2f)
    drawLine(Color(0xFFFACC15), Offset(cx - 30f, cy + 24f), Offset(cx - 30f, cy + 36f), strokeWidth = 2f)
    drawLine(Color(0xFFFACC15), Offset(cx + 25f, cy + 24f), Offset(cx + 25f, cy + 36f), strokeWidth = 2f)
}

private fun DrawScope.drawRegulatorDiagram(w: Float, h: Float, cx: Float, cy: Float) {
    val roadColor = Color(0xFF334155)
    drawRect(roadColor, Offset(0f, cy - 45f), Size(w, 90f))
    drawRect(roadColor, Offset(cx - 45f, 0f), Size(90f, h))

    // Traffic police regulator silhouette
    drawCircle(Color(0xFFFACC15), radius = 10f, center = Offset(cx, cy))
    // Extended right arm with baton
    drawLine(Color(0xFFFACC15), Offset(cx, cy), Offset(cx + 30f, cy), strokeWidth = 6f, cap = StrokeCap.Round)
    drawLine(Color.White, Offset(cx + 30f, cy), Offset(cx + 45f, cy), strokeWidth = 4f, cap = StrokeCap.Round)

    // Approaching vehicles
    drawRoundRect(Color(0xFF38BDF8), Offset(cx - 30f, cy + 50f), Size(24f, 36f), CornerRadius(5f, 5f))
    drawRoundRect(Color(0xFF10B981), Offset(cx - 100f, cy - 20f), Size(36f, 22f), CornerRadius(5f, 5f))
}

private fun DrawScope.drawRailwayCrossing(w: Float, h: Float, cx: Float, cy: Float) {
    val roadColor = Color(0xFF334155)
    drawRect(roadColor, Offset(0f, cy - 40f), Size(w, 80f))

    // Rails crossing vertically
    val railColor = Color(0xFF94A3B8)
    drawLine(railColor, Offset(cx - 15f, 0f), Offset(cx - 15f, h), strokeWidth = 6f)
    drawLine(railColor, Offset(cx + 15f, 0f), Offset(cx + 15f, h), strokeWidth = 6f)

    // Cross ties (shpalalar)
    for (y in 10 until h.toInt() step 20) {
        drawLine(Color(0xFF475569), Offset(cx - 24f, y.toFloat()), Offset(cx + 24f, y.toFloat()), strokeWidth = 4f)
    }

    // Car waiting at required 10m distance
    drawRoundRect(Color(0xFF38BDF8), Offset(cx - 110f, cy + 5f), Size(42f, 24f), CornerRadius(5f, 5f))

    // Barrier / Shlagbaum with red/white stripes
    drawLine(Color.White, Offset(cx - 40f, cy - 35f), Offset(cx - 40f, cy + 35f), strokeWidth = 6f)
    drawLine(Color(0xFFEF4444), Offset(cx - 40f, cy - 20f), Offset(cx - 40f, cy - 5f), strokeWidth = 6f)
    drawLine(Color(0xFFEF4444), Offset(cx - 40f, cy + 10f), Offset(cx - 40f, cy + 25f), strokeWidth = 6f)
}
