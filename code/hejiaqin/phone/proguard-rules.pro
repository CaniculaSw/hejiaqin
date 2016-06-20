# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\devtools\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#通用混淆

#指定混淆是采用的算法，后面的参数是一个过滤器
#这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
#代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

-allowaccessmodification

-dontpreverify

#混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
#指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
#这句话能够使我们的项目混淆后产生映射文件
#包含有类名->混淆后类名的映射关系
-verbose

-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt

#不混淆泛型
-keepattributes Signature
#保留Annotation不混淆
-keepattributes *Annotation*
-keepattributes InnerClasses

-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-dontwarn android.support.**

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.database.sqlite.SQLiteOpenHelper{*;}
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class * extends android.support.v4.*
-keep public class * extends android.support.v7.*
-keep class * extends android.app.Dialog{*;}
-keep class * implements java.io.Serializable{*;}

-keep public class android.support.**{*;}

#业务混淆

#bga-banner jar
-keep class cn.bingoogolapple.**{ *; }
#circleimageview-2.0.0
-keep class de.hdodenhof.circleimageview.**{ *; }
#gson jar
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.**{ *; }
#library-1.4.0
-keep class com.kyleduo.switchbutton.**{ *; }
#library-2.4.0
-keep class com.nineoldandroids.**{ *; }
#okhttp-2.6.0
-keep class com.squareup.okhttp.**{ *; }
#okio-1.6.0
-keep class okio.**{ *; }
#picasso-2.5.2
-keep class com.squareup.picasso.**{ *; }

-dontwarn cn.bingoogolapple.**
-dontwarn de.hdodenhof.circleimageview.**
-dontwarn sun.misc.Unsafe
-dontwarn com.google.gson.**
-dontwarn com.kyleduo.switchbutton.**
-dontwarn com.nineoldandroids.**
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-dontwarn com.squareup.picasso.**


#数据结构使用了gson
-keep class com.chinamobile.hejiaqin.business.model.**{*;}