package fun.hellofun.source;

import com.alibaba.fastjson.JSON;
import fun.hellofun.command.ItemType;
import fun.hellofun.command.topic.TextTopic;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.jUtils.predicate.empty.Empty;
import fun.hellofun.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 于 2020年3月18日 星期三 19时00分20秒 创建；<br>
 * 作用是：<b>文本源</b>；<br>
 *
 * @author zdd
 */
class SourceText extends AbstractSource<String> {

    /**
     * 为模板构建内置数据
     */
    static HashMap<String, Object> forFtl(int randomCount) {
        // 文本数据
        HashMap<String, Object> result = new HashMap<>(102);
        for (TextTopic textTopic : TextTopic.values()) {
            result.put(textTopic.getMark(), AbstractSource.text(textTopic));
            result.put(textTopic.getMark() + "s",
                    AbstractSource.take(ItemType.TEXT,
                            new ArrayList<TextTopic>() {{
                                add(textTopic);
                            }},
                            randomCount
                    )
            );
            for (int i = Constants.INT_2; i <= Constants.INT_100; i++) {
                result.put(textTopic.getMark() + "s" + i,
                        AbstractSource.take(ItemType.TEXT,
                                new ArrayList<TextTopic>() {{
                                    add(textTopic);
                                }},
                                i
                        )
                );
            }
        }
        return result;
    }

    @Override
    protected String get(Topic topic) {
        return list(topic, 1).get(0);
    }

    @Override
    protected List<String> list(Topic topic, Integer count) {
        return take(count, pool(((TextTopic) topic)));
    }

    private static List<String> NAMES = null;
    private static List<String> SOUPS = null;

    /**
     * 从数据池中取值
     */
    private List<String> take(Integer count, List<String> fromPool) {
        List<String> result = new ArrayList<>();
        if (count == null) {
            count = DEFAULT_LIMIT;
        }
        for (Integer i = 0; i < count; i++) {
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
