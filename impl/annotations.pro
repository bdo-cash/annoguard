# Copyright (C) 2016-present, Wei Chou(weichou2010@gmail.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##__________________________________________________
## 本实现基于 ProGuard 5.2.1 测试 #
## @author Wei Chou(weichou2010@gmail.com)
## @version 1.0, 19/02/2016
##
## TODO: 但目前的 R8 也适用（by 15/11/2023）#
##``````````````````````````````````````````````````

##################### 注意：注释不可以中文结束，应该像本行这样。#####################


#####>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>##
##### 名词解释：
# [强]保留：既不被重命名，又不被删除；
# [弱]保留：只确保不被重命名，不保证不被删除；
# [反]保留：不保证不被重命名，但确保不被删除；
# [不]保留：排除到以上保留名单之外。即应用混淆。
##### 优先级：
# 由高到低依次为强、反、弱、不。被延续到子类（接口）的，子类享有相同优先级。
# 任何位置，若被不同优先级的注解同时标注，则会应用较高优先级。
##### 约定：
# 含有[$$]字符的为[反保留]；
# 含有[$]字符的为[强保留]（反保留除外）；
# 不含以上标识字符的为[弱保留]；
# 没有用于[不保留]的标签，即：不加任何标注则为不保留；
#     等效性：所有不保留的类型，如果该类型的任一字段被保留了，则效果上该类型名被反保留。
# 另：以[e]结尾的表示将其前面名称表达的功能延续到子类。
# 需要注意的是：只有父类（接口）没有被混淆删除的情况下，才能延续到子类。
# 即：首先需要将父类强保留或反保留。
##### 其它：
# 对于包[**.anno.inject]中的任何注解，只要该注解没有被优化掉，就会对其直接作用的内容进行反保留。
## 更多内容请见 @**.anno.proguard.Keep 注解的文档。
##<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<#####


#####>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>##
##### 客户代码可使用如下方法引用本配置：#
#-include libs/annotations.pro（必须）
#-include libs/optimize.pro 或：
#-include libs/dont-optimize.pro
##<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<#####


##__________________________________________________
## 这些都不要：#
#-dontshrink
#-keepattributes *Annotation*
##``````````````````````````````````````````````````

##__________________________________________________
## 保持泛型签名，用于 Gson 反序列化，以及其他反射用途。#
-keepattributes Signature
#
##__________________________________________________
## 保持行号，以便跟踪错误记录。#
# 但是这些信息会占用不小的空间（只不过不是在原始字节码上增加的）#
# 通过命令可恢复原文：#
#    java -jar retrace.jar mapping.txt stacktrace.txt
# 见 http://proguard.sourceforge.net/manual/examples.html#stacktrace #
##``````````````````````````````````````````````````
#-printmapping out.map  # 默认会输出到 mapping.txt，这里不用配置。#
# 参数 x 会出现在 at com.xxx.ClassName.method(x:40) 括号中，#
# 默认跟混淆前的类名一致，所以必须替换。#
-renamesourcefileattribute x
##``````````````````````````````````````````````````
-keepattributes SourceFile, LineNumberTable
##``````````````````````````````````````````````````

##__________________________________________________
# 虽然 class 限定词对 interface 也起作用，保险起见，还是都写上。#
##``````````````````````````````````````````````````


#####>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>##
##### Android 提供的 Keep 注解，不推荐使用。#
-keep, allowoptimization, allowobfuscation @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep @interface *
-keep @android.support.annotation.Keep interface *
-keep @android.support.annotation.Keep class *
-keepclassmembers @interface * {
    @android.support.annotation.Keep *;
}
-keepclassmembers interface * {
    @android.support.annotation.Keep *;
}
# 这种用法不对，会把任何类名都保留。#
#-keep class * {
#    @android.support.annotation.Keep *;
#}
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}

