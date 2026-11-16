# AdFlowQA - Hibrit QA Otomasyon & Reklam Geçici

Bu proje, Android uygulamalarında test otomasyonu ve erişilebilirlik özelliklerini kullanarak otomatik etkileşimler gerçekleştiren bir Android servisidir.

## 🚀 Özellikler
- **Hibrit Tespit:** Standart Android UI Tree (Düğüm Ağacı) ile bulunamayan butonları **ML Kit OCR (Görsel Metin Tanıma)** kullanarak tespit eder.
- **Akıllı Tıklama:** Gerçek kullanıcı davranışını taklit etmek için rastgele koordinat sapmalarıyla tıklama yapar.
- **Dinamik Kelime Listesi:** "Kapat", "Atla", "Skip" gibi anahtar kelimeleri hem kod hem de görsel düzeyde tarar.

## 📥 Kurulum ve Çalıştırma

### Geliştiriciler İçin
1. Projeyi bilgisayarınıza indirin veya klonlayın:
   ```bash
   git clone https://github.com/gustguard/AdFlowQA.git
   ```
2. **Android Studio** ile projeyi açın.
3. Projeyi bir Android cihazda (veya emülatörde) derleyip çalıştırın.

### Kullanıcılar İçin
1. [Releases](https://github.com/gustguard/AdFlowQA/releases) sekmesinden en güncel APK dosyasını indirin.
2. APK'yı telefonunuza kurun.

## 🛠️ Kullanım Adımları
1. Cihaz Ayarları > Erişilebilirlik (Accessibility) menüsüne gidin.
2. **AdFlowQA** servisini bulun ve etkinleştirin.
3. Servis, ekran içeriğini tarayarak hedef butonları otomatik olarak bulup tıklayacaktır.

## 🧰 Teknolojiler
- Jetpack Compose
- Google ML Kit (Text Recognition)
- Android Accessibility Service
- Kotlin & Coroutines
