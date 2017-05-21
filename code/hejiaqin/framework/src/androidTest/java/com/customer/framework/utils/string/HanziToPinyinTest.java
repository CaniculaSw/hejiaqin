package com.customer.framework.utils.string;

import android.content.Context;
import android.test.AndroidTestCase;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class HanziToPinyinTest extends AndroidTestCase {

    public void testHanziToPinyin() throws Exception {
        Context context = getContext();

        assertNotNull(context);

        HanziToPinyin hanziToPinyin = HanziToPinyin.getInstance(context);
        assertNotNull(hanziToPinyin);

        assertEquals("nihao", hanziToPinyin.hanziToPinyin("你好"));

        assertEquals("luzhishen", hanziToPinyin.hanziToPinyin("鲁智深"));

        assertEquals("yase", hanziToPinyin.hanziToPinyin("亚瑟"));

        assertEquals("yangjian", hanziToPinyin.hanziToPinyin("杨戬"));

        assertEquals("diaochan", hanziToPinyin.hanziToPinyin("貂蝉"));

        assertEquals("zhangfei", hanziToPinyin.hanziToPinyin("张飞"));

        assertNotNull(hanziToPinyin.hanziHeadToPinyin("你好"));
        assertNotNull(hanziToPinyin.hanziToPinyin('你'));
    }
}
