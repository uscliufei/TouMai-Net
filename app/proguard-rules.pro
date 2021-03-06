# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidSdk_studio/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#蒲公英的混淆
-libraryjars libs/pgyer_sdk_x.x.jar
-dontwarn com.pgyersdk.**
-keep class com.pgyersdk.** { *; }
#支付相关的混淆
#pay_library
-dontwarn io.github.mayubao.pay_library.**
-keep class io.github.mayubao.pay_library.** {*;}
#wechat pay
-dontwarn com.tencent.**
-keep class com.tencent.** {*;}
#alipay
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}
-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}
-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}
-dontwarn  org.json.alipay.**
-keep class corg.json.alipay.** {*;}