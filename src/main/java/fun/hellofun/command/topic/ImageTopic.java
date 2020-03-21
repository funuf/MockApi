package fun.hellofun.command.topic;

/**
 * 于 2020年3月16日 星期一 19时24分19秒 创建；<br>
 * 作用是：<b>图片主题</b>；<br>
 *
 * @author zdd
 */
public enum ImageTopic implements Topic {
    /**
     * 动物
     */
    ANIMAL("animal"),
    /**
     * 轮播图
     */
    BANNER("banner"),
    /**
     * 男生
     */
    BOY("boy"),
    /**
     * 汽车
     */
    CAR("car"),
    /**
     * 食物
     */
    FOOD("food"),
    /**
     * 女生
     */
    GIRL("girl"),
    /**
     * 风景
     */
    LANDSCAPE("landscape"),
    /**
     * 植物
     */
    PLANT("plant");

    private String mark;

    ImageTopic(String mark) {
        this.mark = mark;
    }

    @Override
    public String getMark() {
        return mark;
    }
}
