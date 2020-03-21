package fun.hellofun.command;

/**
 * 于 2020年3月17日 星期二 19时20分25秒  创建；<br>
 * 作用是：<b>命令</b>；<br>
 *
 * @author zdd
 */
public enum Command {
    /**
     * list命令
     */
    LIST("list"),
    /**
     * get命令
     */
    GET("get"),
    /**
     * json命令
     */
    JSON("json"),
    /**
     * config配置命令
     */
    CONFIG("config");

    private String value;

    Command(String value) {
        this.value = value;
    }

    public static Command extract(String[] parts) {
        for (String part : parts) {
            for (Command command : Command.values()) {
                if (command.value.equalsIgnoreCase(part)) {
                    return command;
                }
            }
        }
        return null;
    }
}
