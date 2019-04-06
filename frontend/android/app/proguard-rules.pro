-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontpreverify
-optimizations !field/*
-allowaccessmodification
-repackageclasses

-renamesourcefileattribute SourceFile

-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

-keep class kotlin.Metadata { *; }
-keep public class kotlin.reflect.jvm.internal.impl.builtins.* { public *; }
-keep public class kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.* { public *; }

#-keepnames class kotlinx.** { *; }

-keep interface com.airbnb.mvrx.MvRxState
-keep class * implements com.airbnb.mvrx.MvRxState { *; }
-keepnames class * implements com.airbnb.mvrx.MvRxViewModelFactory

-keepnames class kotlinx.** { *; }

-keep class androidx.lifecycle.ViewModel
-keep class com.airbnb.mvrx.BaseMvRxViewModel
