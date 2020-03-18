package fun.hellofun.command.topic;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 19时24分19秒 创建；<br>
 * 作用是：<b>图片主题</b>；<br>
 */
public enum ImageTopic implements Topic {
    ANIMAL("animal"),
    BANNER("banner"),
    BOY("boy"),
    CAR("car"),
    FOOD("food"),
    GIRL("girl"),
    LANDSCAPE("landscape"),
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
