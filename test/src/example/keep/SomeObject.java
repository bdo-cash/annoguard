/*
 * Copyright (C) 2017-present, Wei.Chou(weichou2010@gmail.com)
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

package example.keep;

import hobby.wei.c.anno.proguard.*;

/**
 * @author Wei.Chou(weichou2010@gmail.com)
 * @version 1.0, 30/04/2017
 */
@KeepVps$ // 强保留public static 字段（常/变量）。
@KeepV
@KeepMps$
@KeepM
@KeepMp$e
public class SomeObject<T> {
    // 本字段在@KeepVps$的作用范围内，因此在Proguard任务后将被保留（即使并不被任何其他任何位置引用）。
    // 由于尾部使用了"$"字符，意味着，本字段不仅不被删除，而且不被重命名（即：在Proguard后将仍然保持下面原样不变）。
    // 但要注意：本字段的值并不在范围内，这取决于本类名是否被Keep.
    public static final String TAG = SomeObject.class.getSimpleName();

    // 本字段不会被保留，因为不是public的，如果希望被保留，可以
    // 1. 在类名前增加@KeepVs, 但这并不可靠，如果确实没有被任何其他位置引用，那么还是会被删除（如果没被删除，则不会被重命名，
    // 因为@KeepVs的优先级是"弱"）；
    // 2. 可以直接在本字段前面加@Keep$（一定被保留）, 或@Keep（效果同1），或@Keep$$（自己理解吧）。
    static int x = 1234;

    // 在@KeepV作用范围内，区别于上面的"x"（需要@KeepVs）. 但同样不可靠，原因同上。
    // 注意：并不在@KeepVpxx的作用范围内。
    private Long maxValue = Long.MAX_VALUE;

    // 在@KeepV作用范围内，但同样不可靠，原因同上。
    // 注意：与上面"maxValue"不同的是，本字段在@KeepVp$和@KeepVp$e的作用范围内。
    public T someObj;


    // 以下所有方法的应用效果同上面的字段，因为@KeepMps$和@KeepM类似于@KeepVps$和@KeepV.

    // 下面的定义可能不符合代码规范，仅作演示。

    public static String TAG() {
        return TAG;
    }

    static int x() {
        return x;
    }

    private Long getMaxValue() {
        return maxValue;
    }

    // 由于受@KeepMp$e的作用，会被强保留，并延续到子类。
    public T someObj() {
        return someObj;
    }
}

class SOe<T> extends SomeObject<T> {
    // 受@KeepMp$e的作用，延续强保留到本子类方法。
    // 不过如果本类没有被任何引用保留（见不保留的定义，及等价特性），则仍然会被删除。但这是合理的。
    // 如果确实需要保留本类，同样的方法：直接给类名标注@Keep$或@Keep$$.
    @Override
    public T someObj() {
        return super.someObj();
    }
}
