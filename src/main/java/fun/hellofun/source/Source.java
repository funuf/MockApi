package fun.hellofun.source;

import fun.hellofun.command.topic.ImageTopic;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.jUtils.predicate.empty.Empty;
import io.reactivex.rxjava3.core.Observable;
import okio.Okio;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 19时06分00秒 创建；<br>
 * 作用是：<b>内嵌数据源</b>；<br>
 */
public abstract class Source<T> {

    /**
     * 默认list接口的元素个数
     */
    public static int DEFAULT_LIMIT = 20;

    /**
     * 文件默认父路径
     */
    public static String DEFAULT_FILE_PATH = null;

    private static final SourceImage IMAGE = new SourceImage();

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
    public static List<String> images(List<ImageTopic> topics, Integer limit) {
        if (Empty.yes(topics)) {
            return IMAGE.list(null, limit);
        } else if (topics.size() == 1) {
            return IMAGE.list(topics.get(0), limit);
        } else {
            List<String> result = new ArrayList<>();
            do {
                result.addAll(IMAGE.list(topics.get(new Random().nextInt(topics.size())), new Random().nextInt(3)));
            } while (limit > result.size());
            return result.subList(0, limit);
        }
    }


    /**
     * 类路径文件中的内容（字符串形式）
     */
    protected String classpathFileString(String path) {
        try {
            return Okio.buffer(Okio.source(new ClassPathResource(path).getInputStream())).readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
