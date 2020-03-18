package fun.hellofun.service;

import fun.hellofun.command.*;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.jUtils.predicate.empty.Empty;
import fun.hellofun.utils.check.Check;
import fun.hellofun.utils.check.InvalidReason;
import fun.hellofun.utils.check.ValidResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 15时29分21秒 创建；<br>
 * 作用是：<b>命令解释器：检验命令合法性，并整理为相对固定的格式供解释器使用</b>；<br>
 */
@Service
public class CommandChecker {
    public Check check(String command) {
        // 循环，将相邻的空格合并
        for (int i = 0; i < 3; i++) {
            command = command.replaceAll("  ", " ");
        }
        if (command.contains("  ")) {
            return InvalidReason.MultiSpace;
        }

        // 以mock开头
        if (!command.startsWith("mockapi")) {
            return InvalidReason.InvalidStartWith;
        }

        // 命令组成部分
        String[] parts = command.split(" ");
        if (!"mockapi".equals(parts[0].toLowerCase())) {
            return InvalidReason.InvalidStartWith;
        }
        if (2 > parts.length) {
            return InvalidReason.MissPart;
        }

        // 命令
        Command cmd = Command.extract(parts);
        if (cmd == null) {
            return InvalidReason.InvalidCommand;
        }

        // list/get命令，需要有数据类型（图片、视频、文本、富文本）
        ItemType itemType = ItemType.extract(parts);
        if (cmd == Command.LIST || cmd == Command.GET) {
            if (itemType == null) {
                return InvalidReason.MissItemType;
            }
        }

        List<Topic> topics = Topic.extract(parts, itemType);
        Hit hit = Hit.extract(parts);
        Limit limit = Limit.extract(parts, cmd);

        File file = null;
        if (cmd == Command.JSON || cmd == Command.TEMPLATE) {
            // template/json命令需要file/path至少其一
            String filePath = TargetFile.extractPath(parts) + TargetFile.extractFile(parts);
            if (Empty.yes(filePath)) {
                return InvalidReason.MissTragetFile;
            }
            int i = filePath.lastIndexOf(".");
            String suffix = filePath.substring(i);
            // https://blog.csdn.net/weixin_34321977/article/details/91658732
            String prefix = filePath.replaceAll("\\.", Matcher.quoteReplacement(File.separator)).substring(0, i);
            file = new File(prefix + suffix);
            if (!file.exists()) {
                return InvalidReason.FileNotExist;
            }
        }
        return new ValidResult(cmd, hit, itemType, limit, file, topics);
        // list命令：limit默认=20  非法limit（小于1）时，按20返回    topic默认为没有分类，分类值可单可多
        // eg:   mockapi list text --limit=n --topic=name,opetry,soup  --ok=0.35
        // eg:   mockapi list image --limit=n --topic=animal,boy,car,food,girl,landscape,plant  --ok=0.35
        // eg:   mockapi list video --limit=n --topic=music,tiktok  --ok=0.35

        // get命令：默认返回一个，limit>1时，等同于list命令  topic默认为没有分类，分类值可单可多
        // eg:   mockapi get text --topic=name|opetry|xiongpeiyun  --ok=0.35
        // eg:   mockapi get image --topic=animal|boy|car|food|girl|landscape|plant  --ok=0.35
        // eg:   mockapi get video  --topic=music,tiktok  --ok=0.35
        // eg:   mockapi get rich --topic=soup,travel --ok=0.35

        // json模板命令：根据json模板返回填充后的数据   {type}
        // eg:   mockapi template  --file=(全文件名/文件名)  --path=D:..123.234.789  --ok=0.35

        // json对象命令：返回指定的json文件
        // eg:   mockapi json --file=（全文件名/文件名）  --path=D:..123.234.789  --ok=0.35

        // json数组命令：返回指定的json文件中的数组元素（全部或指定条目个数）
        // eg:   mockapi json  --file=（全文件名/文件名）  --path=D:..123.234.789 --limit=n  --ok=0.35

        // 下一个版本
        // 配置项
        // eg:   mockapi config --json.path=object和array命令所需文件的父目录 --template.path=json模板所需文件的父目录 --limit=30 --ok=0.35
        // 下下一个版本
        // mockapi append text/image/video --topic=...  --file=存储数据的文件(全文件名/文件名)   --path=存储数据的文件所需的父目录
        // mockapi clear text/image/video --topic=...
    }
}
