package com.github.gustguard.adflowqa

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class QAAccessibilityService : AccessibilityService() {

    private lateinit var nodeDetector: NodeTreeDetector
    private lateinit var visionDetector: VisionOcrDetector
    private lateinit var gestureExecutor: GestureExecutor

    private var currentState = BotState.IDLE
    private var lastScanTime = 0L

    override fun onServiceConnected() {
        super.onServiceConnected()
        nodeDetector = NodeTreeDetector()
        visionDetector = VisionOcrDetector(this)
        gestureExecutor = GestureExecutor(this)
        Log.i("AdFlowQA", "Hibrit QA Otomasyon Servisi Başlatıldı.")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val now = System.currentTimeMillis()
        if (currentState != BotState.IDLE || now - lastScanTime < 1500L) return

        lastScanTime = now
        currentState = BotState.SCANNING_TREE

        // 1. Katman: Hızlı XML Arayüz Taraması
        val treeTarget = nodeDetector.findTargetBounds(rootInActiveWindow)
        if (treeTarget != null) {
            executeClick(treeTarget, "Layer-1 (UI Tree)")
            return
        }

        // 2. Katman: Bulunamazsa ML Kit OCR ile Görsel Tarama
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            currentState = BotState.SCANNING_VISION
            visionDetector.scanScreenForTarget { visionTarget ->
                if (visionTarget != null) {
                    executeClick(visionTarget, "Layer-2 (ML Kit OCR)")
                } else {
                    currentState = BotState.IDLE
                }
            }
        } else {
            currentState = BotState.IDLE
        }
    }

    private fun executeClick(bounds: android.graphics.Rect, sourceLayer: String) {
        currentState = BotState.ACTION_EXECUTING
        Log.d("AdFlowQA", "[$sourceLayer] Hedef tespit edildi: $bounds")
        gestureExecutor.tapBounds(bounds) { success ->
            Log.d("AdFlowQA", "[$sourceLayer] Dokunma sonucu: $success")
            currentState = BotState.IDLE
        }
    }

    override fun onInterrupt() {
        currentState = BotState.IDLE
    }
}