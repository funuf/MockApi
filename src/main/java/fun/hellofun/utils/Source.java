package fun.hellofun.utils;

import com.alibaba.fastjson.JSON;
import fun.hellofun.bean.ImageTopic;
import fun.hellofun.jUtils.predicate.empty.Empty;
import okio.Okio;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 19时06分00秒 创建；<br>
 * 作用是：<b>内嵌数据源</b>；<br>
 */
public class Source {

    /**
     * 默认list接口的元素个数
     */
    private static int DEFAULT_LIMIT = 20;

    /**
     * 图片若干主题
     */
    private static List<String> IMAGE_ANIMAL = null;
    private static List<String> IMAGE_BOY = null;
    private static List<String> IMAGE_CAR = null;
    private static List<String> IMAGE_FOOD = null;
    private static List<String> IMAGE_GIRL = null;
    private static List<String> IMAGE_LANDSCAPE = null;
    private static List<String> IMAGE_PLANT = null;
    private static List<String> IMAGE_BANNER = null;

    public static class Image {

        public static String get(ImageTopic topic) {
            return list(1, topic).get(0);
        }

        public static List<String> list(Integer limit, ImageTopic topic) {
            return take(limit, pool(topic));
        }

        /**
         * 从数据池中取值
         */
        private static List<String> take(Integer limit, List<String> fromPool) {
            List<String> result = new ArrayList<>();
            if (limit == null) {
                limit = DEFAULT_LIMIT;
            }
            for (Integer i = 0; i < limit; i++) {
                result.add(fromPool.get(new Random(fromPool.size()).nextInt()));
            }
            return result;
        }

        /**
         * 确定数据池
         */
        private static List<String> pool(ImageTopic topic) {
            initImage();
            switch (topic) {
                case ANIMAL:
                    return IMAGE_ANIMAL;
                case BOY:
                    return IMAGE_BOY;
                case CAR:
                    return IMAGE_CAR;
                case BANNER:
                    return IMAGE_BANNER;
                case FOOD:
                    return IMAGE_FOOD;
                case GIRL:
                    return IMAGE_GIRL;
                case PLANT:
                    return IMAGE_PLANT;
                case LANDSCAPE:
                    return IMAGE_LANDSCAPE;
                case ALL:
                default:
                    return new ArrayList<String>() {{
                        addAll(IMAGE_ANIMAL);
                        addAll(IMAGE_BOY);
                        addAll(IMAGE_CAR);
                        addAll(IMAGE_FOOD);
                        addAll(IMAGE_GIRL);
                        addAll(IMAGE_LANDSCAPE);
                        addAll(IMAGE_PLANT);
                        addAll(IMAGE_BANNER);
                    }};
            }
        }
    }

    private static void initImage() {
        if (Empty.no(IMAGE_ANIMAL, IMAGE_BOY, IMAGE_CAR, IMAGE_FOOD, IMAGE_GIRL, IMAGE_LANDSCAPE, IMAGE_PLANT, IMAGE_BANNER)) {
            return;
        }
        try {
            // 图片
            IMAGE_ANIMAL = JSON.parseArray(Okio.buffer(Okio.source(ResourceUtils.getFile("image/animal.json"))).readUtf8(), String.class);
            IMAGE_BOY = JSON.parseArray(Okio.buffer(Okio.source(ResourceUtils.getFile("image/boy.json"))).readUtf8(), String.class);
            IMAGE_CAR = JSON.parseArray(Okio.buffer(Okio.source(ResourceUtils.getFile("image/car.json"))).readUtf8(), String.class);
            IMAGE_FOOD = JSON.parseArray(Okio.buffer(Okio.source(ResourceUtils.getFile("image/food.json"))).readUtf8(), String.class);
            IMAGE_GIRL = JSON.parseArray(Okio.buffer(Okio.source(ResourceUtils.getFile("image/girl.json"))).readUtf8(), String.class);
            IMAGE_LANDSCAPE = JSON.parseArray(Okio.buffer(Okio.source(ResourceUtils.getFile("image/landscape.json"))).readUtf8(), String.class);
            IMAGE_PLANT = JSON.parseArray(Okio.buffer(Okio.source(ResourceUtils.getFile("image/plant.json"))).readUtf8(), String.class);
            IMAGE_BANNER = JSON.parseArray(Okio.buffer(Okio.source(ResourceUtils.getFile("image/banner.json"))).readUtf8(), String.class);
            // TODO: 2020/3/16 富文本  文本  视频
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
