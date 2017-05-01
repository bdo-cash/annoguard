#Annoguard

###This document describe how to use a `New Syntax to Config Proguard` base on `@Annotation`

* Principle and goals see [source code](http://github.com/WeiChou/AnnoProguard/blob/master/libs/annotations.pro).

------------------------------------------------------------------------------------------------
---

###Glossary, Rules Definition and Explain

* [<b>`Strong`</b>]()`Keep`: neither be renamed, nor be deleted;
* [<b>`Weak`</b>]()`Keep`: guarantee not be renamed, but without guarantee not be deleted;
* [<b>`Reverse`</b>]()`Keep`: without guarantee not be renamed, but guarantee not be deleted;
* [<b>`Non`</b>]()`Keep`: out of the rules above. Means apply proguard default.


* Priority
    * From high to low in the order: Strong, Weak, Reverse, Non. When be carried over into subclass/subinterface, subclass/subinterface got the same priority.
    * Anywhere, if one name be annotated by some different priority but same function of "@KeepXxx"s, the higher one will be applied.
    
* Appoint
    * Contain chars [<b>`$$`</b>]() means `Reverse Keep`. e.g: [@Keep$$](http://github.com/WeiChou/AnnoProguard/blob/master/src/hobby/wei/c/anno/proguard/Keep$$.java);
    * Contain char [<b>`$`</b>]() means `Strong Keep` (exclude Reverse Keep). e.g: [@Keep$](http://github.com/WeiChou/AnnoProguard/blob/master/src/hobby/wei/c/anno/proguard/Keep$.java);
    * Does not contain chars above means `Weak Keep`;
    * Does not exist annotation definition of `Non Keep`, means no need for annotate names;
    * End with [<b>`e`</b>]() means the function indicated by the words before "e" will be carried over into subclass/subinterface.
    e.g: [@KeepMp$e](http://github.com/WeiChou/AnnoProguard/blob/master/src/hobby/wei/c/anno/proguard/KeepMp$e.java);
     
         Note that only when superclass/superinterface did not deleted on proguard task, subclass/subinterface may kept.
         Means must Strong/Reverse Keep superclass/superinterface first.
        
* Symbols in the end of `@KeepXxx`, means the target places the annotation act on.
    * [<b>`V`</b>]()：var, act on variables/fields;
    * [<b>`M`</b>]()：method, act on methods/functions;
    * [<b>`C`</b>]()：constructor, act on constructors;
    * [<b>`p`</b>]()：public(var/method, depend on another symbol is `V` or `M`). Does not contains means ignore `public/private...`;
    * [<b>`s`</b>]()：static(var/method, depend on another symbol is `V` or `M`). Does not contains means `non static`.
    
        More details see each doc of `@KeepXxx`.

* Else
    * Any annotation in package [\**.anno.inject](http://github.com/WeiChou/Wei.Lib2A/blob/master/Wei.Lib2A/src/hobby/wei/c/anno/inject),
    if not be deleted on proguard task, will apply Reverse Keep on the names it act directly on.


###概念、规则定义及解释

* [<b>`强`</b>]()`保留`：既不被重命名，又不被删除；
* [<b>`弱`</b>]()`保留`：只确保不被重命名，不保证不被删除；
* [<b>`反`</b>]()`保留`：不保证不被重命名，但确保不被删除；
* [<b>`不`</b>]()`保留`：排除到以上保留名单之外。即应用混淆。


* 优先级
    * 由高到低依次为强、反、弱、不。被延续到子类（接口）的，子类享有相同优先级；
    * 任何位置，若被不同优先级的注解同时标注，则会应用较高优先级。
    
* 符号约定
    * 含有[<b>`$$`</b>]()字符的为`反保留`。如：[@Keep$$](http://github.com/WeiChou/AnnoProguard/blob/master/src/hobby/wei/c/anno/proguard/Keep$$.java)；
    * 含有[<b>`$`</b>]()字符的为`强保留`(反保留除外)。如：[@Keep$](http://github.com/WeiChou/AnnoProguard/blob/master/src/hobby/wei/c/anno/proguard/Keep$.java)；
    * 不含以上标识字符的为`弱保留`；
    * 没有用于`不保留`的标签，即：不加任何标注则为不保留；
    * 另：以[<b>`e`</b>]()结尾的表示将其前面名称表达的功能延续到子类。如：[@KeepMp$e](http://github.com/WeiChou/AnnoProguard/blob/master/src/hobby/wei/c/anno/proguard/KeepMp$e.java)。
    
        需要注意的是：只有父类（接口）没有被混淆删除的情况下，才能延续到子类。
        即：首先需要将父类强保留或反保留。
        
* 类名`@KeepXxx`后面的其它字母符号，表示其作用的目标位置。
    * [<b>`V`</b>](): var, 作用于成员变量或常量；
    * [<b>`M`</b>](): method, 作用于方法或函数；
    * [<b>`C`</b>](): constructor, 作用于构造方法；
    * [<b>`p`</b>](): public(变量或方法，取决于组合的是`V`还是`M`)。没有本字符则表示忽略访问权限修饰；
    * [<b>`s`</b>](): static(变量或方法，取决于组合的是`V`还是`M`)。没有则表示非static的。
    
        细节请参见各注解的源码文档。

* 其它：
    * 对于包 [\**.anno.inject](http://github.com/WeiChou/Wei.Lib2A/blob/master/Wei.Lib2A/src/hobby/wei/c/anno/inject) 中的任何注解，只要该注解没有被优化掉，就会对其直接作用的内容进行反保留。

------------------------------------------------------------------------------------------------
---


###使用方法：

####1. 导入annoguard libs:

import from jcenter repository use gradle script:

```Gradle

    repositories {
        jcenter()
    }
    
    dependencies {
        // some code else ...
        compile 'hobby.wei.c.anno:annoguard:1.0.0'
    }
    
    task genProguardConfigFile(type: UnZip ? ) {
        // waiting for me please.
    }
```
or else from bintray [ ![Download](https://api.bintray.com/packages/hobby/maven/annoguard/images/download.svg) ](https://bintray.com/hobby/maven/annoguard/_latestVersion)

####2. 在主module根目录下的混淆配置文件 `proguard-rules.pro`（或其他名称）的开头加入下列代码：

```Bash

    #gradle配置中最好启用优化。即引用'proguard-android-optimize.txt'
    #proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    
    -include libs/annotations.pro
    
    #如果是以jar包的方式导入的而不是作为库项目，那么需要下面两行：#
    -dontwarn hobby.wei.c.**
    -libraryjars libs/annoguard-1.0.0.jar
    
    -keep class com.google.gson.stream.** { *; }
```

####3. 根据需求在代码中添加`@KeepXxx`注解。

暂只给出代码中已有的部分示例链接，有任何问题或建议可 [联系作者](http://github.com/WeiChou/Wei.Lib2A/blob/master/README.md#联系作者)。

* [`@KeepVp$e`](http://github.com/WeiChou/AnnoProguard/blob/master/src/hobby/wei/c/anno/proguard/KeepVp$e.java)
和 [`@KeepMp$e`](http://github.com/WeiChou/AnnoProguard/blob/master/src/hobby/wei/c/anno/proguard/KeepMp$e.java)
的应用示例：[AbsJson](http://github.com/WeiChou/Wei.Lib2A/blob/master/Wei.Lib2A/src/hobby/wei/c/data/abs/AbsJson.java#L45)；

* [`@Burden`](http://github.com/WeiChou/AnnoProguard/blob/master/src/hobby/wei/c/anno/proguard/Burden.java)
应用示例：[ L ](http://github.com/WeiChou/Wei.Lib2A/blob/master/Wei.Lib2A/src/hobby/wei/c/L.java#L121)；

* 更多应用示例见：[Synthetic](http://github.com/WeiChou/AnnoProguard/blob/master/src/test/example/Synthetic.java)
