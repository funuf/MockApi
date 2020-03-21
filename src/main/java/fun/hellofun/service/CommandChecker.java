package fun.hellofun.service;

import fun.hellofun.command.*;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.exception.InvalidLimitEndpointException;
import fun.hellofun.exception.LimitOverflowException;
import fun.hellofun.exception.MissingLimitEndpointException;
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

        Delay delay = Delay.extract(parts);
        List<Topic> topics = Topic.extract(parts, itemType);
        Hit hit = Hit.extract(parts);
        Count count = Count.extract(parts, cmd);
        TimeFormat timeFormat = TimeFormat.extract(parts);
        Limit limit = null;
        try {
            limit = Limit.extract(parts);
        } catch (Exception e) {
            if (e instanceof InvalidLimitEndpointException) {
                return InvalidReason.InvalidLimitEndpoint;
            } else if (e instanceof MissingLimitEndpointException) {
                return InvalidReason.MissingLimitEndpoint;
            } else if (e instanceof LimitOverflowException) {
                return InvalidReason.LimitOverflow;
            }
            e.printStackTrace();
            return InvalidReason.OTHER;
        }

        File file = null;
        if (cmd == Command.JSON) {
            // json命令需要file/path至少其一
            String p = TargetFile.extractPath(parts);
            String f = TargetFile.extractFile(parts);
            // 若路径不以/结尾，则追加一个/
            if (Empty.no(f, p) && !p.endsWith(File.separator)) {
                p = p + File.separator;
            }
            String filePath = p + f;
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
            if (!file.getName().endsWith(".txt")
                    && !file.getName().endsWith(".json")
                    && !file.getName().endsWith(".ftl")) {
                return InvalidReason.NotSupportFileFormat;
            }
        }
        return new ValidResult(cmd, hit, itemType, count, file, topics, limit, timeFormat, delay);
    }
}
