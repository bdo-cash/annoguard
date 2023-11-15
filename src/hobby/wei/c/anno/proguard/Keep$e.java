/*
 * Copyright (C) 2023-present, Wei Chou(weichou2010@gmail.com)
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
 * <i>Strong Keep</i> class/interface name,
 * and carry over the function into subclass/subinterface.<br>
 * Scope: any types/classes.<br>
 * Basic rules see {@link Keep}.
 * <p>
 * <i>强保留</i> 类名（接口），并延续到子类（接口）。<br>
 * 使用范围：任何类型。<br>
 * 基本规则参见 {@link Keep}.
 *
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 15/11/2023
 */
@P$$
@Target(TYPE)
@Retention(CLASS)
public @interface Keep$e {
}
