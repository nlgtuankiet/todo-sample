# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name map the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this map preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this map
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Lorem
-keepnames class com.thedeanda.lorem.LoremIpsum

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
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