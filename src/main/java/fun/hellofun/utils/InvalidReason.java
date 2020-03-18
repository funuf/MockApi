package fun.hellofun.utils;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 15时35分37秒 创建；<br>
 * 作用是：<b>非法命令结果</b>；<br>
 */
public enum InvalidReason implements Check{
    // 太多空格
    MultiSpace,
    // 非法开头
    InvalidStartWith,
    // 缺少part，至少两个单词
    MissPart,
    // 不存在的命令
    InvalidCommand,
    // list/get命令需要类型
    MissItemType,
    // json/template命令缺少文件指向
    MissTragetFile,
    // json/template命令指向的文件不存在
    FileNotExist,
}
