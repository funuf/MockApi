package fun.hellofun.service;

import fun.hellofun.command.Command;
import fun.hellofun.exception.InvalidLimitEndpointException;
import fun.hellofun.exception.LimitOverflowException;
import fun.hellofun.exception.MissingLimitEndpointException;
import fun.hellofun.utils.check.Check;
import fun.hellofun.utils.check.InvalidReason;
import fun.hellofun.jUtils.classes.map.R;
import fun.hellofun.utils.check.ValidResult;
import fun.hellofun.utils.handler.JSONHandler;
import fun.hellofun.utils.handler.GetHandler;
import fun.hellofun.utils.handler.ListHandler;
import org.springframework.stereotype.Service;

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
                    return R.error("Command json need specify a file by --path or --file(json命令需要指定一个文件).");
                case FileNotExist:
                    return R.error("File not exist(文件不存在).");
                case InvalidLimitEndpoint:
                    return R.error(InvalidLimitEndpointException.TIP);
                case MissingLimitEndpoint:
                    return R.error(MissingLimitEndpointException.TIP);
                case LimitOverflow:
                    return R.error(LimitOverflowException.TIP);
                case OTHER:
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
            if (command.getCount().getValue() > 1) {
                return R.ok(ListHandler.handle(command));
            }
            return R.ok(GetHandler.handle(command));
        }

        if (command.getCmd() == Command.JSON) {
            // 返回json 或 填充模板
            try {
                return R.ok(JSONHandler.get(command.getFile(), command.getCount() == null ? null : command.getCount().getValue()));
            } catch (Exception e) {
                e.printStackTrace();
                return R.error(e.getMessage());
            }
        }
        return R.ok();
    }


}
