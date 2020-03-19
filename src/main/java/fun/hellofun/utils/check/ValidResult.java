package fun.hellofun.utils.check;

import fun.hellofun.command.*;
import fun.hellofun.command.topic.Topic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.List;

/**
 * 该方法由 <b>张东冬</b> 于 2020年3月18日 星期三 10时39分05秒 创建；<br>
 * 作用是：<b>命令解释合法结果，同时包含命令的各个有效部分</b>；<br>
 */
@Data
@Accessors(chain = true)
public class ValidResult implements Check {

    /**
     * 命令
     */
    private Command cmd;
    /**
     * 命中率
     */
    private Hit hit;
    /**
     * 文件类型
     */
    private ItemType type;
    /**
     * 元素个数
     */
    private Count count;
    /**
     * 目标文件（.json    .ftl）
     */
    private File file;
    /**
     * 元素主题
     */
    private List<Topic> topics;
    /**
     * integer/float的区间
     */
    private Limit limit;
    /**
     * 时间展示样式
     */
    private TimeFormat timeFormat;

    public ValidResult(Command cmd, Hit hit, ItemType type, Count count, File file, List<Topic> topics, Limit limit, TimeFormat timeFormat) {
        this.cmd = cmd;
        this.hit = hit;
        this.type = type;
        this.count = count;
        this.file = file;
        this.topics = topics;
        this.limit = limit;
        this.timeFormat = timeFormat;
    }
}
