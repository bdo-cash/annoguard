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
 * <i>弱保留</i> 内部所有非static方法。<br/>
 * 注意：这里没有<i>强保留</i> 所有非static方法的实现，以防止不必要的扩大keep范围。
 * 因为对于非public方法，几乎是不应该keep的。<br/>
 * 通常需要keep的只是public，可用{@link KeepMp$}.<br/>
 * 若仍不能满足需求，则可针对单个方法使用{@link Keep$}.<br/>
 * 使用范围：任何类型。<br/>
 * 基本规则参见 {@link Keep}.
 *
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 24/02/2016
 */
@P$$
@Target(TYPE)
@Retention(CLASS)
public @interface KeepM {
}
