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

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * <i>弱保留</i> 本注解<u>直接作用</u>的各种名称。<br/>
 * 使用范围：任何位置。<br/>
 * <p/>
 * 名词解释：<br/>&nbsp;&nbsp;
 * <i>强</i>保留：既不被重命名，又不被删除；<br/>&nbsp;&nbsp;
 * <i>弱</i>保留：只确保不被重命名，不保证不被删除；<br/>&nbsp;&nbsp;
 * <font color=red><i>反</i></font>保留：不保证不被重命名，但确保不被删除；<br/>&nbsp;&nbsp;
 * <i>不</i>保留：排除到以上保留名单之外。<br/>
 * 优先级：<br/>&nbsp;&nbsp;
 * 由高到低依次为强、反、弱、不。被延续到子类（接口）的，子类享有相同优先级。<br/>&nbsp;&nbsp;
 * 任何位置，若被不同优先级的注解同时标注，则会应用较高优先级。<br/>
 * 约定：<br/>&nbsp;&nbsp;
 * 含有<font color=red><i>$$</i></font> 字符的为<i>反保留</i>；<br/>&nbsp;&nbsp;
 * 含有<font color=red><i>$</i></font> 字符（除反保留外）的为<i>强保留</i>；<br/>&nbsp;&nbsp;
 * 不含以上标识字符的为<i>弱保留</i>；<br/>&nbsp;&nbsp;
 * 没有用于<i>不保留</i> 的标签，即：不加任何标注则为不保留；<br/>&nbsp;&nbsp;
 * 另：以<font color=red><i>e</i></font> 结尾的表示将其前面名称表达的功能<i>延续到子类（接口）</i>。
 * 需要注意的是：只有父类（接口）存在的情况下，才能延续到子类。即：首先需要将父类强保留或反保留。<br/>
 * 其它：<br/>&nbsp;&nbsp;
 * 对于<i>inject</i> 包中的任何注解，只要该注解没有被优化掉，就会对其直接作用的内容进行反保留。
 * <p/>&nbsp;&nbsp;
 *
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 11/12/2015
 */
@P$$
@Retention(CLASS)
@Target({PACKAGE, TYPE, ANNOTATION_TYPE, CONSTRUCTOR, METHOD, FIELD})
public @interface Keep {
}
