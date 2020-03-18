package fun.hellofun.command;

import fun.hellofun.jUtils.predicate.empty.Empty;
import fun.hellofun.source.Source;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 9时39分23秒 创建；<br>
 * 作用是：<b>文件名称</b>；<br>
 */
public class TargetFile {

    /**
     * 提取文件
     */
    public static String extractFile(String[] parts) {
        return extract(parts, "file=");
    }

    /**
     * 提取路径
     */
    public static String extractPath(String[] parts) {
        String path = extract(parts, "path=");
        if (Empty.yes(path)) {
            path = Source.DEFAULT_FILE_PATH;
        }
        return path;
    }

    private static String extract(String[] parts, String mark) {
        for (String part : parts) {
            if (part.contains(mark)) {
                return part.split("=")[1];
            }
        }
        return "";
    }
}
