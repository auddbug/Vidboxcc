apply(plugin = "com.android.library")
apply(plugin = "kotlin-android")
apply(plugin = "com.github.recloudstream")

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
