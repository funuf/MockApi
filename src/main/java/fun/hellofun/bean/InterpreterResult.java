package fun.hellofun.bean;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月16日 星期一 15时35分37秒 创建；<br>
 * 作用是：<b>命令解释结果</b>；<br>
 */
public enum InterpreterResult {
    // 太多空格
    MultiSpace,
    // 非法开头
    InvalidStartWith,
    // 缺少part，至少两个单词
    MissPart,
    // json文本
    JSON

}
