plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Flutter plugin (must be after Android/Kotlin)
    id("dev.flutter.flutter-gradle-plugin")

    // ✅ Required for Firebase / Google Sign-In
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.food_go"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        // ✅ Enable core library desugaring (required for flutter_local_notifications)
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        applicationId = "com.example.food_go"
        minSdk = flutter.minSdkVersion
        targetSdk = flutter.targetSdkVersion
        versionCode = flutter.versionCode
        versionName = flutter.versionName

        // ✅ Add FCM default channel Id (optional but handy)
        manifestPlaceholders["firebase_channel_id"] = "default_channel_id"
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

flutter {
    source = "../.."
}

dependencies {
    // ✅ Firebase + Google Sign-In SDKs
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.android.gms:play-services-auth:21.1.0")

    // ✅ Firebase Cloud Messaging
    implementation("com.google.firebase:firebase-messaging:24.0.0")

    // ✅ Needed when coreLibraryDesugaringEnabled = true (version 2.1.4+ required)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")

    // (Optional) if you plan to use Firestore or Realtime Database later
    // implementation("com.google.firebase:firebase-firestore:25.0.0")
    // implementation("com.google.firebase:firebase-database:21.0.0")
}