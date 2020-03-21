package fun.hellofun.source;

import com.alibaba.fastjson.JSON;
import fun.hellofun.command.ItemType;
import fun.hellofun.command.topic.Topic;
import fun.hellofun.command.topic.VideoTopic;
import fun.hellofun.jUtils.predicate.empty.Empty;
import fun.hellofun.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 该类由 <b>张东冬</b> 于 2020年3月18日 星期三 18时40分57秒 创建；<br>
 * 作用是：<b>视频源</b>；<br>
 *
 * @author zdd
 */
public class SourceVideo extends AbstractSource<String> {
    @Override
    protected String get(Topic topic) {
        return list(topic, 1).get(0);
    }

    @Override
    protected List<String> list(Topic topic, Integer count) {
        return take(count, pool(((VideoTopic) topic)));
    }

    private static List<String> MUSICS = null;
    private static List<String> TIKTOKS = null;

    static HashMap<String, Object> forFtl(int randomCount) {
        HashMap<String, Object> result = new HashMap<>(102);
        for (VideoTopic videoTopic : VideoTopic.values()) {
            result.put(videoTopic.getMark(), AbstractSource.video(videoTopic));
            result.put(videoTopic.getMark() + "s",
                    AbstractSource.take(ItemType.VIDEO,
                            new ArrayList<VideoTopic>() {{
                                add(videoTopic);
                            }},
                            randomCount
                    )
            );
            for (int i = Constants.INT_2; i <= Constants.INT_100; i++) {
                result.put(videoTopic.getMark() + "s" + i,
                        AbstractSource.take(ItemType.VIDEO,
                                new ArrayList<VideoTopic>() {{
                                    add(videoTopic);
                                }},
                                i
                        )
                );
            }
        }
        return result;
    }

    /**
     * 从数据池中取值
     */
    private List<String> take(Integer count, List<String> fromPool) {
        List<String> result = new ArrayList<>();
        if (count == null) {
            count = DEFAULT_LIMIT;
        }
        for (Integer i = 0; i < count; i++) {
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
