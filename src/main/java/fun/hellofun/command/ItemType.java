package fun.hellofun.command;

import lombok.Getter;

/**
 * 于 2020年3月17日 星期二 19时57分47秒 创建；<br>
 * 作用是：<b>list、get命令的条目类型</b>；<br>
 *
 * @author zdd
 */
public enum ItemType {
    /**
     * 文本类型
     */
    TEXT(new String[]{"text", "txt"}),
    /**
     * 图片类型
     */
    IMAGE(new String[]{"image", "img", "photo", "picture"}),
    /**
     * 视频类型
     */
    VIDEO(new String[]{"video"}),
    /**
     * 整型
     */
    INTEGER(new String[]{"int", "integer"}),
    /**
     * 浮点型
     */
    FLOAT(new String[]{"float"}),
    /**
     * 布尔值
     */
    BOOLEAN(new String[]{"boolean", "bool"}),
    /**
     * 时间戳
     */
    TIME(new String[]{"time", "timestamp"});

    @Getter
    private String[] marks;

    ItemType(String[] marks) {
        this.marks = marks;
    }

    /**
     * 抽取类型
     */
    public static ItemType extract(String[] parts) {
        for (String part : parts) {
            for (ItemType itemType : ItemType.values()) {
                for (String s : itemType.getMarks()) {
                    if (s.equals(part.toLowerCase())) {
                        return itemType;
                    }
                }
            }
        }
        return null;
    }
}
