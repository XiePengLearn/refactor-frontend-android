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

 -dontwarn com.alibaba.**
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
 -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

 # okhttp
 -dontwarn okhttp3.**

 #retrofit
 -dontwarn retrofit2.**
 -dontwarn okio.**

 #bugly
 -dontwarn com.tencent.bugly.**
 -keep public class com.tencent.bugly.**{*;}

 # =====================fresco================

 -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

 # Do not strip any method/class that is annotated with @DoNotStrip
 -keep @com.facebook.common.internal.DoNotStrip class *
 -keepclassmembers class * {
     @com.facebook.common.internal.DoNotStrip *;
 }

 # Keep native methods
 -keepclassmembers class * {
     native <methods>;
 }

 -dontwarn okio.**
 -dontwarn com.squareup.okhttp.**
 -dontwarn okhttp3.**
 -dontwarn javax.annotation.**
 -dontwarn com.android.volley.toolbox.**
 -dontwarn com.facebook.**

 # =====================fresco================

 #转换JSON的JavaBean，类成员名称保护，使其不被混淆
 -keepclassmembernames class com.sxjs.jd.entities.** { *; }

 #auto view pager
 -keepclassmembernames class com.sxjs.common.widget.autoscrollviewpager.** { *; }


 #======================信鸽===============================
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep class com.tencent.android.tpush.** {*;}
 -keep class com.tencent.mid.** {*;}
 -keep class com.qq.taf.jce.** {*;}

     #华为通道混淆
     -ignorewarning
     -keepattributes *Annotation*
     -keepattributes Exceptions
     -keepattributes InnerClasses
     -keepattributes Signature
     -keepattributes SourceFile,LineNumberTable
     -keep class com.hianalytics.android.**{*;}
     -keep class com.huawei.updatesdk.**{*;}
     -keep class com.huawei.hms.**{*;}
     -keep class com.huawei.android.hms.agent.**{*;}
     #华为通道混淆结束

     #小米通道混淆开始
     -keep class com.xiaomi.**{*;}
     -keep public class * extends com.xiaomi.mipush.sdk.PushMessageReceiver
     #小米通道混淆结束
     #信鸽结束


-optimizationpasses 5
-dontusemixedcaseclassnames
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-ignorewarnings





-dontwarn org.apache.http.**
-keep class org.apache.http.** { *;}


-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView


-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService


