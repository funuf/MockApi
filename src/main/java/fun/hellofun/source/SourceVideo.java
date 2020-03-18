package fun.hellofun.source;

import com.alibaba.fastjson.JSON;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.command.topic.VideoTopic;
import fun.hellofun.jUtils.predicate.empty.Empty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 18时40分57秒 创建；<br>
 * 作用是：<b>视频源</b>；<br>
 */
public class SourceVideo extends Source<String> {
    @Override
    protected String get(Topic topic) {
        return list(topic, 1).get(0);
    }

    @Override
    protected List<String> list(Topic topic, Integer limit) {
        return take(limit, pool(((VideoTopic) topic)));
    }

    private List<String> MUSICS = null;
    private List<String> TIKTOKS = null;

    /**
     * 从数据池中取值
     */
    private List<String> take(Integer limit, List<String> fromPool) {
        List<String> result = new ArrayList<>();
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        }
        for (Integer i = 0; i < limit; i++) {
            result.add(fromPool.get(new Random().nextInt(fromPool.size())));
        }
        return result;
    }

    /**
     * 确定数据池
     */
    private List<String> pool(VideoTopic topic) {
        init();
        if (topic == null) {
            return all();
        }
        switch (topic) {
            case MUSIC:
                return MUSICS;
            case TIKTOK:
                return TIKTOKS;
            default:
                return all();
        }
    }

    private List<String> all() {
        return new ArrayList<String>() {{
            addAll(MUSICS);
            addAll(TIKTOKS);
        }};
    }

    private void init() {
        if (Empty.yes(MUSICS, TIKTOKS)) {
            MUSICS = JSON.parseArray(classpathFileString("video/music.json"), String.class);
            TIKTOKS = JSON.parseArray(classpathFileString("video/tiktok.json"), String.class);
        }
    }
}
