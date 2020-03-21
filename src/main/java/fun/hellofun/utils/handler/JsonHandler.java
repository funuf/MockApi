package fun.hellofun.utils.handler;

import com.alibaba.fastjson.JSON;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import fun.hellofun.command.ItemType;
import fun.hellofun.source.AbstractSource;
import fun.hellofun.utils.Constants;
import okio.Okio;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月19日 星期四 9时24分28秒 创建；<br>
 * 作用是：<b>json命令实际处理者</b>；<br>
 *
 * @author zdd
 */
public class JsonHandler {


    private static Configuration configuration = null;


    /**
     * 获取json数据
     */
    public static Object get(File file, Integer count) throws Exception {
        // 如果后缀是.txt  或 .json，则原样返回
        if (file.getName().endsWith(Constants.FORMAT_TXT)
                || file.getName().endsWith(Constants.FORMAT_JSON)) {
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
        StringWriter out = new StringWriter();

        int randomCount = new Random().nextInt(100) + 1;

        // 整型
        HashMap<String, Object> integer = new HashMap<>(102);
        integer.put("single", AbstractSource.integer(null));
        integer.put("multi", AbstractSource.integers(null, randomCount));
        for (int i = Constants.INT_2; i < Constants.INT_100; i++) {
            integer.put("multi" + i, AbstractSource.integers(null, i));
        }

        // 浮点型
        HashMap<String, Object> floatt = new HashMap<>(102);
        floatt.put("single", AbstractSource.floatt(null));
        floatt.put("multi", AbstractSource.floats(null, randomCount));
        for (int i = Constants.INT_2; i < Constants.INT_100; i++) {
            floatt.put("multi" + i, AbstractSource.floats(null, i));
        }

        // 布尔型
        HashMap<String, Object> bool = new HashMap<>(102);
        bool.put("single", AbstractSource.bool());
        bool.put("multi", AbstractSource.bools(randomCount));
        for (int i = Constants.INT_2; i < Constants.INT_100; i++) {
            bool.put("multi" + i, AbstractSource.bools(i));
        }

        // 时间戳
        HashMap<String, Object> time = new HashMap<>(102);
        time.put("single", AbstractSource.time(null));
        time.put("multi", AbstractSource.times(null, randomCount));
        for (int i = Constants.INT_2; i < Constants.INT_100; i++) {
            time.put("multi" + i, AbstractSource.times(null, i));
        }

        configuration.getTemplate(file.getName()).process(
                new HashMap<String, Object>(1) {{
                    put(Constants.MOCK_API,
                            new HashMap<String, Object>(7) {{
                                put("text", AbstractSource.forFtl(ItemType.TEXT, randomCount));
                                put("image", AbstractSource.forFtl(ItemType.IMAGE, randomCount));
                                put("video", AbstractSource.forFtl(ItemType.VIDEO, randomCount));
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
