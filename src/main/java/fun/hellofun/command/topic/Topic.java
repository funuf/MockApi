package fun.hellofun.command.topic;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月17日 星期二 8时39分33秒 创建；<br>
 * 作用是：<b>抽象主题</b>；<br>
 */
public interface Topic {

    static Topic extract(String[] parts) {
        for (String part : parts) {
            for (ImageTopic topic : ImageTopic.values()) {
                if (topic.getMark().equals(part.toLowerCase())) {
                    return topic;
                }
            }
            // TODO: 2020/3/17 其他主题
        }
        return null;
    }
}
