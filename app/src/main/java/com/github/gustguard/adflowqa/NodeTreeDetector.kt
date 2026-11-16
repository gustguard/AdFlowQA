package com.github.gustguard.adflowqa

import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo

class NodeTreeDetector {

    private val targetKeywords = listOf(
        "kapat", "close", "atla", "skip", "dismiss", "geç", "devam", "continue"
    )

    private val targetViewIds = listOf(
        "close_button", "skip_button", "btn_close", "btn_skip", "ad_close", "iv_close"
    )

    fun findTargetBounds(rootNode: AccessibilityNodeInfo?): Rect? {
        if (rootNode == null) return null
        return searchNodeRecursively(rootNode)
    }

    private fun searchNodeRecursively(node: AccessibilityNodeInfo): Rect? {
        val text = node.text?.toString()?.lowercase()?.trim() ?: ""
        val contentDesc = node.contentDescription?.toString()?.lowercase()?.trim() ?: ""
        val viewId = node.viewIdResourceName?.lowercase() ?: ""

        val isKeywordMatch = targetKeywords.any { kw ->
            text.contains(kw) || contentDesc.contains(kw)
        }

        val isIdMatch = targetViewIds.any { idPart -> viewId.contains(idPart) }

        if (isKeywordMatch || isIdMatch) {
            val bounds = Rect()
            node.getBoundsInScreen(bounds)
            if (bounds.width() > 5 && bounds.height() > 5 && node.isVisibleToUser) {
                return bounds
            }
        }

        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = searchNodeRecursively(child)
            if (result != null) return result
        }

        return null
    }
}