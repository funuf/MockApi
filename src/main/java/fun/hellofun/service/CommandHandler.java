package fun.hellofun.service;

import com.alibaba.fastjson.JSON;
import fun.hellofun.command.Command;
import fun.hellofun.utils.check.Check;
import fun.hellofun.utils.check.InvalidReason;
import fun.hellofun.jUtils.classes.map.R;
import fun.hellofun.utils.check.ValidResult;
import fun.hellofun.utils.handler.GetHandler;
import fun.hellofun.utils.handler.ListHandler;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 15时29分51秒 创建；<br>
 * 作用是：<b>命令处理器</b>；<br>
 */
@Service
public class CommandHandler {

    public R handle(Check checkResult) {
        if (checkResult instanceof InvalidReason) {
            InvalidReason invalidReason = ((InvalidReason) checkResult);
            switch (invalidReason) {
                case MultiSpace:
                    return R.error("Too many Spaces(太多空格).");
                case InvalidStartWith:
                    return R.error("Valid command is start with mockapi(合法命令以mockapi开头).");
                case MissPart:
                    return R.error("Valid command is contain two words at least(合法命令至少包含两个单词).");
                case InvalidCommand:
                    return R.error("Valid command support list/get/json/template/config(合法命令仅支持list/get/json/template/config).");
                case MissItemType:
                    return R.error("Command list/get need explicit type,eg:text/video/image/rich(list/get命令需要明确条目类型).");
                case MissTragetFile:
                    return R.error("Command template/json need specify a file by --path or --file(template/json命令需要指定一个文件).");
                case FileNotExist:
                    return R.error("File not exist(文件不存在).");
                default:
                    return R.error("System Exception(系统异常).");
            }
        }

        // 命中率
        ValidResult command = (ValidResult) checkResult;
        if (command.getHit().getValue() == 0 || Math.random() > command.getHit().getValue()) {
            return R.error("未命中");
        }

        if (command.getCmd() == Command.LIST) {
            return R.ok(ListHandler.handle(command));
        }

        if (command.getCmd() == Command.GET) {
            if (command.getLimit().getCount() > 1) {
                return R.ok(ListHandler.handle(command));
            }
            return R.ok(GetHandler.handle(command));
        }


//        new File("/").list() // D://下的直接文件（夹）
//        new File(".") // pom.xml所在文件夹下所有内容
            /*try {
//                    return R.ok(JSON.parse(Okio.buffer(Okio.source(new File("./heihei.json"))).readUtf8()));
                return R.ok(JSON.parse(Okio.buffer(Okio.source(new File("./haha.txt"))).readUtf8()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return R.error("文件未找到");
            } catch (IOException e) {
                e.printStackTrace();
                return R.error("io异常：" + e.getMessage());
            }*/
        return R.ok();
    }


}