## 符合本库定义，但又改变了原意。#
#-keep, allowoptimization, allowobfuscation @interface androidx.annotation.Keep
#-keep @androidx.annotation.Keep @interface *
#-keep @androidx.annotation.Keep interface *
#-keep @androidx.annotation.Keep class *
#-keepclassmembers @interface * {
#    @androidx.annotation.Keep *;
#}
#-keepclassmembers interface * {
#    @androidx.annotation.Keep *;
#}
#-keepclassmembers class * {
#    @androidx.annotation.Keep *;
#}

## 摘自`META-INF/proguard/androidx-annotations.pro`，用法与本库定义不一致，不推荐使用。#
-keep, allowobfuscation @interface androidx.annotation.Keep
-keep @androidx.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}
##<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<#####



#####>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>##
#-----------------接下来的内容顺序非常重要-----------------#
##<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<#####
#
##__________________________________________________
# allowshrinking 压缩，删除没有用到的方法字段等。#
# allowoptimization 优化，文件合并之类，比较复杂。注意：需要通过反射来调用的都不要启用。#
# allowobfuscation 重命名，常说的混淆其实是这一步。#
#-keep, allowshrinking, allowoptimization, allowobfuscation @interface **.anno.proguard.P$$
#
## 本实现的基础 #
-keep, allowoptimization, allowobfuscation @interface **.anno.proguard.P$$
-keep, allowoptimization, allowobfuscation @**.anno.proguard.P$$ @interface *
##``````````````````````````````````````````````````
#
#####>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>##
##### 以下为字段：#
#-keep 后面带 names 的只保证不重命名，不保证不被删除。即[弱]保留 #
##__________________________________________________
## 弱保留全部非 static 字段 #
-keepclassmembernames @**.anno.proguard.KeepV @interface * {   # 其实该注解不可能标注在`@interface`上 #
    !static <fields>;
}
-keepclassmembernames @**.anno.proguard.KeepV interface * {
    !static <fields>;
}
-keepclassmembernames @**.anno.proguard.KeepV class * {
    !static <fields>;
}
##``````````````````````````````````````````````````
## 强保留全部 public 非 static 字段 #
-keepclassmembers @**.anno.proguard.KeepVp$ @interface * {   # 其实该注解不可能标注在`@interface`上 #
    public !static <fields>;
}
-keepclassmembers @**.anno.proguard.KeepVp$ interface * {
    public !static <fields>;
}
-keepclassmembers @**.anno.proguard.KeepVp$ class * {
    public !static <fields>;
}
##``````````````````````````````````````````````````
## 同 KeepVp$，并延续到子类。#
-keepclassmembers @**.anno.proguard.KeepVp$e @interface * {   # 其实该注解不可能标注在`@interface`上 #
    public !static <fields>;
}
-keepclassmembers @**.anno.proguard.KeepVp$e interface * {
    public !static <fields>;
}
-keepclassmembers @**.anno.proguard.KeepVp$e class * {
    public !static <fields>;
}
##``````````````````````````````````````````````````
## 延续到子类 #
-keepclassmembers interface * extends @**.anno.proguard.KeepVp$e * {
    public !static <fields>;
}
-keepclassmembers class * implements @**.anno.proguard.KeepVp$e * {
    public !static <fields>;
}
-keepclassmembers class * extends @**.anno.proguard.KeepVp$e * {
    public !static <fields>;
}
##``````````````````````````````````````````````````
## 弱保留全部 static 字段 #
-keepclassmembernames @**.anno.proguard.KeepVs @interface * {   # 其实该注解不可能标注在`@interface`上 #
    static <fields>;
}
-keepclassmembernames @**.anno.proguard.KeepVs interface * {
    static <fields>;
}
-keepclassmembernames @**.anno.proguard.KeepVs class * {
    static <fields>;
}
##``````````````````````````````````````````````````
## 强保留全部 public static 字段 #
-keepclassmembers @**.anno.proguard.KeepVps$ @interface * {   # 其实该注解不可能标注在`@interface`上 #
    public static <fields>;
}
-keepclassmembers @**.anno.proguard.KeepVps$ interface * {
    public static <fields>;
}
-keepclassmembers @**.anno.proguard.KeepVps$ class * {
    public static <fields>;
}
##<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<#####
#
#
#####>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>##
##### 以下为方法：#
##__________________________________________________
## 弱保留全部非 static 方法 #
-keepclassmembernames @**.anno.proguard.KeepM @interface * {   # 其实该注解不可能标注在`@interface`上 #
    !static <methods>;
}
-keepclassmembernames @**.anno.proguard.KeepM interface * {
    !static <methods>;
}
-keepclassmembernames @**.anno.proguard.KeepM class * {
    !static <methods>;
}
##``````````````````````````````````````````````````
## 强保留全部 public 非 static 方法 #
-keepclassmembers @**.anno.proguard.KeepMp$ @interface * {   # 其实该注解不可能标注在`@interface`上 #
    public !static <methods>;
}
-keepclassmembers @**.anno.proguard.KeepMp$ interface * {
    public !static <methods>;
}
-keepclassmembers @**.anno.proguard.KeepMp$ class * {
    public !static <methods>;
}
##``````````````````````````````````````````````````
## 同 KeepMp$，并延续到子类。#
-keepclassmembers @**.anno.proguard.KeepMp$e @interface * {   # 其实该注解不可能标注在`@interface`上 #
    public !static <methods>;
}
-keepclassmembers @**.anno.proguard.KeepMp$e interface * {
    public !static <methods>;
}
-keepclassmembers @**.anno.proguard.KeepMp$e class * {
    public !static <methods>;
}
##``````````````````````````````````````````````````
## 延续到子类 #
-keepclassmembers interface * extends @**.anno.proguard.KeepMp$e * {
    public !static <methods>;
}
-keepclassmembers class * implements @**.anno.proguard.KeepMp$e * {
    public !static <methods>;
}
-keepclassmembers class * extends @**.anno.proguard.KeepMp$e * {
    public !static <methods>;
}
##``````````````````````````````````````````````````
## 弱保留全部 static 方法 #
-keepclassmembernames @**.anno.proguard.KeepMs @interface * {   # 其实该注解不可能标注在`@interface`上 #
    static <methods>;
}
-keepclassmembernames @**.anno.proguard.KeepMs interface * {
    static <methods>;
}
-keepclassmembernames @**.anno.proguard.KeepMs class * {
    static <methods>;
}
##``````````````````````````````````````````````````
## 强保留全部 public static 方法 #
-keepclassmembers @**.anno.proguard.KeepMps$ @interface * {   # 其实该注解不可能标注在`@interface`上 #
    public static <methods>;
}
-keepclassmembers @**.anno.proguard.KeepMps$ interface * {
    public static <methods>;
}
-keepclassmembers @**.anno.proguard.KeepMps$ class * {
    public static <methods>;
}
##<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<#####
#
#
#####>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>##
##__________________________________________________
## 弱保留 #
-keepnames @**.anno.proguard.Keep @interface *
-keepnames @**.anno.proguard.Keep interface *
-keepnames @**.anno.proguard.Keep class *
-keepclassmembernames @interface * {
    @**.anno.proguard.Keep *;
}
-keepclassmembernames interface * {
    @**.anno.proguard.Keep *;
}
# 这种用法不对，会把任何类名都保留。#
#-keepnames class * {
#    @**.anno.proguard.Keep *;
#}
-keepclassmembernames class * {
    @**.anno.proguard.Keep *;
}
##``````````````````````````````````````````````````
#
##__________________________________________________
## 任何被 **.anno.inject 包中的注解标注的，只要注解还在，就反保留。#
##（如果注解已经不在了，那这句是不起作用的！那：#
## TODO: 如何保留该注解？仅需保留被该注解标注的`变量/方法`即可）#
-keepclassmembers, allowoptimization, allowobfuscation class * {
    @**.anno.inject.** *;
}
##``````````````````````````````````````````````````
## 反保留构造方法 #
## 但 allowoptimization 会导致反射找不到构造方法 #
-keepclassmembers, allowobfuscation @**.anno.proguard.KeepC$$e class * {
    <init>(...);
}
# 延续到子类 #
-keepclassmembers, allowobfuscation class * implements @**.anno.proguard.KeepC$$e * {
    <init>(...);
}
-keepclassmembers, allowobfuscation class * extends @**.anno.proguard.KeepC$$e * {
    <init>(...);
}
##``````````````````````````````````````````````````
#
##__________________________________________________
## 新增（by 15/11/2023）：#
## 反保留（类型/接口）并延续到子类 #
-keep, allowoptimization, allowobfuscation @**.anno.proguard.Keep$$e @interface *   # 其实该注解不可能标注在`@interface`上 #
-keep, allowoptimization, allowobfuscation @**.anno.proguard.Keep$$e interface *
-keep, allowoptimization, allowobfuscation @**.anno.proguard.Keep$$e class *
# 延续到子类（接口）#
-keep, allowoptimization, allowobfuscation interface * extends @**.anno.proguard.Keep$$e *
-keep, allowoptimization, allowobfuscation class * implements @**.anno.proguard.Keep$$e *
-keep, allowoptimization, allowobfuscation class * extends @**.anno.proguard.Keep$$e *
##``````````````````````````````````````````````````
## 强保留（类型/接口）并延续到子类 #
-keep @**.anno.proguard.Keep$e @interface *   # 其实该注解不可能标注在`@interface`上 #
-keep @**.anno.proguard.Keep$e interface *
-keep @**.anno.proguard.Keep$e class *
# 延续到子类（接口）#
-keep interface * extends @**.anno.proguard.Keep$e *
-keep class * implements @**.anno.proguard.Keep$e *
-keep class * extends @**.anno.proguard.Keep$e *
##``````````````````````````````````````````````````
## 新增 END #
##__________________________________________________
##``````````````````````````````````````````````````
#
##__________________________________________________
## 反保留 #
-keep, allowoptimization, allowobfuscation @**.anno.proguard.Keep$$ @interface *
-keep, allowoptimization, allowobfuscation @**.anno.proguard.Keep$$ interface *
-keep, allowoptimization, allowobfuscation @**.anno.proguard.Keep$$ class *
-keepclassmembers, allowoptimization, allowobfuscation @interface * {
    @**.anno.proguard.Keep$$ *;
}
-keepclassmembers, allowoptimization, allowobfuscation interface * {
    @**.anno.proguard.Keep$$ *;
}
-keepclassmembers, allowoptimization, allowobfuscation class * {
    @**.anno.proguard.Keep$$ *;
}
##``````````````````````````````````````````````````
#
##__________________________________________________
## 强保留 #
-keep @**.anno.proguard.Keep$ @interface *
-keep @**.anno.proguard.Keep$ interface *
-keep @**.anno.proguard.Keep$ class *
-keepclassmembers @interface * {
    @**.anno.proguard.Keep$ *;
}
-keepclassmembers interface * {
    @**.anno.proguard.Keep$ *;
}
-keepclassmembers class * {
    @**.anno.proguard.Keep$ *;
}
##``````````````````````````````````````````````````
##<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<#####



#####>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>##
# 必须得配合 proguard-android-optimize.txt 使用，即 build.gradle 里面将 proguard-android.txt 改为这个。#
# 或者确保所有配置中没有这句：#
#-dontoptimize
#
# 并启用下面这些：#
##__________________________________________________
#-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
#-optimizationpasses 5
#-allowaccessmodification
#
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-verbose
#-dontpreverify
##``````````````````````````````````````````````````
-assumenosideeffects class * {
    @**.anno.proguard.Burden *** *(...);
}
# 相关字符串优化规则请见 @**.anno.proguard.Burden 源码文档 #
##<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<#####


#####>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>##
##### 以下是任何时候都必须保留的 #
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
##<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<#####
