package fun.hellofun.service;

import com.alibaba.fastjson.JSON;
import fun.hellofun.command.InterpreterResult;
import fun.hellofun.jUtils.classes.map.R;
import okio.Okio;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 15时29分51秒 创建；<br>
 * 作用是：<b>命令处理器</b>；<br>
 */
@Service
public class CommandHandler {

    public R handle(InterpreterResult command) {
        switch (command) {
            case MultiSpace:
                return R.error("Too many Spaces(太多空格).");
            case InvalidStartWith:
                return R.error("Valid command is start with mock(合法命令以mock开头).");
            case MissPart:
                return R.error("Valid command is contain two words at least(合法命令至少包含两个单词).");
            case JSON:


//        new File("/").list() // D://下的直接文件（夹）
//        new File(".") // pom.xml所在文件夹下所有内容
                try {
//                    return R.ok(JSON.parse(Okio.buffer(Okio.source(new File("./heihei.json"))).readUtf8()));
                    return R.ok(JSON.parse(Okio.buffer(Okio.source(new File("./haha.txt"))).readUtf8()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return R.error("文件未找到");
                } catch (IOException e) {
                    e.printStackTrace();
                    return R.error("io异常：" + e.getMessage());
                }
        }
        return R.ok();
    }


}
