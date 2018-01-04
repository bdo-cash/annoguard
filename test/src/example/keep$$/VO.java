package example.keep$$;

import com.google.gson.annotations.SerializedName;
import hobby.wei.c.anno.proguard.Keep$$;

// 本类被Synthetic引用了，因此至少会被反保留。

/**
 * @author Wei Chou(weichou2010@gmail.com)
 * @version 1.0, 14/05/2017
 * @see example.Synthetic
 */
public class VO {
    // 字段"stages"可能会被重命名（混淆成随意的任何名称），但一定不会被删除。
    @Keep$$
    // for gson. 使用gson进行（反）序列化时，
    // 会使用这里指定的任何字段名（如："sts"），而不管字段"stages"会被混淆成什么。
    @SerializedName("sts")
    public Stage[] stages = new Stage[0];

    // 由于没有定义作用于这里的反保留内部全部字段的@Keep$$, 所以
    // 必须在下面给每个字段进行标注。
    // 这是故意设计的，强制缩小作用范围。

    // 不管这里也没有对本类进行Keep标注，意味着：如果有其它任何位置引用它，而且没有被remove掉，
    // 则内部的@Keep$$才会被apply.
    // 注意上面的"stages"字段引用了本类，而且被反保留了，意味着本类将会被keep, 效果上等同于反保留@Keep$$.
    // 所有没有明确标注@KeepXxx的（即：Non Keep/不保留），如果该类型的任一字段被保留了，则效果上该类型名被反保留。
    public static class Stage {
        Stage() {
            time = 0;
            steps = 0;
            calories = 0;
        }

        public Stage(int time, int steps, float calories) {
            this.time = time;
            this.steps = steps;
            this.calories = calories;
        }

        @Keep$$ // 同上
        @SerializedName("t") // for gson
        public final int time;
        @Keep$$ // 同上
        @SerializedName("s") // for gson
        public final int steps;
        @Keep$$ // 同上
        @SerializedName("c") // for gson
        public final float calories;
    }
}