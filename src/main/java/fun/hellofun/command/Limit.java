package fun.hellofun.command;

import fun.hellofun.source.Source;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月17日 星期二 20时06分14秒 创建；<br>
 * 作用是：<b>数量限制</b>；<br>
 */
public class Limit {

    private int count;

    public Limit(int count) {
        this.count = count;
    }

    public static Limit extract(String[] parts) {
        for (String part : parts) {
            try {
                int i = Integer.parseInt(part);
                if (i < 0) {
                    return new Limit(Source.DEFAULT_LIMIT);
                }
                return new Limit(i);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
