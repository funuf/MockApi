package fun.hellofun.command;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月19日 星期四 16时36分22秒 创建；<br>
 * 作用是：<b>日期显示样式</b>；<br>
 */
@Data
@Accessors(chain = true)
public class TimeFormat {
    private String value;

    /**
     * 时间展示样式，默认null
     */
    public static TimeFormat extract(String[] parts) {
        for (String part : parts) {
            if (part.contains("{") && part.endsWith("}")) {
                return new TimeFormat().setValue(part.substring(part.lastIndexOf("{") + 1, part.length() - 1));
            }
        }
        return null;
    }
}
