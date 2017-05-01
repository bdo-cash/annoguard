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

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * <i>Weak Keep</i> the names this annotation <u>act directly</u> on. What it means, see below.<br>
 * Scope: anywhere possible.<br>
 * <p>
 * Glossary:
 * <ul>
 * <li><i>Strong</i> Keep: neither be renamed, nor be deleted;
 * <li><i>Weak</i> Keep: guarantee not be renamed, but without guarantee not be deleted;
 * <li><font color=red><i>Reverse</i></font> Keep: without guarantee not be renamed, but guarantee not be deleted;
 * <li><i>Non</i> Keep: out of the rules above. Means apply proguard default.
 * </ul>
 * Priority:
 * <ul>
 * From high to low in the order: <i>Strong</i>, <i>Weak</i>, <i>Reverse</i>, <i>Non</i>.
 * When be carried over into subclass (subinterface), subclass got the same priority.<br>
 * Anywhere, if one name be annotated by some different priority but same function of "@KeepXx"s,
 * the higher one will be applied.
 * </ul>
 * Appoint:
 * <ul>
 * <li>Contain chars <font color=red><i>$$</i></font> means <i>Reverse Keep</i>;
 * <li>Contain char <font color=red><i>$</i></font> means <i>Strong Keep</i> (exclude Reverse Keep);
 * <li>Does not contain chars above means <i>Weak Keep</i>;
 * <li>Does not exist annotation definition of <i>Non Keep</i>, means no need for annotate names;
 * <li>End with <font color=red><i>e</i></font> means the function indicated by the words before "e"
 * will be <i>carried over into subclass (subinterface)</i>.
 * Note that only when superclass (superinterface) did not deleted on proguard task, subclass (subinterface) may kept.
 * Means must Strong/Reverse Keep superclass (superinterface) first.
 * </ul>
 * Else:
 * <ul>
 * Any annotation in package <i>{@code **.anno.inject}</i>, if not be deleted on proguard task,
 * will apply Reverse Keep on the names it act directly on.
 * </ul>
 * <p>
 * <i>弱保留</i> 本注解<u>直接作用</u>的各种名称。<br>
 * 使用范围：任何可用的位置。<br>
 * <p>
 * 名词解释：
 * <ul>
 * <li><i>强</i>保留：既不被重命名，又不被删除；
 * <li><i>弱</i>保留：只确保不被重命名，不保证不被删除；
 * <li><font color=red><i>反</i></font>保留：不保证不被重命名，但确保不被删除；
 * <li><i>不</i>保留：排除到以上保留名单之外。即应用混淆。
 * </ul>
 * 优先级：
 * <ul>
 * 由高到低依次为强、反、弱、不。被延续到子类（接口）的，子类享有相同优先级。<br>
 * 任何位置，若被不同优先级的注解同时标注，则会应用较高优先级。
 * </ul>
 * 约定：
 * <ul>
 * <li>含有<font color=red><i>$$</i></font> 字符的为<i>反保留</i>；
 * <li>含有<font color=red><i>$</i></font> 字符的为<i>强保留</i>（反保留除外）；
 * <li>不含以上标识字符的为<i>弱保留</i>；
 * <li>没有用于<i>不保留</i> 的标签，即：不加任何标注则为不保留；
 * <li>另：以<font color=red><i>e</i></font> 结尾的表示将其前面名称表达的功能<i>延续到子类（接口）</i>。
 * 需要注意的是：只有父类（接口）没有被混淆删除的情况下，才能延续到子类。即：首先需要将父类强保留或反保留。
 * </ul>
 * 其它：
 * <ul>
 * 对于包<i>{@code **.anno.inject}</i> 中的任何注解，只要该注解没有被优化掉，就会对其直接作用的内容进行反保留。
 * </ul>
 *
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 11/12/2015
 */
@P$$
@Retention(CLASS)
@Target({PACKAGE, TYPE, ANNOTATION_TYPE, CONSTRUCTOR, METHOD, FIELD})
public @interface Keep {
}
