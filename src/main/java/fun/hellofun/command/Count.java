package fun.hellofun.command;

import fun.hellofun.source.Source;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月17日 星期二 20时06分14秒 创建；<br>
 * 作用是：<b>数量限制</b>；<br>
 */
public class Count {

    @Getter
    private int value;

    public Count(int value) {
        this.value = value;
    }

    public static Count extract(String[] parts, Command cmd) {
        // 检测count=
        for (String part : parts) {
            if (part.contains("count=")) {
                int i = 0;
                try {
                    i = new BigDecimal(part.split("=")[1]).intValue();
                } catch (Exception e) {
                    // no-op
                } finally {
                    if (0 == i) {
                        return new Count(Source.DEFAULT_LIMIT);
                    } else {
                        return new Count(Math.abs(i));
                    }
                }
            }
        }

        // 检测首个非0整数
        for (String part : parts) {
            try {
                int i = Integer.parseInt(part);
                if (i == 0) {
                    return new Count(Source.DEFAULT_LIMIT);
                }
                return new Count(Math.abs(i));
            } catch (Exception e) {
                // no-op
            }
        }

        // 默认值
        if (cmd == Command.GET || cmd == Command.JSON) {
            return new Count(1);
        }
        if (cmd == Command.LIST) {
            return new Count(Source.DEFAULT_LIMIT);
        }
        return null;
    }
}
