package fun.hellofun.utils.handler;

import fun.hellofun.command.ItemType;
import fun.hellofun.command.topic.ImageTopic;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.jUtils.predicate.empty.Empty;
import fun.hellofun.source.Source;
import fun.hellofun.utils.check.ValidResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 11时04分47秒 创建；<br>
 * 作用是：<b>list命令实际执行者</b>；<br>
 */
public class ListHandler {

    public static Object handle(ValidResult result) {
        List<Topic> topics = result.getTopics();

        // 如果没有指明主题，那么就是全部
        boolean allTopic = false;
        if (Empty.yes(topics)) {
            allTopic = true;
        }

        if (result.getType() == ItemType.IMAGE) {
            List<ImageTopic> imageTopics = new ArrayList<>();
            for (Topic topic : topics) {
                imageTopics.add(((ImageTopic) topic));
            }
            return Source.images(allTopic ? null : imageTopics, result.getLimit().getCount());
        }

        return "Waiting develop";
    }
}
