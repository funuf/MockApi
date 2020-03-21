package fun.hellofun.utils.handler;

import fun.hellofun.source.AbstractSource;
import fun.hellofun.utils.check.ValidResult;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 11时04分47秒 创建；<br>
 * 作用是：<b>list命令实际执行者</b>；<br>
 *
 * @author zdd
 */
public class ListHandler {

    public static Object handle(ValidResult result) {
        switch (result.getType()) {
            case IMAGE:
            case VIDEO:
            case TEXT:
                return AbstractSource.take(result.getType(), result.getTopics(), result.getCount().getValue());
            case INTEGER:
                return AbstractSource.integers(result.getLimit(), result.getCount().getValue());
            case FLOAT:
                return AbstractSource.floats(result.getLimit(), result.getCount().getValue());
            case BOOLEAN:
                return AbstractSource.bools(result.getCount().getValue());
            case TIME:
                return AbstractSource.times(result.getTimeFormat(), result.getCount().getValue());
            default:
                return "Waiting develop";
        }
    }
}
