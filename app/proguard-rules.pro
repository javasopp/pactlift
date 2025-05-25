# 为MPAndroidChart添加ProGuard规则
-keep class com.github.mikephil.charting.** { *; }

# 为Room添加ProGuard规则
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# 保持模型类不被混淆
-keep class com.pactlift.android.data.model.** { *; }

# 通用Android规则
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-keep class androidx.core.app.CoreComponentFactory { *; } 