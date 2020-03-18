package fun.hellofun.command.topic;

import fun.hellofun.command.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月17日 星期二 8时39分33秒 创建；<br>
 * 作用是：<b>抽象主题</b>；<br>
 */
public interface Topic {

    /**
     * 获取唯一标识
     */
    String getMark();


    /**
     * 该方法由 <b>张东冬</b> 于 2020年3月18日 星期三 19时35分27秒 创建；<br>
     * 作用是：<b>所有合法主题</b>；<br>
     */
    static List<String> allMark() {
        List<String> result = new ArrayList<>();
        for (ImageTopic imageTopic : ImageTopic.values()) {
            result.add(imageTopic.getMark());
        }
        for (TextTopic textTopic : TextTopic.values()) {
            result.add(textTopic.getMark());
        }
        for (VideoTopic videoTopic : VideoTopic.values()) {
            result.add(videoTopic.getMark());
        }
        return result;
    }

    /**
     * 获取主题，可能是多个
     */
    static List<Topic> extract(String[] parts, ItemType type) {
        List<Topic> result = new ArrayList<>();
        if (type == null) {
            return result;
        }

        Map<String, Topic> map = new HashMap<>();

        Topic[] topics = null;
        switch (type) {
            case IMAGE:
                topics = ImageTopic.values();
                break;
            case TEXT:
                topics = TextTopic.values();
                break;
            case VIDEO:
                topics = VideoTopic.values();
                break;
        }
        for (Topic topic : topics) {
            map.put(topic.getMark(), topic);
        }

        for (String part : parts) {
            // 可能是逗号拼接的多个主题
            String temp = part;
            if (part.contains("topic=")) {
                temp = part.split("=")[1];
            }
            // 遍历形如   animal,boy,girl
            for (String s : temp.split(",")) {
                Topic topic = map.get(s);
                if (topic != null && !result.contains(topic)) {
                    result.add(topic);
                }
            }
        }
        return result;
    }
}
