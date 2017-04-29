/*
 * Copyright (C) 2016-present, Wei Chou (weichou2010@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hobby.wei.c.anno.proguard;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * 仅用于本注解<u>直接作用</u>的方法，表示如果删除该方法不会引起副作用的话，则可以删除。<br>
 * 使用范围：任何位置。<br>
 * 基本规则参见 {@link Keep}.
 * <p>
 * 注意事项：<br>
 * 如果希望在删除某方法的时候连同字符串也优化掉，需要有一些策略，简单讲：<br>
 * <br>
 * 有字符串和其它[非直接字符串]连接的不行：<br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "混淆测试:" + L.class.getName());</font></code>
 * <br>
 * 参数为数组或(String... arr)的也不行：<br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "混淆测试:", "L.i", L.class.getName());</font></code>
 * <br>
 * 这两行的字符串可被优化掉：<br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "混淆测试, 没有字符串连接");</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "混淆测试, " + "~有字符串连接");</font></code>
 * <br>
 * 如果字符串不包含在数组或(Object... arr)中才可被优化掉：<br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "uid:%s, name:%s", uid, name);</font></code>
 * <br>
 * 那么最后这条可行的办法是这么写的：<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>public static void i(Object o, String s, Object... args) {</font></code><br>&nbsp;&nbsp;&nbsp;&nbsp;
 * <code><font color=blue>Log.i(contextName(o), String.format(s, args));</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>}</font></code>
 *
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 19/02/2016
 */
@P$$
@Retention(CLASS)
@Target(METHOD)
public @interface Burden {
}
