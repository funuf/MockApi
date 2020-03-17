package fun.hellofun.controller;

import fun.hellofun.jUtils.classes.map.R;
import fun.hellofun.service.CommandHandler;
import fun.hellofun.service.CommandInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 15时08分41秒 创建；<br>
 * 作用是：<b>首项控制器</b>；<br>
 */
@RestController
public class IndexController {

    /**
     * 命令解析器
     */
    @Autowired
    private CommandInterpreter interpreter;

    /**
     * 命令处理器
     */
    @Autowired
    private CommandHandler handler;

    /**
     * 判断服务是否启动
     */
    @RequestMapping("/")
    public String nothing() {
        return "Command-style Mock Application Programming Interface is running,command can not be empty or nothing is matched.";
    }

    /**
     * 合法命令不含多级目录
     */
    @RequestMapping("/**")
    public String multilevelPath() {
        return "Valid command must not contain / .";
    }


    /**
     * 命令解析及处理
     */
    @RequestMapping("/{command}")
    public R run(@PathVariable("command") String command) {
        return handler.handle(interpreter.test(command));
    }
}
