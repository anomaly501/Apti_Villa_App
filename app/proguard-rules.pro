# Add project specific ProGuard rules here.

# Keep serialization classes
-keepattributes *Annotation*, InnerClasses, Signature
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
}
-keep @kotlinx.serialization.Serializable class * { *; }

# Keep Room entities and DAOs
-keep class app.apti.villa.data.local.entity.** { *; }
-keep class app.apti.villa.data.local.dao.** { *; }
-keep class * extends androidx.room.RoomDatabase

# Keep Domain models
-keep class app.apti.villa.domain.model.** { *; }

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# OkHttp & Okio
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

# Google Tink
-dontwarn com.google.crypto.tink.**

