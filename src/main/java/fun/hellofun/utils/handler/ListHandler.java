package fun.hellofun.utils.handler;

import fun.hellofun.command.ItemType;
import fun.hellofun.source.Source;
import fun.hellofun.utils.check.ValidResult;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 11时04分47秒 创建；<br>
 * 作用是：<b>list命令实际执行者</b>；<br>
 */
public class ListHandler {

    public static Object handle(ValidResult result) {
        if (result.getType() == ItemType.IMAGE
                ||result.getType() == ItemType.VIDEO
                ||result.getType() == ItemType.TEXT) {
            return Source.take(result.getType(), result.getTopics(), result.getLimit().getCount());
        }

        return "Waiting develop";
    }
}
