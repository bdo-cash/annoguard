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
 * Only <u>act directly</u> on methods which annotated by this annotation，means
 * if delete the methods will not cause side effects，then delete them.<br>
 * Scope: any methods.<br>
 * Basic rules see {@link Keep}.
 * <p>
 * Matters need attention:<br>
 * If hope optimize string literals which are used as parameters when delete the method (mean
 * the string literals of parameters which are not referenced in any other kept fields will be deleted from
 * jar/dex), would need some strategy. In short:<br>
 * <br>
 * 1. string literals join with string fields would not work correctly:<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "proguard test:" + L.class.getName());</font></code>
 * <br>
 * 2. args use array "String[]" or "(String...)" would not work correctly:<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "proguard test:", new String[]{"L.i", L.class.getName()});</font></code>
 * <br>
 * 3. the following would be ok:<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "proguard test, no join.");</font></code><br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "proguard test, " + "~有字符串连接");</font></code>
 * <br>
 * 4. string literals not in array is ok:<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "uid:%s, name:%s", uid, name);</font></code>
 * <br>
 * In conclusion, the feasible solution is like this:<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>public static void i(Object o, String s, Object... args) {</font></code><br>&nbsp;&nbsp;&nbsp;&nbsp;
 * <code><font color=blue>Log.i(tag(o), String.format(s, args));</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>}</font></code><br>
 * Mind the {@code args} mustn't contains any string literals, a full solution see <a target="_blank"
 * href="http://github.com/WeiChou/Wei.Lib2A/blob/master/Wei.Lib2A/src/hobby/wei/c/L.java#L121">
 * L.java</a>.<br>
 * <p>
 * <p>
 * 仅用于本注解<u>直接作用</u>的方法，表示如果删除该方法不会引起副作用的话，则可以删除。<br>
 * 使用范围：任何方法。<br>
 * 基本规则参见 {@link Keep}.
 * <p>
 * 注意事项：<br>
 * 如果希望在删除方法的同时连同作为参数的字符串常量也一并优化掉（即：如果字符串常量没有被其它任何未删除的变量引用，
 * 希望最终从jar/dex中被彻底删除），需要一些策略。简单讲：<br>
 * <br>
 * 1. 有字符串字面量和[非字面量]连接的不行：<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "混淆测试:" + L.class.getName());</font></code>
 * <br>
 * 2. 参数为数组或(String... arr)的也不行：<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "混淆测试:", new String[]{"L.i", L.class.getName()});</font></code>
 * <br>
 * 3. 这两行的字符串字面量可被优化掉：<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "混淆测试, 没有字符串连接");</font></code><br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "混淆测试, " + "~有字符串连接");</font></code>
 * <br>
 * 4. 如果字符串字面量不包含在数组或(Object... arr)中才可被优化掉：<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>L.i(TAG, "uid:%s, name:%s", uid, name);</font></code>
 * <br>
 * 那么，最后这个可行的方案是这样的：<br>&nbsp;&nbsp;
 * <code><font color=red>@Burden</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>public static void i(Object o, String s, Object... args) {</font></code><br>&nbsp;&nbsp;&nbsp;&nbsp;
 * <code><font color=blue>Log.i(tag(o), String.format(s, args));</font></code><br>&nbsp;&nbsp;
 * <code><font color=blue>}</font></code><br>
 * 注意参数{@code args}里面不应该包含字符串字面量，完整的方案见<a target="_blank"
 * href="http://github.com/WeiChou/Wei.Lib2A/blob/master/Wei.Lib2A/src/hobby/wei/c/L.java#L121">
 * L.java</a>.<br>
 *
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 19/02/2016
 */
@P$$
@Target(METHOD)
@Retention(CLASS)
public @interface Burden {
}
