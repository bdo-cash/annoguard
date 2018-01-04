/*
 * Copyright (C) 2017-present, Wei Chou(weichou2010@gmail.com)
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

package example.burden;

import hobby.wei.c.anno.proguard.Burden;

/**
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 25/04/2017
 * @see example.Synthetic
 */
public class L {
    /**
     * When be annotated with {@link Burden}, this method will be deleted on proguard task,
     * and the string literal parameters which are not referenced in any other kept fields will be deleted from
     * jar/dex (but this takes some skill, details see {@link Burden}).
     * Note: if the method's return-type is not "void", otherwise.
     * <p>
     * 当方法被{@link Burden}标注，则会在混淆任务中被删除，同时参数中如果用到了字符串[字面量]，
     * 但没有被其它任何未删除的变量引用，也会最终从jar/dex中被彻底删除（但这需要一些技巧，详见{@link Burden}文档）。
     * 注意：如果本方法的返回值不是"void", 则不会应用上述规则。
     *
     * @param o    log tag.
     * @param s    formatted string literal.
     * @param args format args.
     */
    @Burden
    public static void i(Object o, String s, Object... args) {
        checkArgs(args);
        Log.i(TAG(o), String.format(s, args));
    }

    // mock from some SDK
    public static class Log {
        static void i(String tag, String msg) {
            // print
        }
    }

    @Burden
    private static String TAG(Object o) {
        final String prefix = "prefix-";
        if (o instanceof String) return prefix + o;
        if (o instanceof Class) return prefix + ((Class<?>) o).getSimpleName();
        return prefix + o.getClass().getSimpleName();
    }

    @Burden
    public static S s(String s) {
        return S.obtain(s);
    }

    public static class S {
        public static S obtain(String s) {
            final S wrapper = null/*some codes wrap String s.*/;
            return wrapper;
        }
    }

    @Burden
    private static void checkArgs(Object... args) {
        if (args != null && args.length > 0) {
            for (Object o : args) {
                if (o instanceof String) throw new IllegalArgumentException("must use L.s(s) instead.");
            }
        }
    }
}
