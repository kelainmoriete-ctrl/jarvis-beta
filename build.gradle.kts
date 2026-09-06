plugins {
    id("com.android.application")
    kotlin("android")
}
android {
    namespace = "com.jarvis.voice"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jarvis.voice"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
