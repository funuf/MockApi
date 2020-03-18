package fun.hellofun.command;

import fun.hellofun.source.Source;

import java.math.BigDecimal;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月17日 星期二 20时06分14秒 创建；<br>
 * 作用是：<b>数量限制</b>；<br>
 */
public class Limit {

    private int count;

    public Limit(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public static Limit extract(String[] parts, Command cmd) {
        if (cmd != Command.LIST && cmd != Command.GET) {
            return null;
        }

        // 检测limit=
        for (String part : parts) {
            if (part.contains("limit=")) {
                int i = 0;
                try {
                    i = new BigDecimal(part.split("=")[1]).intValue();
                } finally {
                    if (0 == i) {
                        return new Limit(Source.DEFAULT_LIMIT);
                    } else {
                        return new Limit(Math.abs(i));
                    }
                }
            }
        }

        // 检测首个非0整数
        for (String part : parts) {
            try {
                int i = Integer.parseInt(part);
                if (i == 0) {
                    return new Limit(Source.DEFAULT_LIMIT);
                }
                return new Limit(Math.abs(i));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 默认值
        if (cmd == Command.GET) {
            return new Limit(1);
        }
        if (cmd == Command.LIST) {
            return new Limit(Source.DEFAULT_LIMIT);
        }
        return null;
    }
}
