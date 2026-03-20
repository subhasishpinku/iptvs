# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep Lottie
-keep class com.airbnb.lottie.** { *; }

# Keep ExoPlayer
-keep class com.google.android.exoplayer2.** { *; }

# Keep Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Remove unused classes
-dontwarn kotlin.reflect.jvm.internal.**
-dontwarn org.jetbrains.annotations.**
-keep class androidx.leanback.widget.** { *; }

# Suppress WebView/Chrome warnings
-dontwarn org.chromium.**
-dontwarn com.google.android.gms.**
-dontwarn com.pierfrancescosoffritti.androidyoutubeplayer.**

# Keep YouTube Player classes
-keep class com.pierfrancescosoffritti.androidyoutubeplayer.** { *; }

# Keep WebView classes
-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void openFileChooser(...);
}

# Keep bitmap optimization
-keepclassmembers class * extends android.graphics.Bitmap {
    public void recycle();
    public boolean isRecycled();
}

# Keep vector drawable compatibility (AndroidX version)
-keep class androidx.vectordrawable.graphics.drawable.VectorDrawableCompat { *; }

# Keep AppCompat resources
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}