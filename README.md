# AdFlowQA - Hibrit QA Otomasyon & Reklam Geçici

Bu proje, Android uygulamalarında test otomasyonu ve erişilebilirlik özelliklerini kullanarak otomatik etkileşimler gerçekleştiren bir Android servisidir.

## Özellikler
- **Hibrit Tespit:** Standart Android UI Tree (Düğüm Ağacı) ile bulunamayan butonları **ML Kit OCR (Görsel Metin Tanıma)** kullanarak tespit eder.
- **Akıllı Tıklama:** Gerçek kullanıcı davranışını taklit etmek için rastgele koordinat sapmalarıyla tıklama yapar.
- **Dinamik Kelime Listesi:** "Kapat", "Atla", "Skip" gibi anahtar kelimeleri hem kod hem de görsel düzeyde tarar.

## Kullanım
1. Projeyi Android Studio ile derleyin ve cihazınıza yükleyin.
2. Cihaz Ayarları > Erişilebilirlik > **AdFlowQA** servisini etkinleştirin.
3. Uygulama, ekran içeriğini tarayarak hedef butonları otomatik olarak bulup tıklayacaktır.

## Teknolojiler
- Jetpack Compose
- Google ML Kit (Text Recognition)
- Android Accessibility Service
