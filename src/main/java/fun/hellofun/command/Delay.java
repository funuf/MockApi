package fun.hellofun.command;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月21日 星期六 13时44分54秒 创建；<br>
 * 作用是：<b>接口请求耗时，单位：秒</b>；<br>
 */
@Data
@Accessors(chain = true)
public class Delay {

    private int value;

    public Delay(int value) {
        this.value = value;
    }

    public static final Delay NOW = new Delay(0);

    public static Delay DEFAULT = NOW;

    /**
     * 获取本次接口需要耗时。默认0s
     */
    public static Delay extract(String[] parts) {
        for (String part : parts) {
            if (part.toLowerCase().contains("delay=")) {
                try {
                    return new Delay(Math.abs(Integer.parseInt(part.split("=")[1])));
                } catch (NumberFormatException e) {
                    return DEFAULT;
                }
            }
        }
        return DEFAULT;
    }
}
