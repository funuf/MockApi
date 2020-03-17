package fun.hellofun.source;

import fun.hellofun.topic.ImageTopic;
import fun.hellofun.topic.Topic;

import java.util.List;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 19时06分00秒 创建；<br>
 * 作用是：<b>内嵌数据源</b>；<br>
 */
public abstract class Source<T> {

    private static final SourceImage IMAGE = new SourceImage();

    /**
     * 默认list接口的元素个数
     */
    protected int DEFAULT_LIMIT = 20;

    /**
     * 获取单个元素
     */
    protected abstract T get(Topic topic);

    /**
     * 获取多个元素
     */
    protected abstract List<T> list(Topic topic, Integer limit);

    /**
     * 单张图片
     */
    public static String image(ImageTopic imageTopic) {
        return IMAGE.get(imageTopic);
    }

    /**
     * 多张图片
     */
    public static List<String> images(ImageTopic topic, Integer limit) {
        return IMAGE.list(topic, limit);
    }

}
