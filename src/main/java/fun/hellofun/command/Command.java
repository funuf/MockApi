package fun.hellofun.command;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月17日 星期二 19时20分25秒 创建；<br>
 * 作用是：<b>命令</b>；<br>
 */
public enum Command {
    LIST("list"),
    GET("get"),
    TEMPLATE("template"),
    JSON("json"),
    CONFIG("config");
    private String value;

    Command(String value) {
        this.value = value;
    }

    public static Command extract(String[] parts) {
        for (String part : parts) {
            for (Command command : Command.values()) {
                if (command.value.equals(part.toLowerCase())) {
                    return command;
                }
            }
        }
        return null;
    }
}
