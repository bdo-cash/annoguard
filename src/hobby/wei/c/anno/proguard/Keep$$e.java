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
 * <i><font color=red>Reverse</font> Keep</i> class/interface name,
 * and carry over the function into subclass/subinterface.<br>
 * Scope: any types/classes.<br>
 * Basic rules see {@link Keep}.
 * <p>
 * <i><font color=red>反</font>保留</i> 类名（接口），并延续到子类（接口）。<br>
 * 使用范围：任何类型。<br>
 * 基本规则参见 {@link Keep}.
 *
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 15/11/2023
 * @deprecated <br>
 * Careful: because only the reflection calls usually need kept force for not deleted,
 * but if renamed, the names could be found? need to <font color=red>guarantee by yourself</font>.<br>
 * 慎用：因为通常只有被反射调用的才需要显式保留不删除，但是重命名了反射能找到？需要<font color=red>自行保证</font>。
 */
@P$$
@Deprecated
@Target(TYPE)
@Retention(CLASS)
public @interface Keep$$e {
}
