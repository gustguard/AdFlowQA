package com.github.gustguard.adflowqa

enum class BotState {
    IDLE,               // Beklemede
    SCANNING_TREE,      // 1. Katman: XML düğüm ağacı taranıyor
    SCANNING_VISION,    // 2. Katman: ML Kit OCR ile ekran görüntüsü taranıyor
    ACTION_EXECUTING,   // Hedef bulundu, dokunma komutu gönderiliyor
    COOLDOWN            // Ardışık hatalı tıklamaları önlemek için bekleme
}