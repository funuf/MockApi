package fun.hellofun.exception;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月19日 星期四 15时43分30秒 创建；<br>
 * 作用是：<b>区间缺少端点异常</b>；<br>
 */
public class MissingLimitEndpointException extends RuntimeException {
    public static final String TIP = "Valid limit must have two endpoint,eg [-1024,1024](有效区间需包含两个端点).";
}
