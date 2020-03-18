package fun.hellofun.command.topic;

import fun.hellofun.command.ItemType;
import fun.hellofun.jUtils.predicate.empty.Empty;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月17日 星期二 8时39分33秒 创建；<br>
 * 作用是：<b>抽象主题</b>；<br>
 */
public interface Topic {

    /**
     * 获取主题，可能是多个
     */
    static List<Topic> extract(String[] parts, ItemType type) {
        List<Topic> result = new ArrayList<>();
        if (type == null) {
            return result;
        }
        for (String part : parts) {
            // 可能是逗号拼接的多个主题
            String temp = part;
            if (part.contains("topic=")) {
                temp = part.split("=")[1];
            }
            for (String s : temp.split(",")) {
                switch (type) {
                    case IMAGE:
                        for (ImageTopic topic : ImageTopic.values()) {
                            if (topic.getMark().equals(s.toLowerCase())) {
                                result.add(topic);
                            }
                        }
                        return result;
                    // TODO: 2020/3/17 其他主题
                    case VIDEO:
                        break;
                    case TEXT:
                        break;
                    case RICH:
                        break;
                }
            }
        }
        return result;
    }
}
