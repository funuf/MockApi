package fun.hellofun.utils;

import fun.hellofun.command.Command;
import fun.hellofun.command.Hit;
import fun.hellofun.command.ItemType;
import fun.hellofun.command.Limit;
import fun.hellofun.command.topic.Topic;

import java.io.File;
import java.util.List;

/**
 * 该方法由 <b>张东冬</b> 于 2020年3月18日 星期三 10时39分05秒 创建；<br>
 * 作用是：<b>命令解释合法结果：即命令的各个有效部分</b>；<br>
 */
public class ValidResult implements Check {

    private Command cmd;
    private Hit hit;
    private ItemType type;
    private Limit limit;
    private File file;
    private List<Topic> topics;

    public ValidResult(Command cmd, Hit hit, ItemType type, Limit limit, File file, List<Topic> topics) {
        this.cmd = cmd;
        this.hit = hit;
        this.type = type;
        this.limit = limit;
        this.file = file;
        this.topics = topics;
    }

    public Command getCmd() {
        return cmd;
    }

    public Hit getHit() {
        return hit;
    }

    public ItemType getType() {
        return type;
    }

    public Limit getLimit() {
        return limit;
    }

    public File getFile() {
        return file;
    }

    public List<Topic> getTopics() {
        return topics;
    }
}
