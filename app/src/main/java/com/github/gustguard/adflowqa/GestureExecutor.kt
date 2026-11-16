package com.github.gustguard.adflowqa

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import kotlin.random.Random

class GestureExecutor(private val service: AccessibilityService) {

    fun tapBounds(bounds: Rect, onComplete: ((Boolean) -> Unit)? = null) {
        val offsetX = Random.nextInt(-4, 5)
        val offsetY = Random.nextInt(-4, 5)
        val targetX = (bounds.centerX() + offsetX).coerceAtLeast(1).toFloat()
        val targetY = (bounds.centerY() + offsetY).coerceAtLeast(1).toFloat()

        tapCoordinates(targetX, targetY, onComplete)
    }

    fun tapCoordinates(x: Float, y: Float, onComplete: ((Boolean) -> Unit)? = null) {
        val clickPath = Path().apply { moveTo(x, y) }
        val duration = Random.nextLong(55L, 115L)

        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(clickPath, 0L, duration))
            .build()

        service.dispatchGesture(
            gesture,
            object : AccessibilityService.GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    onComplete?.invoke(true)
                }

                override fun onCancelled(gestureDescription: GestureDescription?) {
                    onComplete?.invoke(false)
                }
            },
            null
        )
    }
}