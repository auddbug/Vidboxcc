plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.github.recloudstream")
}

cloudstream {
    version = 1
    description = "Vidbox Provider"
    authors = listOf("auddbug")
}

android {
    namespace = "com.auddbug.vidbox"
    compileSdk = 33
    
    defaultConfig {
        minSdk = 21
    }
}

dependencies {
    implementation("com.github.lagradost:cloudstream3:master-SNAPSHOT")
}
