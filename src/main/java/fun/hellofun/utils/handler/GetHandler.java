package fun.hellofun.utils.handler;

import fun.hellofun.command.ItemType;
import fun.hellofun.command.topic.ImageTopic;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.command.topic.VideoTopic;
import fun.hellofun.jUtils.predicate.empty.Empty;
import fun.hellofun.source.Source;
import fun.hellofun.utils.check.ValidResult;

import java.util.List;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 10时50分31秒 创建；<br>
 * 作用是：<b>Get命令实际处理者</b>；<br>
 */
public class GetHandler {

    public static Object handle(ValidResult result) {
        List<Topic> topics = result.getTopics();

        // 如果没有指明主题，那么就是全部
        boolean allTopic = false;
        if (Empty.yes(topics)) {
            allTopic = true;
        }

        if (result.getType() == ItemType.IMAGE) {
            return Source.image(allTopic ? null : ((ImageTopic) topics.get(0)));
        }

        if (result.getType() == ItemType.VIDEO) {
            return Source.video(allTopic ? null : ((VideoTopic) topics.get(0)));
        }

        if (result.getType() == ItemType.TEXT) {
            return Source.video(allTopic ? null : ((VideoTopic) topics.get(0)));
        }


        return "Waiting develop";
    }
}
