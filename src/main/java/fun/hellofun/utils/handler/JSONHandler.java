package fun.hellofun.utils.handler;

import com.alibaba.fastjson.JSON;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import fun.hellofun.command.ItemType;
import fun.hellofun.command.topic.ImageTopic;
import fun.hellofun.command.topic.TextTopic;
import fun.hellofun.command.topic.VideoTopic;
import fun.hellofun.source.Source;
import okio.Okio;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月19日 星期四 9时24分28秒 创建；<br>
 * 作用是：<b>json命令实际处理者</b>；<br>
 */
public class JSONHandler {


    private static Configuration configuration = null;


    /**
     * 获取json数据
     */
    public static Object get(File file, Integer count) throws Exception {
        // 如果后缀是.txt  或 .json，则原样返回
        if (file.getName().endsWith(".txt")
                || file.getName().endsWith(".json")) {
            Object item = JSON.parse(Okio.buffer(Okio.source(file)).readUtf8());
            if (count != null && count > 1) {
                return new ArrayList() {{
                    for (Integer i = 0; i < count; i++) {
                        add(item);
                    }
                }};
            }
            return item;
        }
        // 如果是后缀是.ftl 则进行填充
        return JSON.parse(inflate(file));
    }


    /**
     * 模板填充
     */
    private static String inflate(File file) throws Exception {
        if (configuration == null) {
            configuration = new Configuration(Configuration.VERSION_2_3_29);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        }
        configuration.setDirectoryForTemplateLoading(file.getParentFile());
        Template template = configuration.getTemplate(file.getName());
        StringWriter out = new StringWriter();

        int randomCount = new Random().nextInt(100) + 1;

        // 图片数据
        HashMap<String, Object> image = new HashMap<>();
        for (ImageTopic imageTopic : ImageTopic.values()) {
            image.put(imageTopic.getMark(), Source.image(imageTopic));
            image.put(imageTopic.getMark() + "s",
                    Source.take(ItemType.IMAGE,
                            new ArrayList<ImageTopic>() {{
                                add(imageTopic);
                            }},
                            randomCount
                    )
            );
            for (int i = 2; i <= 100; i++) {
                image.put(imageTopic.getMark() + "s" + i,
                        Source.take(ItemType.IMAGE,
                                new ArrayList<ImageTopic>() {{
                                    add(imageTopic);
                                }},
                                i
                        )
                );
            }
        }
        // 视频数据
        HashMap<String, Object> video = new HashMap<>();
        for (VideoTopic videoTopic : VideoTopic.values()) {
            video.put(videoTopic.getMark(), Source.video(videoTopic));
            video.put(videoTopic.getMark() + "s",
                    Source.take(ItemType.VIDEO,
                            new ArrayList<VideoTopic>() {{
                                add(videoTopic);
                            }},
                            randomCount
                    )
            );
            for (int i = 2; i <= 100; i++) {
                video.put(videoTopic.getMark() + "s" + i,
                        Source.take(ItemType.VIDEO,
                                new ArrayList<VideoTopic>() {{
                                    add(videoTopic);
                                }},
                                i
                        )
                );
            }
        }

        // 文本数据
        HashMap<String, Object> text = new HashMap<>();
        for (TextTopic textTopic : TextTopic.values()) {
            text.put(textTopic.getMark(), Source.text(textTopic));
            text.put(textTopic.getMark() + "s",
                    Source.take(ItemType.TEXT,
                            new ArrayList<TextTopic>() {{
                                add(textTopic);
                            }},
                            randomCount
                    )
            );
            for (int i = 2; i <= 100; i++) {
                text.put(textTopic.getMark() + "s" + i,
                        Source.take(ItemType.TEXT,
                                new ArrayList<TextTopic>() {{
                                    add(textTopic);
                                }},
                                i
                        )
                );
            }
        }


        // 整型
        HashMap<String, Object> integer = new HashMap<>();
        integer.put("single", Source.integer(null));
        integer.put("multi", Source.integers(null, randomCount));
        for (int i = 2; i < 100; i++) {
            integer.put("multi" + i, Source.integers(null, i));
        }


        // 浮点型
        HashMap<String, Object> floatt = new HashMap<>();
        floatt.put("single", Source.floatt(null));
        floatt.put("multi", Source.floats(null, randomCount));
        for (int i = 2; i < 100; i++) {
            floatt.put("multi" + i, Source.floats(null, i));
        }

        // 布尔型
        HashMap<String, Object> bool = new HashMap<>();
        bool.put("single", Source.bool());
        bool.put("multi", Source.bools(randomCount));
        for (int i = 2; i < 100; i++) {
            bool.put("multi" + i, Source.bools(i));
        }

        // 时间戳
        HashMap<String, Object> time = new HashMap<>();
        time.put("single", Source.time(null));
        time.put("multi", Source.times(null, randomCount));
        for (int i = 2; i < 100; i++) {
            time.put("multi" + i, Source.times(null, i));
        }

        template.process(
                new HashMap<String, Object>() {{
                    put("mockapi",
                            new HashMap<String, Object>() {{
                                put("text", text);
                                put("image", image);
                                put("video", video);
                                put("integer", integer);
                                put("float", floatt);
                                put("boolean", bool);
                                put("time", time);
                            }}
                    );
                }},
                out
        );
        out.close();
        return out.toString();
    }
}
