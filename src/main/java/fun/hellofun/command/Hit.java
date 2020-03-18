package fun.hellofun.command;

import java.math.BigDecimal;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 8时38分53秒 创建；<br>
 * 作用是：<b>命中率：  [0,1]区间的值，忽略正负数，以绝对值为准</b>；<br>
 */
public class Hit {
    private double hit;

    public Hit(double hit) {
        this.hit = hit;
    }

    public double getHit() {
        if (hit >= 1) {
            return 1;
        } else if (hit <= 0) {
            return 0;
        } else {
            return hit;
        }
    }

    public static Hit extract(String[] parts) {
        // 检测ok=    hit=   ,=后边非数字的话，默认1
        for (String part : parts) {
            if (part.contains("ok=") || part.contains("hit=")) {
                try {
                    return new Hit(new BigDecimal(part.split("=")[1]).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                } finally {
                    return new Hit(1);
                }
            }
        }

        // 检测首个有效浮点数作为命中值
        for (String part : parts) {
            if (part.contains(".")) {
                try {
                    return new Hit(new BigDecimal(part).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                } finally {
                    // no-op
                }
            }
        }
        return new Hit(1);

    }
}
