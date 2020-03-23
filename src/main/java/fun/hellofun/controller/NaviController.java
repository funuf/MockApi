package fun.hellofun.controller;

import com.alibaba.fastjson.JSON;
import fun.hellofun.jUtils.classes.map.R;
import lombok.Data;
import lombok.experimental.Accessors;
import okio.Okio;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月23日 星期一 13时44分40秒 创建；<br>
 * 作用是：<b>个人导航页</b>；<br>
 *
 * @author zdd
 */
@RestController
@RequestMapping("navi")
public class NaviController {
    /**
     * 文件路径
     */
    private static final String FILE_PATH = "C:\\Users\\Administrator\\Desktop\\mygit\\.navi\\json\\links.json";

    /**
     * 内存集合
     */
    private List<Link> links;

    /**
     * 获取列表
     */
    @RequestMapping("list")
    public R list() throws Exception {

        // 读取文本内容
        links = JSON.parseArray(Okio.buffer(Okio.source(new File(FILE_PATH))).readUtf8(), Link.class);

        // 按常用量，取前10个
        List<Link> linksByViews = JSON.parseArray(Okio.buffer(Okio.source(new File(FILE_PATH))).readUtf8(), Link.class);
        linksByViews.sort(Comparator.comparingInt(Link::getViews));
        linksByViews = linksByViews.subList(0, 10);

        // 按所属模块
        List<String> belongTos = new ArrayList<>();
        Map<String, List<Link>> linksOfBelongTo = new HashMap<>(10);
        for (Link link : links) {
            if (linksOfBelongTo.containsKey(link.getBelongTo())) {
                linksOfBelongTo.get(link.getBelongTo()).add(link);
            } else {
                List<Link> t = new ArrayList<>();
                t.add(link);
                linksOfBelongTo.put(link.getBelongTo(), t);
            }
            if (!belongTos.contains(link.getBelongTo())) {
                belongTos.add(link.belongTo);
            }
        }
        Collections.sort(belongTos);


        // 按首字母
        List<String> firstChars = new ArrayList<>();
        Map<String, List<Link>> linksOfFirstChar = new HashMap<>(30);
        for (Link link : links) {
            if (linksOfFirstChar.containsKey(link.getFirstChar().toUpperCase())) {
                linksOfFirstChar.get(link.getFirstChar().toUpperCase()).add(link);
            } else {
                List<Link> t = new ArrayList<>();
                t.add(link);
                linksOfFirstChar.put(link.getFirstChar().toUpperCase(), t);
            }
            if (!firstChars.contains(link.getFirstChar().toUpperCase())) {
                firstChars.add(link.getFirstChar().toUpperCase());
            }
        }
        Collections.sort(firstChars);

        HashMap<String, Object> result = new HashMap<>(3);
        result.put("byViews", linksByViews);
        result.put("belongTos", belongTos);
        result.put("ofBelongTo", linksOfBelongTo);
        result.put("firstChars", firstChars);
        result.put("ofFirstChar", linksOfFirstChar);
        return R.ok(result);
    }

    /**
     * 目标网站被查看
     */
    @RequestMapping("see")
    public R see(String name) throws Exception {
        for (Link link : links) {
            if (link.getName().equals(name)) {
                link.setViews(link.getViews() + 1);
                Okio.buffer(Okio.sink(new File(FILE_PATH))).writeUtf8(JSON.toJSONString(links));
                return R.ok();
            }
        }
        return R.ok();
    }


    /**
     * 代表一个网站
     */
    @Data
    @Accessors(chain = true)
    static class Link {
        /**
         * 网站名称
         */
        private String name;
        /**
         * 网站描述
         */
        private String title;
        /**
         * 网站链接
         */
        private String url;
        /**
         * 是否加粗（着重标识）
         */
        private boolean bold;
        /**
         * 浏览次数
         */
        private int views;
        /**
         * 首字母（大小写不限）
         */
        private String firstChar;
        /**
         * 所属模块
         */
        private String belongTo;
    }
}
