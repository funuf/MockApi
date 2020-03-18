package fun.hellofun.source;

import com.alibaba.fastjson.JSON;
import fun.hellofun.command.topic.TextTopic;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.command.topic.VideoTopic;
import fun.hellofun.jUtils.predicate.empty.Empty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 19时00分20秒 创建；<br>
 * 作用是：<b>文本源</b>；<br>
 */
public class SourceText extends Source<String> {
    @Override
    protected String get(Topic topic) {
        return list(topic, 1).get(0);
    }

    @Override
    protected List<String> list(Topic topic, Integer limit) {
        return take(limit, pool(((TextTopic) topic)));
    }

    private List<String> NAMES = null;
    private List<String> SOUPS = null;

    /**
     * 从数据池中取值
     */
    private List<String> take(Integer limit, List<String> fromPool) {
        List<String> result = new ArrayList<>();
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        }
        for (Integer i = 0; i < limit; i++) {
            result.add(fromPool.get(new Random().nextInt(fromPool.size())));
        }
        return result;
    }

    /**
     * 确定数据池
     */
    private List<String> pool(TextTopic topic) {
        init();
        if (topic == null) {
            return all();
        }
        switch (topic) {
            case NAME:
                return NAMES;
            case SOUP:
                return SOUPS;
            default:
                return all();
        }
    }

    private List<String> all() {
        return new ArrayList<String>() {{
            addAll(NAMES);
            addAll(SOUPS);
        }};
    }

    private void init() {
        if (Empty.yes(NAMES, SOUPS)) {
            NAMES = JSON.parseArray(classpathFileString("text/name.json"), String.class);
            SOUPS = JSON.parseArray(classpathFileString("text/soup.json"), String.class);
        }
    }
}
