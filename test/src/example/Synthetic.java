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
package example;

import com.google.gson.Gson;
import example.burden.L;
import example.keep$$.VO;
import hobby.wei.c.anno.proguard.Keep$;

/**
 * @author Wei.Chou(weichou2010@gmail.com)
 * @version 1.0, 25/04/2017
 */
public class Synthetic {
    // this field will be deleted on proguard task, because of "L.i()" will be deleted.
    // 本字段在Proguard任务中将被删除，因为"L.i()"将被删除。
    private static final String TAG = Synthetic.class.getSimpleName();

    // this method will not change at all, because of Strong("$") Keep.
    // 本方法将保持不变，因为被强（"$"）保留。
    @Keep$
    public static void main(String[] args) {
        new Synthetic().correctUsage(1234567, "Bob Dylan", new Object());
    }

    // this method will be renamed on proguard task.
    // 本方法在Proguard任务中将被重命名。
    void correctUsage(int uid, String name, Object o) {
        // the following two lines with string literals "uid:%s, name:%s, others:%s. "
        // and "abcdefg" will be deleted on proguard task from jar/dex.
        // 下面两行以及字符串字面量"uid:%s, name:%s, others:%s. "和"abcdefg"会在Proguard任务中从jar/dex彻底删除。
        final String stringLiteral = "abcdefg";
        L.i(TAG, "uid:%s, name:%s, others:%s. " + stringLiteral, uid, L.s(name), o);

        // 这里引用了VO, 所有类型VO至少被反保留。
        new Gson().toJson(new VO());
    }
}
