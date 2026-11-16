package com.github.gustguard.adflowqa

import android.accessibilityservice.AccessibilityService
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.view.Display
import androidx.annotation.RequiresApi
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class VisionOcrDetector(private val service: AccessibilityService) {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private val ocrKeywords = listOf("kapat", "close", "atla", "skip", "geç", "dismiss")

    @RequiresApi(Build.VERSION_CODES.R)
    fun scanScreenForTarget(onResult: (Rect?) -> Unit) {
        service.takeScreenshot(
            Display.DEFAULT_DISPLAY,
            service.mainExecutor,
            object : AccessibilityService.TakeScreenshotCallback {
                override fun onSuccess(screenshot: AccessibilityService.ScreenshotResult) {
                    val hardwareBuffer = screenshot.hardwareBuffer
                    val colorSpace = screenshot.colorSpace
                    val bitmap = Bitmap.wrapHardwareBuffer(hardwareBuffer, colorSpace)
                    hardwareBuffer.close()

                    if (bitmap == null) {
                        onResult(null)
                        return
                    }

                    val softwareBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)
                    bitmap.recycle()

                    val image = InputImage.fromBitmap(softwareBitmap, 0)
                    recognizer.process(image)
                        .addOnSuccessListener { visionText ->
                            var matchedRect: Rect? = null
                            for (block in visionText.textBlocks) {
                                val normalized = block.text.lowercase().trim()
                                if (ocrKeywords.any { normalized == it || normalized.contains(" $it") || normalized.startsWith("$it ") }) {
                                    matchedRect = block.boundingBox
                                    break
                                }
                            }
                            softwareBitmap.recycle()
                            onResult(matchedRect)
                        }
                        .addOnFailureListener {
                            softwareBitmap.recycle()
                            onResult(null)
                        }
                }

                override fun onFailure(errorCode: Int) {
                    onResult(null)
                }
            }
        )
    }
}