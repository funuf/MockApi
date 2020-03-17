package fun.hellofun.command;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月17日 星期二 19时57分47秒 创建；<br>
 * 作用是：<b>list、get命令的条目类型</b>；<br>
 */
public enum ItemType {
    TEXT(new String[]{"text", "txt"}),
    IMAGE(new String[]{"image", "img", "photo", "picture"}),
    VIDEO(new String[]{"video"}),
    RICH(new String[]{"rich", "article"});

    private String[] marks;

    ItemType(String[] marks) {
        this.marks = marks;
    }

    public String[] getMark() {
        return marks;
    }

    /**
     * 抽取类型
     */
    public static ItemType extract(String[] parts) {
        for (String part : parts) {
            for (ItemType itemType : ItemType.values()) {
                for (String s : itemType.getMark()) {
                    if (s.equals(part.toLowerCase())) {
                        return itemType;
                    }
                }
            }
        }
        return null;
    }
}
