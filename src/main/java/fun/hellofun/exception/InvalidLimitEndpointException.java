package fun.hellofun.exception;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月19日 星期四 15时47分51秒 创建；<br>
 * 作用是：<b>数字区间包含非法端点异常</b>；<br>
 */
public class InvalidLimitEndpointException extends RuntimeException {
    public static final String TIP = "Limit contain invalid endpoint(区间包含非法端点).";
}
