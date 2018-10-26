package com.flym.fhzk.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morphine on 2017/8/23.
 */

public class TestDataUtil {


    public static List<String> getTestData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("测试数据" + i);
        }
        return strings;
    }

    public static List<String> getBannerTestData() {
        List<String> strings = new ArrayList<>();
        strings.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504603053&di=e17209a147cd4cf21aadae0d04fc73a4&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201204%2F20120412123929822.jpg");
        strings.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504008354949&di=90bca070e4e3a34e36f6c73332bb7d87&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F140331%2F234990-1403310S34966.jpg");
        strings.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504008371472&di=d0734a99ad6a7ad136e5e073dc023a95&imgtype=0&src=http%3A%2F%2Fimg.tuku.cn%2Ffile_big%2F201503%2Fbaec4ab4a2984da89fb108e3bf289361.jpg");
        return strings;
    }

}
