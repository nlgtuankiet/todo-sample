### WHAT A MESS! optimize later

#region moshi
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

-keep @com.squareup.moshi.JsonQualifier interface *

# Enum field names are used by the integrated EnumJsonAdapter.
# Annotate enums with @JsonClass(generateAdapter = false) to use them with Moshi.
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
}

# The name of @JsonClass types is used to look up the generated adapter.
-keepnames @com.squareup.moshi.JsonClass class *

# Retain generated JsonAdapters if annotated type is retained.
-if @com.squareup.moshi.JsonClass class *
-keep class <1>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*
-keep class <1>_<2>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*$*
-keep class <1>_<2>_<3>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*$*$*
-keep class <1>_<2>_<3>_<4>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*$*$*$*
-keep class <1>_<2>_<3>_<4>_<5>JsonAdapter {
    <init>(...);
    <fields>;
}
-if @com.squareup.moshi.JsonClass class **$*$*$*$*$*
-keep class <1>_<2>_<3>_<4>_<5>_<6>JsonAdapter {
    <init>(...);
    <fields>;
}
#endregion

#copy from tivi
# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontpreverify

# Optimize all the things (other than those listed)
-optimizations !field/*

-allowaccessmodification
-repackageclasses ''

-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile

# Fabric/Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

# Retrofit
-dontnote retrofit2.Platform
-keepattributes Signature, InnerClasses, Exceptions
# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Keep Trakt-java Entity names (for GSON)
-keepclassmembernames class com.uwetrottmann.trakt5.entities.** { <fields>; }
-keepclassmembers class com.uwetrottmann.trakt5.entities.** { <init>(...); }

# Keep TMDb Entity names (for GSON)
-keepclassmembernames class com.uwetrottmann.tmdb2.entities.** { <fields>; }
-keepclassmembers class com.uwetrottmann.tmdb2.entities.** { <init>(...); }

# !! Remove this once https://issuetracker.google.com/issues/112386012 is fixed !!
-keep class com.uwetrottmann.trakt5.entities.**
-keep class com.uwetrottmann.tmdb2.entities.**

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Coroutines
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
# Keep Coroutine class names. See https://github.com/Kotlin/kotlinx.coroutines/issues/657.
# This should be removed when bug is fixed.
-keepnames class kotlinx.** { *; }

-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }

# Kotlin Reflect internal impl
-keep public class kotlin.reflect.jvm.internal.impl.builtins.* { public *; }
-keep public class kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.* { public *; }

-keep interface com.airbnb.mvrx.MvRxState
-keep class * implements com.airbnb.mvrx.MvRxState { *; }
-keepnames class * implements com.airbnb.mvrx.MvRxViewModelFactory
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
#endregion
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-keepnames class kotlinx.** { *; }

-keep class androidx.lifecycle.ViewModel
-keep class com.airbnb.mvrx.BaseMvRxViewModel
-keep class kotlin.reflect.jvm.internal.impl.load.java.FieldOverridabilityCondition
-keep class kotlin.reflect.** { *; }
########--------Retrofit + RxJava--------#########
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions