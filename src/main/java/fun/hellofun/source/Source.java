package fun.hellofun.source;

import fun.hellofun.command.ItemType;
import fun.hellofun.command.Limit;
import fun.hellofun.command.TimeFormat;
import fun.hellofun.command.topic.ImageTopic;
import fun.hellofun.command.topic.TextTopic;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.command.topic.VideoTopic;
import fun.hellofun.jUtils.predicate.empty.Empty;
import okio.Okio;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    public static String DEFAULT_FILE_PATH = "";

    private static final SourceText TEXT = new SourceText();

    private static final SourceImage IMAGE = new SourceImage();

    private static final SourceVideo VIDEO = new SourceVideo();

    /**
     * 获取单个元素
     */
    protected abstract T get(Topic topic);

    /**
     * 获取多个元素
     */
    protected abstract List<T> list(Topic topic, Integer count);

    /**
     * 单个文本
     */
    public static String text(TextTopic textTopic) {
        return TEXT.get(textTopic);
    }

    /**
     * 单张图片
     */
    public static String image(ImageTopic imageTopic) {
        return IMAGE.get(imageTopic);
    }

    /**
     * 单个视频
     */
    public static String video(VideoTopic videoTopic) {
        return VIDEO.get(videoTopic);
    }

    /**
     * 取多个 文本、图片、视频
     */
    public static <T extends Topic> List<String> take(ItemType type, List<T> topics, Integer count) {
        Source source = null;
        if (type == ItemType.IMAGE) {
            source = IMAGE;
        } else if (type == ItemType.TEXT) {
            source = TEXT;
        } else if (type == ItemType.VIDEO) {
            source = VIDEO;
        }
        if (Empty.yes(topics)) {
            return source.list(null, count);
        } else if (topics.size() == 1) {
            return source.list(topics.get(0), count);
        } else {
            List<String> result = new ArrayList<>();
            do {
                result.addAll(source.list(topics.get(new Random().nextInt(topics.size())), new Random().nextInt(3)));
            } while (count > result.size());
            return result.subList(0, count);
        }
    }

    /**
     * 单个整数
     */
    public static int integer(Limit limit) {
        if (limit == null) {
            limit = Limit.DEFAULT_LIMIT;
        }
        return limit.getLowerLimit().intValue()
                + new Random().nextInt(limit.getUpperLimit().intValue() - limit.getLowerLimit().intValue());
    }

    /**
     * 多个整数
     */
    public static List<Integer> integers(Limit limit, Integer count) {
        return new ArrayList<Integer>() {{
            for (Integer i = 0; i < count; i++) {
                add(integer(limit));
            }
        }};
    }

    /**
     * 单个浮点数
     */
    public static double floatt(Limit limit) {
        if (limit == null) {
            limit = Limit.DEFAULT_LIMIT;
        }
        double v = new Random().nextDouble();
        return (new BigDecimal(v).doubleValue() - new BigDecimal(v).intValue())
                + limit.getLowerLimit().intValue()
                + new Random().nextInt(limit.getUpperLimit().intValue() - limit.getLowerLimit().intValue());
    }

    /**
     * 多个浮点数
     */
    public static List<Double> floats(Limit limit, Integer count) {
        return new ArrayList<Double>() {{
            for (Integer i = 0; i < count; i++) {
                add(floatt(limit));
            }
        }};
    }

    /**
     * 单个布尔值
     */
    public static boolean bool() {
        return new Random().nextInt() % 2 == 0;
    }

    /**
     * 多个布尔值
     */
    public static List<Boolean> bools(Integer count) {
        return new ArrayList<Boolean>() {{
            for (Integer i = 0; i < count; i++) {
                add(bool());
            }
        }};
    }

    /**
     * 单个时间
     */
    public static Object time(TimeFormat format) {
        long l = System.currentTimeMillis() - new Random().nextInt(1_000_000_000) * 100L;
        if (format == null) {
            return l;
        }
        return new SimpleDateFormat(format.getValue()).format(l);
    }

    /**
     * 多个时间
     */
    public static Object times(TimeFormat format, Integer count) {
        return new ArrayList() {{
            for (Integer i = 0; i < count; i++) {
                add(time(format));
            }
        }};
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
