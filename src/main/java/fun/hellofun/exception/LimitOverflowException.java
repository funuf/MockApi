package fun.hellofun.exception;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月19日 星期四 16时20分19秒 创建；<br>
 * 作用是：<b>区间过大异常，仅支持  [Integer.MAX_VALUE - Integer.MIN_VALUE]</b>；<br>
 *
 * @author zdd
 */
public class LimitOverflowException extends RuntimeException {
    public static final String TIP = "Valid limit is  [Integer.MAX_VALUE - Integer.MIN_VALUE](合法区间由Integer能表示的为准).";
}
