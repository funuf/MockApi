package fun.hellofun.command;

import fun.hellofun.exception.InvalidLimitEndpointException;
import fun.hellofun.exception.LimitOverflowException;
import fun.hellofun.exception.MissingLimitEndpointException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.*;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月19日 星期四 15时33分06秒 创建；<br>
 * 作用是：<b>integer/float的上下限</b>；<br>
 */
@Data
@Accessors(chain = true)
public class Limit {

    public static final Limit DEFAULT_LIMIT = new Limit().setLowerLimit(new BigDecimal("0")).setUpperLimit(new BigDecimal("100"));
    /**
     * 下限
     */
    private BigDecimal lowerLimit;
    /**
     * 上限
     */
    private BigDecimal upperLimit;


    /**
     * 提取区间，默认[0,100]
     */
    public static Limit extract(String[] parts) throws Exception {
        for (String part : parts) {
            if (part.contains("(") && part.endsWith(")")) {
                String substring = part.substring(part.lastIndexOf("(") + 1, part.length() - 1);
                if (!substring.contains(",")) {
                    throw new MissingLimitEndpointException();
                }
                String[] split = substring.split(",");
                if (2 > split.length) {
                    throw new MissingLimitEndpointException();
                }
                List<BigDecimal> list = new ArrayList<>();
                for (String s : split) {
                    try {
                        list.add(new BigDecimal(s));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new InvalidLimitEndpointException();
                    }
                }
                Collections.sort(list, BigDecimal::compareTo);
                long i = 0L + list.get(list.size() - 1).intValue() - list.get(0).intValue();
                long j = 0L + Integer.MAX_VALUE - Integer.MIN_VALUE;
                if (i > j) {
                    throw new LimitOverflowException();
                }
                return new Limit()
                        .setLowerLimit(list.get(0))
                        .setUpperLimit(list.get(list.size() - 1));

            }
        }
        return DEFAULT_LIMIT;
    }

}
