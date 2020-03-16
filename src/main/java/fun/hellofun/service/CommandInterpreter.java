package fun.hellofun.service;

import fun.hellofun.bean.InterpreterResult;
import org.springframework.stereotype.Service;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 15时29分21秒 创建；<br>
 * 作用是：<b>命令解释器：检验命令合法性，并整理为相对固定的格式供解释器使用</b>；<br>
 */
@Service
public class CommandInterpreter {
    public InterpreterResult test(String command) {
        // 循环，将相邻的空格合并
        for (int i = 0; i < 3; i++) {
            command = command.replaceAll("  ", " ");
        }
        if (command.contains("  ")) {
            return InterpreterResult.MultiSpace;
        }

        // 以capi开头
        if (!command.startsWith("capi")) {
            return InterpreterResult.InvalidStartWith;
        }

        // 命令组成部分
        String[] parts = command.split(" ");
        if (2 > parts.length) {
            return InterpreterResult.MissPart;
        }

        // list命令：limit默认=20  非法limit（小于1）时，按20返回    topic默认为没有分类，分类值可单可多
        // eg:   capi list text --limit=n --topic=name,opetry,soup  --ok=0.35
        // eg:   capi list image --limit=n --topic=animal,boy,car,food,girl,landscape,plant  --ok=0.35
        // eg:   capi list video --limit=n --topic=music,tiktok  --ok=0.35

        // get命令：默认返回一个，limit>1时，等同于list命令  topic默认为没有分类，分类值可单可多
        // eg:   capi get text --topic=name|opetry|xiongpeiyun  --ok=0.35
        // eg:   capi get image --topic=animal|boy|car|food|girl|landscape|plant  --ok=0.35
        // eg:   capi get video  --topic=music,tiktok  --ok=0.35
        // eg:   capi get rich --topic=soup,travel --ok=0.35

        // json模板命令：根据json模板返回填充后的数据   {type}
        // eg:   capi template  --file=(全文件名/文件名)  --path=D:..123.234.789  --ok=0.35

        // json对象命令：返回指定的json文件
        // eg:   capi jsono --file=（全文件名/文件名）  --path=D:..123.234.789  --ok=0.35

        // json数组命令：返回指定的json文件中的数组元素（全部或指定条目个数）
        // eg:   capi jsona  --file=（全文件名/文件名）  --path=D:..123.234.789 --limit=n  --ok=0.35

        // 配置项
        // eg:   capi config --json.path=object和array命令所需文件的父目录 --template.path=json模板所需文件的父目录 --limit=30 --ok=0.35

        // 下一个版本
        // capi append text/image/video --topic=...  --file=存储数据的文件(全文件名/文件名)   --path=存储数据的文件所需的父目录
        // capi clear text/image/video --topic=...

        return InterpreterResult.JSON;
    }
}
