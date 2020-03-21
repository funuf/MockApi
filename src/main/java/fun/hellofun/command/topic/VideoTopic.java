package fun.hellofun.command.topic;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 18时42分17秒 创建；<br>
 * 作用是：<b>视频主题</b>；<br>
 *
 * @author zdd
 */
public enum VideoTopic implements Topic {
    /**
     * 音乐视频
     */
    MUSIC("music"),
    /**
     * 抖音上的视频
     */
    TIKTOK("tiktok");

    private String mark;

    VideoTopic(String mark) {
        this.mark = mark;
    }

    @Override
    public String getMark() {
        return mark;
    }
}