-keepclasseswithmembernames class * {
    native <methods>;
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

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep public class [com.kpi.client].R$*{
    public static final int *;
}

-keep class com.watchdata.** {*;}
-dontwarn com.watchdata.**

-dontwarn com.thoughtworks.xstream.**
-keep class com.thoughtworks.xstream.**{*;}



-keep class com.squareup.**{*;}
-dontwarn  com.squareup.**

-keep class net.java.sip.communicator.impl.protocol.jabber.extensions.**{*;}
-dontwarn  net.java.sip.communicator.impl.protocol.jabber.extensions.**

#2.0.9���������ͨ�����ܣ�����ʹ�ô˹��ܵ�api����������keep
-dontwarn ch.imvs.**
-dontwarn org.slf4j.**
-keep class org.ice4j.** {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}

-keep class com.google.gson.**{*;}
-keep class com.kpi.client.rqt.obj.JkxEvaluateRequest {*;}

-keep class com.kpi.client.net.NetTaskThread {*;}



-keep class com.kpi.client.rsp.obj.**{*;}
-keep class com.kpi.client.view.**{*;}
-keep class com.kpi.client.bean.**{*;}



    #eventbus 开始
    -keepattributes *Annotation*
    -keepclassmembers class ** {
        @org.greenrobot.eventbus.Subscribe <methods>;
    }
    -keep enum org.greenrobot.eventbus.ThreadMode { *; }

    # Only required if you use AsyncExecutor
    -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
        <init>(Java.lang.Throwable);
    }
    #event bus 结束


     #butterknife
        -keep class butterknife.** { *; }
        -dontwarn butterknife.internal.**
        -keep class **$$ViewBinder { *; }
        -keepclasseswithmembernames class * {      @butterknife.* <fields>;  }
        -keepclasseswithmembernames class * {      @butterknife.* <methods>;  }

    #Glide库 混淆
    -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep public class * extends com.bumptech.glide.module.AppGlideModule
        -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
        **[] $VALUES;
        public *;
      }

    #极验验证
    -keep class com.geetest.android.sdk.**{*;}
    #popw混淆
    -dontwarn com.lxj.xpopup.widget.**
    -keep class com.lxj.xpopup.widget.**{*;}

    #bugly避免混淆
    -dontwarn com.tencent.bugly.**
    -keep public class com.tencent.bugly.**{*;}

    #hua环信混淆
    -keep class com.hyphenate.** {*;}
    -dontwarn  com.hyphenate.**
    #如果使用了实时音视频功能
    -keep class com.superrtc.** {*;}
    -dontwarn  com.superrtc.**
    -libraryjars ../easeui/src/main/jniLibs/armeabi/libBaiduMapSDK_base_v5_1_0.so
    -libraryjars ../easeui/src/main/jniLibs/armeabi/libBaiduMapSDK_map_v5_1_0.so
    -libraryjars ../easeui/src/main/jniLibs/armeabi/liblocSDK7b.so
    #环信混淆

    #讯飞sdk
    -keep class com.iflytek.**{*;}
    -keepattributes Signature

    #易网新混淆
    #保持 Serializable 不被混淆
    -keepnames class * implements java.io.Serializable
    # 不混淆 gson 代码
    -keep class com.google.gson.** {*;}
    # 不混淆医网信所有文件
    -dontwarn cn.org.bjca.**
    -keep class cn.org.bjca.**{ *;}
    -keep interface cn.org.bjca.**{ *;}
    #易网签结束

    #信鸽sdk
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep class com.tencent.android.tpush.** {* ;}
    -keep class com.tencent.mid.** {* ;}
    -keep class com.qq.taf.jce.** {*;}
    -keep class com.tencent.bigdata.** {* ;}
    #华为通道混淆
    -ignorewarning
    -keepattributes *Annotation*
    -keepattributes Exceptions
    -keepattributes InnerClasses
    -keepattributes Signature
    -keepattributes SourceFile,LineNumberTable
    -keep class com.hianalytics.android.**{*;}
    -keep class com.huawei.updatesdk.**{*;}
    -keep class com.huawei.hms.**{*;}
    -keep class com.huawei.android.hms.agent.**{*;}
    #华为通道混淆结束

    #小米通道混淆开始
    -keep class com.xiaomi.**{*;}
    -keep public class * extends com.xiaomi.mipush.sdk.PushMessageReceiver
    #小米通道混淆结束
    #信鸽结束

   #PictureSelector 2.0
   -keep class com.luck.picture.lib.** { *; }

   -dontwarn com.yalantis.ucrop**
   -keep class com.yalantis.ucrop** { *; }
   -keep interface com.yalantis.ucrop** { *; }

    #rxjava
   -dontwarn sun.misc.**
   -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
   }
   -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
   }
   -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
   }

   #rxandroid
   -dontwarn sun.misc.**
   -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
      long producerIndex;
      long consumerIndex;
   }
   -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
       rx.internal.util.atomic.LinkedQueueNode producerNode;
   }
   -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
       rx.internal.util.atomic.LinkedQueueNode consumerNode;
   }

   #glide
   -keep public class * implements com.bumptech.glide.module.GlideModule
   -keep public class * extends com.bumptech.glide.AppGlideModule
   -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
     **[] $VALUES;
     public *;
   }

 # for DexGuard only
 #-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

   #结束

    #x5webview 开始
       -dontskipnonpubliclibraryclassmembers
       -dontwarn dalvik.**
       -dontwarn com.tencent.smtt.**
       #-overloadaggressively

       # ------------------ Keep LineNumbers and properties ---------------- #
       -keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
       # --------------------------------------------------------------------------

       # Addidional for x5.sdk classes for apps

       -keep class com.tencent.smtt.export.external.**{
           *;
       }

       -keep class com.tencent.tbs.video.interfaces.IUserStateChangedListener {
           *;
       }

       -keep class com.tencent.smtt.sdk.CacheManager {
           public *;
       }

       -keep class com.tencent.smtt.sdk.CookieManager {
           public *;
       }

       -keep class com.tencent.smtt.sdk.WebHistoryItem {
           public *;
       }

       -keep class com.tencent.smtt.sdk.WebViewDatabase {
           public *;
       }

       -keep class com.tencent.smtt.sdk.WebBackForwardList {
           public *;
       }

       -keep public class com.tencent.smtt.sdk.WebView {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.WebView$HitTestResult {
           public static final <fields>;
           public java.lang.String getExtra();
           public int getType();
       }

       -keep public class com.tencent.smtt.sdk.WebView$WebViewTransport {
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.WebView$PictureListener {
           public <fields>;
           public <methods>;
       }


       -keepattributes InnerClasses

       -keep public enum com.tencent.smtt.sdk.WebSettings$** {
           *;
       }

       -keep public enum com.tencent.smtt.sdk.QbSdk$** {
           *;
       }

       -keep public class com.tencent.smtt.sdk.WebSettings {
           public *;
       }


       -keepattributes Signature
       -keep public class com.tencent.smtt.sdk.ValueCallback {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.WebViewClient {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.DownloadListener {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.WebChromeClient {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.WebChromeClient$FileChooserParams {
           public <fields>;
           public <methods>;
       }

       -keep class com.tencent.smtt.sdk.SystemWebChromeClient{
           public *;
       }
       # 1. extension interfaces should be apparent
       -keep public class com.tencent.smtt.export.external.extension.interfaces.* {
           public protected *;
       }

       # 2. interfaces should be apparent
       -keep public class com.tencent.smtt.export.external.interfaces.* {
           public protected *;
       }

       -keep public class com.tencent.smtt.sdk.WebViewCallbackClient {
           public protected *;
       }

       -keep public class com.tencent.smtt.sdk.WebStorage$QuotaUpdater {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.WebIconDatabase {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.WebStorage {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.DownloadListener {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.QbSdk {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.QbSdk$PreInitCallback {
           public <fields>;
           public <methods>;
       }
       -keep public class com.tencent.smtt.sdk.CookieSyncManager {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.Tbs* {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.utils.LogFileUtils {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.utils.TbsLog {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.utils.TbsLogClient {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.CookieSyncManager {
           public <fields>;
           public <methods>;
       }

       # Added for game demos
       -keep public class com.tencent.smtt.sdk.TBSGamePlayer {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.TBSGamePlayerClient* {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.TBSGamePlayerClientExtension {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.TBSGamePlayerService* {
           public <fields>;
           public <methods>;
       }

       -keep public class com.tencent.smtt.utils.Apn {
           public <fields>;
           public <methods>;
       }
       -keep class com.tencent.smtt.** {
           *;
       }
       # end


       -keep public class com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension {
           public <fields>;
           public <methods>;
       }

       -keep class MTT.ThirdAppInfoNew {
           *;
       }

       -keep class com.tencent.mtt.MttTraceEvent {
           *;
       }

       # Game related
       -keep public class com.tencent.smtt.gamesdk.* {
           public protected *;
       }

       -keep public class com.tencent.smtt.sdk.TBSGameBooter {
               public <fields>;
               public <methods>;
       }

       -keep public class com.tencent.smtt.sdk.TBSGameBaseActivity {
           public protected *;
       }

       -keep public class com.tencent.smtt.sdk.TBSGameBaseActivityProxy {
           public protected *;
       }

       -keep public class com.tencent.smtt.gamesdk.internal.TBSGameServiceClient {
           public *;
       }
       #---------------------------------------------------------------------------


       #------------------  下方是android平台自带的排除项，这里不要动         ----------------

       -keep public class * extends android.app.Activity{
           public <fields>;
           public <methods>;
       }
       -keep public class * extends android.app.Application{
           public <fields>;
           public <methods>;
       }
       -keep public class * extends android.app.Service
       -keep public class * extends android.content.BroadcastReceiver
       -keep public class * extends android.content.ContentProvider
       -keep public class * extends android.app.backup.BackupAgentHelper
       -keep public class * extends android.preference.Preference

       -keepclassmembers enum * {
           public static **[] values();
           public static ** valueOf(java.lang.String);
       }

       -keepclasseswithmembers class * {
           public <init>(android.content.Context, android.util.AttributeSet);
       }

       -keepclasseswithmembers class * {
           public <init>(android.content.Context, android.util.AttributeSet, int);
       }

       -keepattributes *Annotation*

       -keepclasseswithmembernames class *{
           native <methods>;
       }

       -keep class * implements android.os.Parcelable {
         public static final android.os.Parcelable$Creator *;
       }

       #------------------  下方是共性的排除项目         ----------------
       # 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
       # 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

       -keepclasseswithmembers class * {
           ... *JNI*(...);
       }

       -keepclasseswithmembernames class * {
           ... *JRI*(...);
       }

       -keep class **JNI* {*;}
       #x5webview 结束


       #TabLayout需要混淆
       -keep class android.support.design.widget.TabLayout{*;}

       #下载服务混淆
         -keep class com.jkx4rh.client.service.**{*;}

        #去除log
        -assumenosideeffects class android.util.Log {
            public static *** d(...);
            public static *** v(...);
            public static *** i(...);
            public static *** e(...);
            public static *** w(...);
        }


