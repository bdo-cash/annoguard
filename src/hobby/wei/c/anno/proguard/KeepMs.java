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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * <i>Weak Keep</i> all static methods in the class.<br>
 * Scope: any types/classes.<br>
 * Basic rules see {@link Keep}.<br>
 * Note: there is no implementations of <i>Strong Keep</i> for all static methods,
 * in order to prevent unnecessary expand the scope of keep.
 * because:
 * <ul>
 * <li>for the public methods, it's really unnecessary.
 * <li>usually need kept only for public, can use {@link KeepMps$} instead.
 * </ul>
 * If still cannot meet the demand, can use {@link Keep$} aim at methods one by one.
 * <p>
 * <i>弱保留</i> 内部所有static方法。<br>
 * 使用范围：任何类型。<br>
 * 基本规则参见 {@link Keep}.<br>
 * 注意：这里没有<i>强保留</i> 所有static方法的实现，以防止不必要的扩大keep范围。因为：
 * <ul>
 * <li>对于非public方法，几乎是不应该keep的。
 * <li>通常需要keep的只是public，可用{@link KeepMps$}.
 * </ul>
 * 若仍不能满足需求，则可针对单个方法使用{@link Keep$}.
 *
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 24/02/2016
 */
@P$$
@Target(TYPE)
@Retention(CLASS)
public @interface KeepMs {
}
